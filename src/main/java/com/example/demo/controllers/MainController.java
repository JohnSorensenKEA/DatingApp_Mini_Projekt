package com.example.demo.controllers;

import com.example.demo.models.UserIdentification;
import com.example.demo.services.*;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @GetMapping("/login")
    public String login(){
        return "";
    }

    @PostMapping("/loginRequest")
    public String loginRequest(){
        return "";
    }

    @GetMapping("/register")
    public String register(){
        return "";
    }

    @PostMapping("/registrationRequest")
    public String registrationRequest(){
        return "";
    }

    @GetMapping("/")
    public String index(){
        return "";
    }

    @GetMapping("/match")
    public String match(){
        return "";
    }

    @PostMapping("/likeUser")
    public String likeUser(){
        return "";
    }

    @PostMapping("/nextUser")
    public String nextUser(){
        return "";
    }

    @GetMapping("/inbox")
    public String inbox(){
        return "";
    }

    @PostMapping("/conversation")
    public String conversation(){
        return "";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(){
        return "";
    }

    @GetMapping("/candidates")
    public String candidateList(){
        return "";
    }

    @GetMapping("/profile")
    public String profil(){
        return "";
    }

    @PostMapping("/saveTextChanges")
    public String saveTextChanges(){
        return "";
    }

    @PostMapping("/uploadPicture")
    public String uploadPicture(){
        return "";
    }

    @PostMapping("/deletePicture")
    public String deletePicture(){
        return "";
    }

    @PostMapping("/logout")
    public String logOut(){
        return "";
    }

    @PostMapping("/deleteProfile")
    public String deleteProfile(){
        return "";
    }

    @PostMapping("/deleteConversation")
    public String deleteConversation(){
        return "";
    }

    @PostMapping("/deleteCandidate")
    public String deleteCandidate(){
        return "";
    }

    @GetMapping("/userList")
    public String userList(){
        return "";
    }

    @PostMapping("/searchUser")
    public String searchUser(){
        return "";
    }

    @GetMapping("/userProfile")
    public String userProfile(){
        return "";
    }

    @GetMapping("/userInbox")
    public String userInbox(){
        return "";
    }

}
