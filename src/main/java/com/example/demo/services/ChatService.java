package com.example.demo.services;

import com.example.demo.models.Conversation;
import com.example.demo.models.ConversationPreview;
import com.example.demo.models.Message;
import com.example.demo.repositories.JDBCChatService;
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

    public void getAllUsersConversations(int userID, ModelMap modelMap){
        ArrayList<ConversationPreview> list = jdbc.getUserConversations(userID);
        if (list.size() == 0){
            modelMap.addAttribute("errorMessage", "Kunne ikke finde nogle samtaler.");
        }
        else{
            modelMap.addAttribute("conversationList", list);
        }
    }

    public void addMessageToConversation(int userID, int conversationID, String message){
        jdbc.createMessage(conversationID,userID,message);
    }

    public void deleteConversation(int conversationID){
        jdbc.deleteConversation(conversationID);
        //Deletes all message to conversation
        //Deletes conversation
        //Deletes user-convo-relation
    }

    public void getMessages(int conversationID, ModelMap modelMap){
        ArrayList<Message> list = jdbc.getMessages(conversationID);
        modelMap.addAttribute("messages",list);
    }

    public void getConversation(int conversationID, int userID, ModelMap  modelMap){
        Conversation conversation = jdbc.getConversation(conversationID,userID);
        modelMap.addAttribute("conversation",conversation);
    }

    public void getConversationAdmin(int conversationID, ModelMap modelMap){
        ArrayList<Conversation> list = jdbc.getConversationAdmin(conversationID);
        if (list.size() == 0){
            modelMap.addAttribute("errorMessage","Noget gik galt, mens vi prøvede at finde deltagerne i denne samtale");
        }
        else {
            modelMap.addAttribute("user1", list.get(0));
            modelMap.addAttribute("user2", list.get(1));
        }
    }

    public boolean checkIfUserIsPartOfConversation(int conversationID, int userID){
        return jdbc.checkIfUserIsInConversation(conversationID,userID);
    }
}
