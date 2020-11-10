package com.example.demo.services;

import com.example.demo.repositories.JDBCLoginService;

public class LoginService {

    private JDBCLoginService jdbc;

    public LoginService() {
        jdbc = new JDBCLoginService();
        jdbc.setConnection();
    }

    public int getUserID(String username, String password){
        return jdbc.getUserFromLogin(username,password);
    }

    public boolean checkAdmin(String username, String password){
        return jdbc.getAdminFromLogin(username,password);
    }
}
