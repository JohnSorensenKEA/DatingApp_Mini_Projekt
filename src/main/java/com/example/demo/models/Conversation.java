package com.example.demo.models;

public class Conversation {
    private String conversationID;
    private String secondaryUserID;
    private String secondaryUsername;
    private String secondaryPhoto;

    public Conversation(String conversationID, String secondaryUserID, String secondaryUsername, String secondaryPhoto) {
        this.conversationID = conversationID;
        this.secondaryUserID = secondaryUserID;
        this.secondaryUsername = secondaryUsername;
        this.secondaryPhoto = secondaryPhoto;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getSecondaryUserID() {
        return secondaryUserID;
    }

    public void setSecondaryUserID(String secondaryUserID) {
        this.secondaryUserID = secondaryUserID;
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
}
