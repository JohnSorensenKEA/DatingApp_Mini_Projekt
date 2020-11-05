package com.example.demo.models;

public class Candidate {
    private String secondaryUsername;
    private String secondaryPhoto;
    private String relationID;

    public Candidate(String secondaryUsername, String secondaryPhoto, String relationID) {
        this.secondaryUsername = secondaryUsername;
        this.secondaryPhoto = secondaryPhoto;
        this.relationID = relationID;
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

    public String getRelationID() {
        return relationID;
    }

    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }
}
