package com.example.demo.services;

import com.example.demo.repositories.LoginRepository;

public class LoginService {

    private LoginRepository jdbc;

    public LoginService() {
        jdbc = new LoginRepository();
        jdbc.setConnection();
    }

    public int getUserID(String username, String password){
        return jdbc.getUserFromLogin(username,password);
    }

    public boolean checkAdmin(String username, String password){
        return jdbc.getAdminFromLogin(username,password);
    }
}
