package com.example.demo.services;

import com.example.demo.models.ConversationPreview;

import java.util.ArrayList;

public class ChatService {

    private JDBCChatService jdbc;

    public ChatService() {
        jdbc = new JDBCChatService();
        jdbc.setConnection();
    }


    public void createConversation(int userID, int secondaryID){
        //Creates conversation and relation from users to it
    }

    public ArrayList<ConversationPreview> getAllUsersConversations(int userID){
        // Gets ConversationPreviews of userID
        return new ArrayList<>();
    }

    public void addMessageToConversation(int userID, int conversationID, String message){

    }

    public void deleteConversation(int conversationID){
        //Deletes all message to conversation
        //Deletes conversation
        //Deletes user-convo-relation
    }
}
