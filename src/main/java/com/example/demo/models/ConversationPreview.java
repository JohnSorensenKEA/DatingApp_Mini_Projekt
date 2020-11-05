package com.example.demo.models;

public class ConversationPreview {
    private String conversationID;
    private String secondaryUsername;
    private String secondaryPhoto;
    private String latestMessage;

    public ConversationPreview(String conversationID, String secondaryUsername, String secondaryPhoto, String latestMessage) {
        this.conversationID = conversationID;
        this.secondaryUsername = secondaryUsername;
        this.secondaryPhoto = secondaryPhoto;
        this.latestMessage = latestMessage;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
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

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }
}
