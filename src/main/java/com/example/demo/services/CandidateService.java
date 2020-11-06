package com.example.demo.services;

import com.example.demo.models.UserIdentification;
import org.springframework.ui.ModelMap;

public class CandidateService {

    private JDBC jdbc;

    public CandidateService(JDBC jdbc) {
        this.jdbc = jdbc;
    }

    public void getCandidates(ModelMap modelMap, UserIdentification userIdentification){
        //Puts list of Candidate obj in modelmap
        //If user: liked users
        //If admin: all users
    }

    public void getMatchCandidate(ModelMap modelMap, UserIdentification userIdentification){
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
}
