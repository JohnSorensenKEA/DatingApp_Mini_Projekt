package com.example.demo.services;

import java.sql.*;

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
    public void getUserConversations(int userID){
        String selectStatement = "SELECT";
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
