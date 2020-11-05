package com.example.demo.models;

public class UserIdentification {
    private String cookieID;
    private int userID;
    private boolean admin;

    public UserIdentification(String cookieID, int userID, boolean admin) {
        this.cookieID = cookieID;
        this.userID = userID;
        this.admin = admin;
    }

    public String getCookieID() {
        return cookieID;
    }

    public void setCookieID(String cookieID) {
        this.cookieID = cookieID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
