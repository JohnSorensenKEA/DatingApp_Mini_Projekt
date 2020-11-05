package com.example.demo.controllers;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @GetMapping("/test")
    public String jegTesterLigeNogetMedCookiesBTW(@CookieValue(value = "cookieID", defaultValue = "") String cookieID){

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
