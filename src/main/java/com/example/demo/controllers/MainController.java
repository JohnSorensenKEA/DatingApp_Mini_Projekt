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
        deleteService = new DeleteService();
        photoHandler = new PhotoHandler();

        candidateService = new CandidateService(jdbc);
        chatService = new ChatService(jdbc);
        loginService = new LoginService(jdbc);
        profileHandler = new ProfileHandler(jdbc);
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


}
