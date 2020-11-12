package com.example.demo.services;

import com.example.demo.models.Candidate;
import com.example.demo.models.SecondaryUser;
import com.example.demo.models.UserIdentification;
import com.example.demo.repositories.JDBCCandidateService;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;

public class CandidateService {

    private JDBCCandidateService jdbc;

    public CandidateService() {
        jdbc = new JDBCCandidateService();
        jdbc.setConnection();
    }

    public void getCandidates(ModelMap modelMap, int userID){
        ArrayList<Candidate> list = jdbc.getUsersCandidates(userID);
        if (list.size() == 0){
            modelMap.addAttribute("errorMessage","Du har ikke like nogen endnu.");
        }
        else {
            modelMap.addAttribute("candidateList", list);
        }

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

    private boolean makeMatchCheck(int userID, int secondaryID){
        return jdbc.checkIfMatch(userID, secondaryID);
    }

    public void deleteCandidate(int userID, int secondaryID){
        jdbc.deleteLike(userID, secondaryID);
    }

    public void deleteAllUserReferencedCandidates(int userID){
        //Deletes all "like" and relations referencing to userID
    }

    public boolean addCandidate(int userID, int secondaryID){
        jdbc.createLike(userID,secondaryID);
        return makeMatchCheck(userID,secondaryID);
    }

    public void getAllUsersLikeUsername(String username, ModelMap modelMap){
        ArrayList<Candidate> list = jdbc.getAllUsersLikeUsername(username);
        modelMap.addAttribute("candidateList",list);
    }
}
