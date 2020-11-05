package com.example.demo.services;

public class LoginService {

    private JDBC jdbc;

    public LoginService(JDBC jdbc) {
        this.jdbc = jdbc;
    }

    public int getUserID(String username, String password){
        return -1;
    }

    public boolean checkAdmin(String username, String password){
        return false;
    }
}
