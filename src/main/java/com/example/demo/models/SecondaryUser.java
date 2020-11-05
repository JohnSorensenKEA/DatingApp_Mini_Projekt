package com.example.demo.models;

public class SecondaryUser {
    private String secondaryUsername;
    private String secondaryPhoto;
    private String secondaryDescription;
    private String secondaryUserID;
    private String secondarySex;
    private String secondaryFavorites;
    private int secondaryAge;

    public SecondaryUser(String secondaryUsername, String secondaryPhoto, String secondaryDescription, String secondaryUserID, String secondarySex, String secondaryFavorites, int secondaryAge) {
        this.secondaryUsername = secondaryUsername;
        this.secondaryPhoto = secondaryPhoto;
        this.secondaryDescription = secondaryDescription;
        this.secondaryUserID = secondaryUserID;
        this.secondarySex = secondarySex;
        this.secondaryFavorites = secondaryFavorites;
        this.secondaryAge = secondaryAge;
    }

    public String getSecondaryUsername() {
        return secondaryUsername;
    }

    public void setSecondaryUsername(String secondaryUsername) {
        this.secondaryUsername = secondaryUsername;
    }

    public String getSecondaryPhoto() {
        return secondaryPhoto;
    }

    public void setSecondaryPhoto(String secondaryPhoto) {
        this.secondaryPhoto = secondaryPhoto;
    }

    public String getSecondaryDescription() {
        return secondaryDescription;
    }

    public void setSecondaryDescription(String secondaryDescription) {
        this.secondaryDescription = secondaryDescription;
    }

    public String getSecondaryUserID() {
        return secondaryUserID;
    }

    public void setSecondaryUserID(String secondaryUserID) {
        this.secondaryUserID = secondaryUserID;
    }

    public String getSecondarySex() {
        return secondarySex;
    }

    public void setSecondarySex(String secondarySex) {
        this.secondarySex = secondarySex;
    }

    public String getSecondaryFavorites() {
        return secondaryFavorites;
    }

    public void setSecondaryFavorites(String secondaryFavorites) {
        this.secondaryFavorites = secondaryFavorites;
    }

    public int getSecondaryAge() {
        return secondaryAge;
    }

    public void setSecondaryAge(int secondaryAge) {
        this.secondaryAge = secondaryAge;
    }
}
