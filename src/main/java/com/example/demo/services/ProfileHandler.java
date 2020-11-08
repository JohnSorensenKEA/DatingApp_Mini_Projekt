package com.example.demo.services;

public class ProfileHandler {

    private JDBCProfileService jdbc;

    public ProfileHandler() {
        jdbc = new JDBCProfileService();
        jdbc.setConnection();
    }


}
