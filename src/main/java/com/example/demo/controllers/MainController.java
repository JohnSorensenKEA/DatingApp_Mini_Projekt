package com.example.demo.controllers;

import com.example.demo.models.UserIdentification;
import com.example.demo.services.*;
import org.apache.tomcat.jni.File;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class MainController {

    CandidateService candidateService;
    ChatService chatService;
    CheckUserInput checkUserInput;
    CheckUserService checkUserService;
    LoginService loginService;
    PhotoHandler photoHandler;
    ProfileHandler profileHandler;


    public MainController(){
        checkUserInput = new CheckUserInput();
        checkUserService = new CheckUserService();
        photoHandler = new PhotoHandler();
        candidateService = new CandidateService();
        chatService = new ChatService();
        loginService = new LoginService();
        profileHandler = new ProfileHandler();
    }

    //Not tested
    @GetMapping("/login")
    public String login(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "login";
        }
        else if(userIden.isAdmin()){
            return "redirect:userList";
        }
        else if(userIden.getUserID() > 0){
            return "redirect:match";
        }
        return "login";
    }

    //Not tested
    @PostMapping("/loginRequest")
    public String loginRequest(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest webRequest){
        String username = webRequest.getParameter("username");
        String password = webRequest.getParameter("password");
        UserIdentification userIden = null;
        if(loginService.checkAdmin(username,password)){
            userIden = checkUserService.createUserIdentification(-1, true);
            Cookie cookie = new Cookie("cookieID",userIden.getCookieID());
            cookie.setMaxAge(2592000);
            response.addCookie(cookie);
            return "redirect:userList";
        }
        else{
            int userID = loginService.getUserID(username,password);
            if(userID > 0){
                userIden = checkUserService.createUserIdentification(userID,false);
                Cookie cookie = new Cookie("cookieID",userIden.getCookieID());
                cookie.setMaxAge(2592000);
                response.addCookie(cookie);
                return "redirect:match";
            }
        }
        modelMap.addAttribute("errorMessage","Forkert Brugernavn eller Kodeord");
        return "redirect:login";
    }

    //Not tested
    @GetMapping("/register")
    public String register(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return  "register";
        }
        else if(userIden.isAdmin()){
            return "redirect:userList";
        }
        else if(userIden.getUserID() > 0){
            return "redirect:match";
        }
        return "register";
    }

    //Not tested...
    @PostMapping("/registrationRequest")
    public String registrationRequest(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest webRequest){
        String email = webRequest.getParameter("email");
        String firstname = webRequest.getParameter("firstname");
        String surname = webRequest.getParameter("surname");
        int sex = Integer.parseInt(webRequest.getParameter("sex"));
        String birthdate = webRequest.getParameter("birthdate");
        String username = webRequest.getParameter("username");
        String password = webRequest.getParameter("password");
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            int userID = profileHandler.createProfile(email, firstname, surname, username, password, sex, birthdate);
            if(userID > 0){
                userIden = checkUserService.createUserIdentification(userID, false);
                Cookie cookie = new Cookie("cookieID", userIden.getCookieID());
                cookie.setMaxAge(2592000);
                response.addCookie(cookie);
                return "redirect:profile";
            }
            else{
                return "redirect:login";
            }
        }
        else if(userIden.isAdmin()){
            return "redirect:userList";
        }
        return "redirect:match";
    }

    //Not tested
    @GetMapping("/")
    public String index(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "redirect:login";
    }

    //Not tested
    @GetMapping("/match")
    public String match(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            return "redirect:userList";
        }
        else if(userIden.getUserID() < 0){
            return "redirect:login";
        }
        candidateService.getMatchCandidate(modelMap,userIden);
        modelMap.addAttribute("userIden", userIden);
        return "match";
    }

    //Not tested
    @GetMapping("/likeUser")
    public String likeUser(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        if(userIden.isAdmin()){
            return "redirect:userList";
        }
        int secondaryID = Integer.parseInt(request.getParameter("secondaryID"));
        System.out.println(secondaryID);
        candidateService.addCandidate(userIden.getUserID(),secondaryID);
        return "redirect:match";
    }

    //Not tested
    @PostMapping("/nextUser")
    public String nextUser(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return match(cookieID,response,modelMap);
    }

    //Not tested
    @GetMapping("/inbox")
    public String inbox(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if (userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            return "redirect:userList";
        }
        else if(userIden.getUserID() < 0){
            return "redirect:login";
        }
        chatService.getAllUsersConversations(userIden.getUserID(), modelMap);
        modelMap.addAttribute("userIden", userIden);
        return "inbox";
    }

    //Not tested, check userID or admin?
    @GetMapping("/conversation")
    public String conversation(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            int conversationID = Integer.parseInt(request.getParameter("conversationID"));
            chatService.getConversationAdmin(conversationID, modelMap);
            chatService.getMessages(conversationID,modelMap);
            modelMap.addAttribute("userIden",userIden);
            return "conversation";
        }
        int conversationID = Integer.parseInt(request.getParameter("conversationID"));
        if(chatService.checkIfUserIsPartOfConversation(conversationID,userIden.getUserID())){
            chatService.getMessages(conversationID,modelMap);
            chatService.getConversation(conversationID,userIden.getUserID(),modelMap);
            modelMap.addAttribute("userIden",userIden);
            return "conversation";
        }
        else {
            return "redirect:inbox";
        }
    }

    //Not done, add inputCheck and userID/admin check?
    @GetMapping("/sendMessage")
    public String sendMessage(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if (userIden.isAdmin()){
            return "redirect:userList";
        }
        String message = request.getParameter("message");
        int conversationID = Integer.parseInt(request.getParameter("conversationID"));
        if (chatService.checkIfUserIsPartOfConversation(conversationID, userIden.getUserID())){
            chatService.addMessageToConversation(userIden.getUserID(),conversationID,message);
            return "forward:conversation";
        }
        else {
            return "redirect:inbox";
        }
    }

    //Not tested
    @GetMapping("/candidates")
    public String candidateList(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if (userIden.isAdmin())
        {
            return "redirect:userList";
        }
        candidateService.getCandidates(modelMap,userIden.getUserID());
        return "candidate-list";
    }

    //Not tested, missing service method, add profileID request
    @GetMapping("/profile")
    public String profile(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            int profileID = Integer.parseInt(request.getParameter("profileID"));
            profileHandler.getProfile(profileID, modelMap);
            return "profile";
        }
        profileHandler.getProfile(userIden.getUserID(), modelMap);
        return "profile";
    }

    @PostMapping("/saveTextChanges")
    public String saveTextChanges(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        return "redirect:profile";
    }

    @PostMapping("/uploadPicture")
    public String uploadPicture(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            return "redirect:userList";
        }
        return "redirect:profile";
    }

    public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/resources/static/user_photos";

    @RequestMapping("PhotoTest")
    public String UploadPage() {
        return "Uploadview";
    }


    @RequestMapping("/upload")
    public String upload(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, Model model,@RequestParam("file") MultipartFile file) {
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "login";
        }
        //StringBuilder fileNames = new StringBuilder();
        // String fileNames = "";
        String contentType = file.getContentType();
        if (contentType.startsWith("image/")) {
            String fileName = "photoTilhoendeUserId" + userIden.getUserID();
            // --||-- = userIden.getUserID() "";
            Path filePath = Paths.get(uploadDirectory, fileName);
            //fileNames.append(file.getOriginalFilename() + " ");
            // fileName = fileName + file.getOriginalFilename;

            try {
                Files.write(filePath, file.getBytes());
                // tager imod filens name og vej og files bytes(indhold)
            } catch (IOException e) {
                e.printStackTrace();
            }

            model.addAttribute("msg", "Successfully uploaded files "+fileName);
            model.addAttribute("filepath", uploadDirectory + fileName);
            return "UploadStatusView";
        }
        return "ErrorPage";
    }


    @Configuration
    public class ResourceConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(final ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/src/main/resources/static/user_photos**").addResourceLocations("file:/src/main/resources/static/user_photos");
        }
    }

    @PostMapping("/deletePicture")
    public String deletePicture(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        return "redirect:profile";
    }

    //Not tested
    @GetMapping("/logout")
    public String logOut(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        checkUserService.removeUserIdentification(cookieID);
        return "redirect:login";
    }

    //Not done
    @PostMapping("/deleteProfile")
    public String deleteProfile(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){

            return "redirect:userList";
        }

        return "redirect:candidates";
    }

    //Not done, Check if userID in conversation?
    @PostMapping("/deleteConversation")
    public String deleteConversation(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if (userIden.isAdmin()){
            return "redirect:userList";
        }
        int conversationID = Integer.parseInt(request.getParameter("conversationID"));
        if (chatService.checkIfUserIsPartOfConversation(conversationID,userIden.getUserID())){
            chatService.deleteConversation(conversationID);
//candidateService.deleteCandidate(); How to get secondaryUserID?
        }

        return "redirect:inbox";
    }

    @PostMapping("/deleteCandidate")
    public String deleteCandidate(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            return "redirect:userList";
        }
        int secondaryID = Integer.parseInt(request.getParameter("secondaryID"));
        candidateService.deleteCandidate(secondaryID);
//Check if there is a conversation, delete if there is
        return "redirect:candidates";
    }

    @GetMapping("/userList")
    public String userList(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(!userIden.isAdmin()){
            return "redirect:match";
        }
        candidateService.getAllUsersLikeUsername("",modelMap);
        return "candidate-list";
    }

    @GetMapping("/searchUser")
    public String searchUser(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(!userIden.isAdmin()){
            return "redirect:candidates";
        }
        String username = request.getParameter("username");
        candidateService.getAllUsersLikeUsername(username,modelMap);
        return "candidate-list";
    }

    //Not done
    @GetMapping("/userProfile")
    public String userProfile(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(!userIden.isAdmin()){
            return "redirect:match";
        }
        int userID = Integer.parseInt(request.getParameter("userID"));
        profileHandler.getProfile(userID,modelMap);
        return "profile";
    }

    @GetMapping("/userInbox")
    public String userInbox(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(!userIden.isAdmin()){
            return "redirect:inbox";
        }
        int userID = Integer.parseInt(request.getParameter("userID"));
        chatService.getAllUsersConversations(userID,modelMap);
        return "inbox";
    }
}
