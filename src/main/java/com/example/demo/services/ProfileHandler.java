package com.example.demo.services;

import com.example.demo.repositories.JDBCProfileService;

public class ProfileHandler {

    private JDBCProfileService jdbc;

    public ProfileHandler() {
        jdbc = new JDBCProfileService();
        jdbc.setConnection();
    }

    public int createProfile(String email, String firstname, String surname, String username, String password, int sex, String birthdate){
        return jdbc.createProfile(email, firstname, surname, username, password, sex, birthdate);
    }



}
