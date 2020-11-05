package com.example.demo.models;

public class Conversation {
    private int conversationID;
    private int secondaryUserID;
    private String secondaryUsername;
    private String secondaryPhoto;

    public Conversation(int conversationID, int secondaryUserID, String secondaryUsername, String secondaryPhoto) {
        this.conversationID = conversationID;
        this.secondaryUserID = secondaryUserID;
        this.secondaryUsername = secondaryUsername;
        this.secondaryPhoto = secondaryPhoto;
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }

    public int getSecondaryUserID() {
        return secondaryUserID;
    }

    public void setSecondaryUserID(int secondaryUserID) {
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
