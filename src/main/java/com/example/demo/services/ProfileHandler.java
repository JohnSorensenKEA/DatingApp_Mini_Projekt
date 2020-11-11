package com.example.demo.services;

import com.example.demo.models.Profile;
import com.example.demo.repositories.JDBCProfileService;
import org.springframework.ui.ModelMap;

public class ProfileHandler {

    private JDBCProfileService jdbc;

    public ProfileHandler() {
        jdbc = new JDBCProfileService();
        jdbc.setConnection();
    }

    public int createProfile(String email, String firstname, String surname, String username, String password, int sex, String birthdate){
        return jdbc.createProfile(email, firstname, surname, username, password, sex, birthdate);
    }

    public void getProfile(int userID, ModelMap modelMap){
        Profile profile = jdbc.getUserInfo(userID);

        if (profile == null){
            modelMap.addAttribute("errorMessage", "Kunne ikke finde din profil");
        }
        else {
            modelMap.addAttribute("profile", profile);
        }
    }


}
