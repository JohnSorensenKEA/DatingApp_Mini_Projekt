package com.example.demo.models;

public class ConversationPreview {
    private String Conversation_id;
    private String Secondary_username;
    private String Secondary_photo;
    private String latest_message;

    public ConversationPreview(String conversation_id, String secondary_username, String secondary_photo,
                               String latest_message) {
        Conversation_id = conversation_id;
        Secondary_username = secondary_username;
        Secondary_photo = secondary_photo;
        this.latest_message = latest_message;
    }

    public String getConversation_id() {
        return Conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        Conversation_id = conversation_id;
    }

    public String getSecondary_username() {
        return Secondary_username;
    }

    public void setSecondary_username(String secondary_username) {
        Secondary_username = secondary_username;
    }

    public String getSecondary_photo() {
        return Secondary_photo;
    }

    public void setSecondary_photo(String secondary_photo) {
        Secondary_photo = secondary_photo;
    }

    public String getLatest_message() {
        return latest_message;
    }

    public void setLatest_message(String latest_message) {
        this.latest_message = latest_message;
    }
}
