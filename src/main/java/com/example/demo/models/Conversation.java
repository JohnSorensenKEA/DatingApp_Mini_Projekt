package com.example.demo.models;

public class Conversation {
    private String Conversation_id;
    private String Secondary_user_id;
    private String Secondary_username;
    private String Secondary_photo;

    public Conversation(String conversation_id, String secondary_user_id, String secondary_username,
                        String secondary_photo) {
        Conversation_id = conversation_id;
        Secondary_user_id = secondary_user_id;
        Secondary_username = secondary_username;
        Secondary_photo = secondary_photo;
    }

    public String getConversation_id() {
        return Conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        Conversation_id = conversation_id;
    }

    public String getSecondary_user_id() {
        return Secondary_user_id;
    }

    public void setSecondary_user_id(String secondary_user_id) {
        Secondary_user_id = secondary_user_id;
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
}
