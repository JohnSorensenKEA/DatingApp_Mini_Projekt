package com.example.demo.models;

public class ConversationPreview {
    private int conversationID;
    private String secondaryUsername;
    private String secondaryPhoto;
    private String latestMessage;
    private String latestMessageDateTime;

    public ConversationPreview(int conversationID, String secondaryUsername, String secondaryPhoto, String latestMessage, String latestMessageDateTime) {
        this.conversationID = conversationID;
        this.secondaryUsername = secondaryUsername;
        this.secondaryPhoto = secondaryPhoto;
        this.latestMessage = latestMessage;
        this.latestMessageDateTime = latestMessageDateTime;
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
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

    public String getLatestMessageDateTime() {
        return latestMessageDateTime;
    }

    public void setLatestMessageDateTime(String latestMessageDateTime) {
        this.latestMessageDateTime = latestMessageDateTime;
    }
}
