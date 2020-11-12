package com.example.demo.models;

public class Candidate {
    private String secondaryUsername;
    private String secondaryPhoto;
    private int secondaryID;
    private int sex;
    private String birthdate;

    public Candidate(String secondaryUsername, String secondaryPhoto, int relationID, int sex, String birthdate) {
        this.secondaryUsername = secondaryUsername;
        this.secondaryPhoto = secondaryPhoto;
        this.secondaryID = relationID;
        this.sex = sex;
        this.birthdate = birthdate;
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

    public int getSecondaryID() {
        return secondaryID;
    }

    public void setSecondaryID(int secondaryID) {
        this.secondaryID = secondaryID;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getSex(){ return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
