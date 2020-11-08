package com.example.demo.services;

public class LoginService {

    private JDBCLoginService jdbc;

    public LoginService() {
        jdbc = new JDBCLoginService();
        jdbc.setConnection();
    }

    public int getUserID(String username, String password){
        return -1;
    }

    public boolean checkAdmin(String username, String password){
        return false;
    }
}
