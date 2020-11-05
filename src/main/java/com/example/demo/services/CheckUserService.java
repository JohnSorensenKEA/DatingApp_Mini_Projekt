package com.example.demo.services;

import com.example.demo.models.UserIdentification;

import java.util.ArrayList;
import java.util.Random;

public class CheckUserService {

    private ArrayList<UserIdentification> list = new ArrayList<>();

    public UserIdentification checkUser(String cookieID){
        for(UserIdentification u : list){
            if(u.getCookieID().equals(cookieID)){
                return u;
            }
        }
        return null;
    }

    public void removeUserIdentification(String cookieID){
        for(UserIdentification u : list){
            if(u.getCookieID().equals(cookieID)){
                list.remove(u);
                break;
            }
        }
    }

    public void removeUserIdentification(int userID){
        for(UserIdentification u : list){
            if(u.getUserID() == userID){
                list.remove(u);
                break;
            }
        }
    }

    public UserIdentification createUserIdentification(int userID, boolean admin){
        String cookieID = createCookieID();
        UserIdentification userIdentification = new UserIdentification(cookieID,userID,admin);
        list.add(userIdentification);
        return userIdentification;
    }

    public String createCookieID(){
        String res = "";
        Random rand = new Random();
        for(int i = 0; i < 10; i++){
           res += rand.nextInt(10);
        }
        return res;
    }
}
