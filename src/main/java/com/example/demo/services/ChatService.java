package com.example.demo.services;

import com.example.demo.models.ConversationPreview;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChatService {

    private JDBCChatService jdbc;
    private LocalDateTime dateTime;
    private DateTimeFormatter formatter;

    public ChatService() {
        jdbc = new JDBCChatService();
        jdbc.setConnection();

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        dateTime = LocalDateTime.now();
    }

    public void createConversation(int userID, int secondaryID){
        //Creates conversation and relation from users to it
        jdbc.createConversation(userID,secondaryID,formatter.format(dateTime));
    }

    public ArrayList<ConversationPreview> getAllUsersConversations(int userID){
        // Gets ConversationPreviews of userID
        return jdbc.getUserConversations(userID);
    }

    public void addMessageToConversation(int userID, int conversationID, String message){
        jdbc.createMessage(conversationID,userID,message);
    }

    public void deleteConversation(int conversationID){
        //Deletes all message to conversation
        //Deletes conversation
        //Deletes user-convo-relation
    }

    public void getMessages(int conversationID, ModelMap modelMap){

    }
}
