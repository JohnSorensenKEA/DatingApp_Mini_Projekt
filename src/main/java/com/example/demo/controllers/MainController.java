package com.example.demo.controllers;

import com.example.demo.models.UserIdentification;
import com.example.demo.services.*;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class MainController {

    JDBC jdbc;

    CandidateService candidateService;
    ChatService chatService;
    CheckUserInput checkUserInput;
    CheckUserService checkUserService;
    DeleteService deleteService;
    LoginService loginService;
    PhotoHandler photoHandler;
    ProfileHandler profileHandler;

    public MainController(){
        jdbc = new JDBC();
        jdbc.setConnection();

        checkUserInput = new CheckUserInput();
        checkUserService = new CheckUserService();
        photoHandler = new PhotoHandler();

        candidateService = new CandidateService(jdbc);
        chatService = new ChatService(jdbc);
        loginService = new LoginService(jdbc);
        profileHandler = new ProfileHandler(jdbc);

        deleteService = new DeleteService(candidateService,chatService,checkUserService, profileHandler, photoHandler);
    }

    @GetMapping("/test")
    public String jegTesterLigeNogetMedCookiesBTW(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        System.out.println(cookieID);
        return "login";
    }

    @GetMapping("/get")
    public String cookieSender(HttpServletResponse response){
        Cookie cookie = new Cookie("cookieID","42");
        cookie.setMaxAge(2592000);
        response.addCookie(cookie);

        return "login";
    }

    @PostMapping("/gem")
    public String hentFil(HttpServletRequest request){

        var x = request.getParameter("fil");
        if (ServletFileUpload.isMultipartContent(request)){

        }

        return "register";
    }

    //Not tested
    @GetMapping("/login")
    public String login(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden.isAdmin()){
            return userList(cookieID, response, modelMap);
        }
        else if(userIden.getUserID() > 0){
            return match(cookieID,response,modelMap);
        }
        return "login";
    }

    //Not tested, loginService needs methods
    @PostMapping("/loginRequest")
    public String loginRequest(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap, WebRequest webRequest){
        String username = webRequest.getParameter("username");
        String password = webRequest.getParameter("password");
        UserIdentification userIden;
        if(loginService.checkAdmin(username,password)){
            userIden = checkUserService.createUserIdentification(-1, true);
            Cookie cookie = new Cookie("cookieID",userIden.getCookieID());
            response.addCookie(cookie);
            return userList(userIden.getCookieID(), response, modelMap);
        }
        else{
            int userID = loginService.getUserID(username,password);
            if(userID > 0){
                userIden = checkUserService.createUserIdentification(userID,false);
                Cookie cookie = new Cookie("cookieID",userIden.getCookieID());
                response.addCookie(cookie);
                return match(userIden.getCookieID(),response,modelMap);
            }
        }
        modelMap.addAttribute("errorMessage","Forkert Brugernavn eller Kodeord");
        return "login";
    }

    //Not tested
    @GetMapping("/register")
    public String register(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden.isAdmin()){
            return userList(cookieID, response, modelMap);
        }
        else if(userIden.getUserID() > 0){
            return match(cookieID,response,modelMap);
        }
        return "register";
    }

    //Not done...
    @PostMapping("/registrationRequest")
    public String registrationRequest(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    //Not tested
    @GetMapping("/")
    public String index(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return login(cookieID,response,modelMap);
    }

    //Not tested
    @GetMapping("/match")
    public String match(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        UserIdentification userIden = checkUserService.checkUser(cookieID);
        if(userIden.isAdmin()){
            return userList(cookieID, response, modelMap);
        }
        else if(userIden.getUserID() < 0){
            return "login";
        }
        candidateService.getMatchCandidate(modelMap,userIden);
        return "match";
    }

    @PostMapping("/likeUser")
    public String likeUser(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
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
        if(userIden.isAdmin()){
            return userList(cookieID, response, modelMap);
        }
        else if(userIden.getUserID() < 0){
            return "login";
        }
        candidateService.getCandidates(modelMap,userIden);
        return "inbox";
    }

    @PostMapping("/conversation")
    public String conversation(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @GetMapping("/candidates")
    public String candidateList(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @GetMapping("/profile")
    public String profil(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @PostMapping("/saveTextChanges")
    public String saveTextChanges(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @PostMapping("/uploadPicture")
    public String uploadPicture(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    public static String uploadDirectory = System.getProperty("user.dir")+ "/uploads";

    @RequestMapping("PhotoTest")
    public String UploadPage() {
        return "Uploadview";
    }

    @RequestMapping("/upload")
    public String upload(Model model,@RequestParam("files") MultipartFile[] files) {
        StringBuilder fileNames = new StringBuilder();
        // String fileNames = "";
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename()+" ");
            // fileName = fileName + file.getOriginalFilename;
            try {
                Files.write(fileNameAndPath, file.getBytes());
                // tager imod filens name og vej og files bytes(indhold)
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("msg", "Successfully uploaded files "+fileNames.toString());
        model.addAttribute("filepath", uploadDirectory + fileNames.toString());
        return "UploadStatusView";
    }


    @PostMapping("/deletePicture")
    public String deletePicture(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @PostMapping("/logout")
    public String logOut(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @PostMapping("/deleteProfile")
    public String deleteProfile(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @PostMapping("/deleteConversation")
    public String deleteConversation(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @PostMapping("/deleteCandidate")
    public String deleteCandidate(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @GetMapping("/userList")
    public String userList(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @PostMapping("/searchUser")
    public String searchUser(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @GetMapping("/userProfile")
    public String userProfile(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

    @GetMapping("/userInbox")
    public String userInbox(@CookieValue(value = "cookieID", defaultValue = "") String cookieID, HttpServletResponse response, ModelMap modelMap){
        return "";
    }

}
