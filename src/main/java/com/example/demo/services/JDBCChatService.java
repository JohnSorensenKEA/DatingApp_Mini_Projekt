package com.example.demo.services;

import com.example.demo.models.Conversation;
import com.example.demo.models.ConversationPreview;
import com.example.demo.models.Message;
import org.springframework.ui.ModelMap;

import java.sql.*;
import java.util.ArrayList;

public class JDBCChatService {
    private Connection connection;

    public boolean setConnection(){
        boolean bres = false;
        String url = "jdbc:mysql://localhost:3306/clm1?serverTimezone=UTC";
        try{
            connection = DriverManager.getConnection(url,"clm_server","clm_server");
            bres = true;
        }
        catch (SQLException e){
            System.out.println("No connection to sever="+e.getMessage());
        }
        return bres;
    }

    //ChatService
    public void createMessage(int conversationID, int authorID, String message){
        String insertStatement = "INSERT INTO messages (conversation_id, message_text, message_author_id) VALUES (?, ?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, conversationID);
            preparedStatement.setString(2, message);
            preparedStatement.setInt(3, authorID);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Failed to create new message="+e.getMessage());
        }
    }
    public void createConversation(int userID, int secondaryID, String date){
        String insertStatement = "INSERT INTO conversations (date) VALUES (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setString(1, date);
            preparedStatement.executeUpdate();

            int conversationID = getLastCreatedID();

            createConversationRelation(conversationID,userID);
            createConversationRelation(conversationID,secondaryID);
        }
        catch (SQLException e) {
            System.out.println("Failed to create conversation="+e.getMessage());
        }
    }

    public void createConversationRelation(int conversationID, int userID){
        String insertStatement = "INSERT INTO user_conversation_relations (conversation_id, user_id) VALUES (?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, conversationID);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Conversation relation creation failed="+e.getMessage());
        }
    }

    public void deleteMessages(int conversationID){
        String deleteStatement = "DELETE FROM messages WHERE conversation_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, conversationID);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Failed to delete messages="+e.getMessage());
        }
    }

    //???
    public void deleteConversation(int conversationID){
        String deleteStatement =
                "DELETE conversations, user_conversation_relations FROM user_conversation_relations " +
                "JOIN conversations using(conversation_id) " +
                "WHERE conversation_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, conversationID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Failed to delete conversation="+e.getMessage());
        }
    }

    //Welp, Use join on max date
    public ArrayList<ConversationPreview> getUserConversations(int userID){
        String selectStatement = "SELECT";
        ArrayList<ConversationPreview> list = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                //ConversationPreview
            }
        }
        catch (SQLException e){
            System.out.println("Failed to get conversations="+e.getMessage());
        }
        return list;
    }

    public ArrayList<Message> getMessages(int conversationID){
        String selectStatement =
                "SELECT * FROM messages " +
                "WHERE conversation_id = ? " +
                "ORDER BY message_created_at ASC";
        ArrayList<Message> list = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, conversationID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String messageText = resultSet.getString("message_text");
                int authorID = resultSet.getInt("message_author_id");
                String dateTime = resultSet.getString("message_created_at");
//add
                Message message = null;
                list.add(message);
            }
        }
        catch (SQLException e){
            System.out.println("Failed getting messages="+e.getMessage());
        }
        return list;
    }

    public Conversation getConversation(int conversationID, int userID){
        String selectStatement =
                "SELECT * FROM user_conversation_relations uc " +
                "JOIN users uu using(user_id) " +
                "WHERE uc.conversation_id = ? AND uc.user_id != ?";
        Conversation conversation = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1,conversationID);
            preparedStatement.setInt(2, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
//add
        }
        catch (SQLException e){
            System.out.println("Failed getting conversation="+e.getMessage());
        }
        return conversation;
    }

    //Other
    public int getLastCreatedID(){ //Returns AI ID of last added row
        String selectStatement = "SELECT last_insert_id()";
        int res = -1;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);

            ResultSet resultSet = preparedStatement.executeQuery();

            res = resultSet.getInt("last_insert_id()");
        }
        catch (SQLException e){
            System.out.println("last_insert_id() error="+e.getMessage());
        }
        return res;
    }
}
