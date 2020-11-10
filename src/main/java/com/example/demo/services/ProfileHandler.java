package com.example.demo.services;

import com.example.demo.repositories.JDBCProfileService;

public class ProfileHandler {

    private JDBCProfileService jdbc;

    public ProfileHandler() {
        jdbc = new JDBCProfileService();
        jdbc.setConnection();
    }


}
