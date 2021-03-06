package com.example.demo.controllers;

import com.example.demo.models.UserIdentification;
import com.example.demo.services.*;
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
import javax.servlet.http.HttpServletResponse;

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

    //UserIdentifying
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

    //UserIdentifying
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

    //UserIdentifying
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

    //UserIdentifying
    @PostMapping("/registrationRequest")
    public String registrationRequest(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest webRequest){
        String email = webRequest.getParameter("email");
        String firstname = webRequest.getParameter("firstname");
        String surname = webRequest.getParameter("surname");
        int sex = Integer.parseInt(webRequest.getParameter("sex"));
        String year = webRequest.getParameter("year");
        String month = webRequest.getParameter("month");
        String day = webRequest.getParameter("day");
        String birthdate = checkUserInput.checkDate(year,month,day);
        String username = webRequest.getParameter("username");
        String password = webRequest.getParameter("password");
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null && birthdate != null || checkUserInput.checkRegistration(firstname,surname,email,username,password)){
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

    //UserIdentifying
    @GetMapping("/")
    public String index(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "redirect:login";
    }

    //Match
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

    //Match
    @GetMapping("/likeUser")
    public String likeUser(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            return "redirect:userList";
        }
        int secondaryID = Integer.parseInt(request.getParameter("secondaryID"));
        boolean createConversation = candidateService.addCandidate(userIden.getUserID(),secondaryID);
        if (createConversation){
            chatService.createConversation(userIden.getUserID(), secondaryID);
        }
        return "redirect:match";
    }

    //Match
    @PostMapping("/nextUser")
    public String nextUser(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return match(cookieID,response,modelMap);
    }

    //Chat
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

    //Chat
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

    //Chat
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
        if (chatService.checkIfUserIsPartOfConversation(conversationID, userIden.getUserID()) && checkUserInput.checkMessageSize(message)){
            chatService.addMessageToConversation(userIden.getUserID(),conversationID,message);
            return "forward:conversation";
        }
        else {
            return "redirect:inbox";
        }
    }

    //Candidate*
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
        modelMap.addAttribute("userIden", userIden);
        return "candidate-list";
    }

    //Profile
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
        else if(userIden.getUserID() < 0){
            return "redirect:login";
        }
        profileHandler.getProfile(userIden.getUserID(), modelMap);
        modelMap.addAttribute("userIden", userIden);
        return "profile";
    }

    //Profile
    @GetMapping("/saveTextChanges")
    public String saveTextChanges(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        String firstname = request.getParameter("firstname");
        String surname = request.getParameter("surname");
        int sex = Integer.parseInt(request.getParameter("sex"));
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String description = request.getParameter("description");
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            int userID = Integer.parseInt(request.getParameter("userID"));
            String birthdate = checkUserInput.checkDate(year,month,day);
            boolean b = checkUserInput.checkChangesToProfile(firstname, surname, email, username, password, description);
            if (b && birthdate != null){
                profileHandler.changeProfile(userID, firstname,surname,sex,birthdate,email,username,password,description);
            }
            return "redirect:userList";
        }
        String birthdate = checkUserInput.checkDate(year,month,day);
        boolean b = checkUserInput.checkChangesToProfile(firstname, surname, email, username, password, description);
        if (b && birthdate != null){
            profileHandler.changeProfile(userIden.getUserID(), firstname,surname,sex,birthdate,email,username,password,description);
        }

        return "redirect:profile";
    }

    //Profile
    @GetMapping("/saveKeywords")
    public String saveKeywords(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        String keyword1 = request.getParameter("keyword1");
        String keyword2 = request.getParameter("keyword2");
        String keyword3 = request.getParameter("keyword3");
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            int userID = Integer.parseInt(request.getParameter("userID"));
            if(checkUserInput.checkKeywords(keyword1,keyword2,keyword3)){
                profileHandler.changeKeywords(userID, keyword1,keyword2,keyword3);
            }
            return "redirect:userList";
        }
        if(checkUserInput.checkKeywords(keyword1,keyword2,keyword3)){
            profileHandler.changeKeywords(userIden.getUserID(), keyword1,keyword2,keyword3);
        }
        return "redirect:profile";
    }

    public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/resources/static/user_photos";

    //Profile
    @RequestMapping("/upload")
    public String upload(@CookieValue(value = "cookieID", defaultValue = "") String cookieID,
                         Model model,@RequestParam("file") MultipartFile file, @RequestParam("userID") String userIDString, @RequestParam("photoName") String photoName) {
        UserIdentification userIden = checkUserService.checkUser(cookieID);

        if (userIden == null) {
            return "login";
        }
        else if(userIden.isAdmin()){
            int userID = Integer.parseInt(userIDString);
            photoHandler.deletePhoto(photoName);
            String filename = photoHandler.takePhoto(file,file.getOriginalFilename(), userID);
            if (filename != null){
                profileHandler.changePhoto(userIden.getUserID(),filename);
            }
            return "redirect:userList";
        }
        photoHandler.deletePhoto(photoName);
        System.out.println(photoName);
        String filename = photoHandler.takePhoto(file,file.getOriginalFilename(), userIden.getUserID());
        if (filename != null){
            profileHandler.changePhoto(userIden.getUserID(),filename);
        }
        return "redirect:profile";
    }

    @Configuration
    public class ResourceConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(final ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/src/main/resources/static/user_photos**").addResourceLocations("file:/src/main/resources/static/user_photos");
        }
    }

    //Profile
    @GetMapping("/deletePicture")
    public String deletePicture(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        String photoName = request.getParameter("photoName");
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            int userID = Integer.parseInt(request.getParameter("userID"));
            photoHandler.deletePhoto(photoName);
            profileHandler.changePhoto(userID,"user_photos/stock_photo.png");
            return "redirect:userList";
        }
        photoHandler.deletePhoto(photoName);
        profileHandler.changePhoto(userIden.getUserID(),"user_photos/stock_photo.png");
        return "redirect:profile";
    }

    //UserIdentifying
    @GetMapping("/logout")
    public String logOut(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        checkUserService.removeUserIdentification(cookieID);
        return "redirect:login";
    }

    //Profile
    @PostMapping("/deleteProfile")
    public String deleteProfile(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(userIden.isAdmin()){
            int userID = Integer.parseInt(request.getParameter("userID"));
            chatService.deleteAllUsersConversations(userID);
            profileHandler.deleteProfile(userID, modelMap);
            checkUserService.removeUserIdentification(userID);
            return "redirect:userList";
        }
        chatService.deleteAllUsersConversations(userIden.getUserID());
        profileHandler.deleteProfile(userIden.getUserID(), modelMap);
        checkUserService.removeUserIdentification(userIden.getCookieID());
        return "redirect:login";
    }

    //Chat
    @GetMapping("/deleteConversation")
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
            int secondaryID = chatService.getSecondaryIDFromConversation(conversationID, userIden.getUserID());
            chatService.deleteConversation(conversationID);
            candidateService.deleteCandidate(userIden.getUserID(),secondaryID);
        }

        return "redirect:inbox";
    }

    //Admin
    @GetMapping("/userList")
    public String userList(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(!userIden.isAdmin()){
            return "redirect:match";
        }
        modelMap.addAttribute("userIden", userIden);
        candidateService.getAllUsersLikeUsername("",modelMap);
        return "candidate-list";
    }

    //Admin
    @GetMapping("/searchUser")
    public String searchUser(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(!userIden.isAdmin()){
            return "redirect:candidates";
        }
        modelMap.addAttribute("userIden", userIden);
        String username = request.getParameter("username");
        candidateService.getAllUsersLikeUsername(username,modelMap);
        return "candidate-list";
    }

    //Admin
    @GetMapping("/userProfile")
    public String userProfile(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(!userIden.isAdmin()){
            return "redirect:match";
        }
        modelMap.addAttribute("userIden", userIden);
        int userID = Integer.parseInt(request.getParameter("userID"));
        profileHandler.getProfile(userID,modelMap);
        return "profile";
    }

    //Admin
    @GetMapping("/userInbox")
    public String userInbox(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest request){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden == null){
            return "redirect:login";
        }
        else if(!userIden.isAdmin()){
            return "redirect:inbox";
        }
        modelMap.addAttribute("userIden", userIden);
        int userID = Integer.parseInt(request.getParameter("userID"));
        chatService.getAllUsersConversations(userID,modelMap);
        return "inbox";
    }
}
