package com.example.demo.services;

import com.example.demo.models.SecondaryUser;
import com.example.demo.models.UserIdentification;
import com.example.demo.repositories.JDBCCandidateService;
import org.springframework.ui.ModelMap;

public class CandidateService {

    private JDBCCandidateService jdbc;

    public CandidateService() {
        jdbc = new JDBCCandidateService();
        jdbc.setConnection();
    }

    public void getCandidates(ModelMap modelMap, int userID){
        modelMap.addAttribute("candidateList",jdbc.getUsersCandidates(userID));
    }

    public void getMatchCandidate(ModelMap modelMap, UserIdentification userIdentification){
        SecondaryUser su = jdbc.getRandomUnlikedUser(userIdentification.getUserID());

        if(su == null){
            modelMap.addAttribute("errorMessage", "Kunne ikke finde et nyt match, prøv igen senere");
        }
        else {
            modelMap.addAttribute("secondaryUser",su);
        }
        //Puts a SecondaryUser in modelmap
        //Secondary is not liked by user
        //Random secondary
    }

    private void makeMatchCheck(int likeID){
        //Get primary & Secondary user from likeID
        //Reverse and check if Secondary has liked Primary
        //Send to ChatService
    }

    public void deleteCandidate(int likeID){
        //Deletes "like" and relation
    }

    public void deleteAllUserReferencedCandidates(int userID){
        //Deletes all "like" and relations referencing to userID
    }

    public void addCandidate(int userID, int secondaryID){
        jdbc.createLike(userID,secondaryID);
    }
}
