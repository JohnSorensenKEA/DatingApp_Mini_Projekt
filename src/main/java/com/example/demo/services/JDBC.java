package com.example.demo.services;

import java.sql.*;

public class JDBC {

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

    //CandidateService
    public void createLike(int userID, int secondaryID){

    }

    //ChatService
    public void createMessage(int conversationID, int authorID, String message){

    }
    public void createConversation(int userID, int secondaryID){

    }

    //LoginService
    public void getUserFromLogin(){

    }

    //ProfileHandler
    public void createProfile(){

    }

    public ResultSet getUserInfo(int userID){
        ResultSet res = null;
        String selectSQL = "SELECT * FROM users WHERE user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, userID);
            res =preparedStatement.executeQuery();
        }
        catch (SQLException e){
            System.out.println("GetUserInfoError="+e.getMessage());
        }
        return res;
    }

}
