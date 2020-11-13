package com.example.demo.services;

import com.example.demo.models.Profile;
import com.example.demo.repositories.ProfileRepository;
import org.springframework.ui.ModelMap;

public class ProfileHandler {

    private ProfileRepository jdbc;

    public ProfileHandler() {
        jdbc = new ProfileRepository();
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

    public void deleteProfile(int userID, ModelMap modelMap){
        ProfileRepository deleteProfile = jdbc.deleteProfile(userID);

        if (deleteProfile == null){
           modelMap.addAttribute("errorMessage", "Kunne ikke slette din profil");
        }
        else{
            modelMap.addAttribute("deleteProfile", deleteProfile);
        }
    }

    //True if taken
    public boolean checkIfUsernameIsTaken(String username){
        boolean b = false;
        if(jdbc.checkIfUserHasUsername(username)){
            b = true;
        }
        else if (jdbc.checkIfAdminHasUsername(username)) {
            b = true;
        }
        return b;
    }

    public void changeProfile(int userID, String firstname, String surname, int sex, String birthdate, String email, String username, String password, String description){
        jdbc.changeProfile(userID,firstname,surname,sex,birthdate,email,username,password,description);
    }

    public void changeKeywords(int userID, String keyword1, String keyword2, String keyword3){
        jdbc.changeKeywords(userID,keyword1,keyword2,keyword3);
    }
}
