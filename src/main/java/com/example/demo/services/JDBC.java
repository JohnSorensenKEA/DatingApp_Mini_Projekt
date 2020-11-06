package com.example.demo.services;

import com.example.demo.models.Candidate;

import java.sql.*;
import java.util.ArrayList;

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
        String insertStatement = "INSERT INTO likes (user_id) VALUES ( ? )";
        String insertStatement2 = "INSERT INTO user_like_relations (user_id, like_id) VALUES ( ?, ?)";
        int lastID;

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, secondaryID);
            preparedStatement.executeUpdate();

            lastID = getLastCreatedID();

            preparedStatement = connection.prepareStatement(insertStatement2);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, lastID);
        }
        catch(SQLException e){
            System.out.println("Like creation failed="+e.getMessage());
        }
    }

    public ArrayList<Candidate> getUsersCandidates(int userID){
        String selectStatement = "SELECT uu2.user_id, uu2.username, uu2.photo, uu2.sex, uu2.birthdate " +
                "FROM users uu " +
                "JOIN user_like_relations ul ON uu.user_id = ul.user_id " +
                "JOIN likes li ON ul.like_id = li.like_id " +
                "Join users uu2 ON li.user_id = uu2.user_id " +
                "WHERE uu.user_id = ? " +
                "ORDER BY uu2.username";
        ResultSet resultSet;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1,userID);
            resultSet = preparedStatement.executeQuery();
        }
        catch (SQLException e){

        }

        ArrayList<Candidate> list = new ArrayList<>();

        return list;
    }

    public ArrayList<Candidate> getAllUsersLike(String username){
        String selectStatement = "SELECT user_id, username, photo, sex, birthdate FROM users WHERE username LIKE ? ORDER BY username";
        ResultSet resultSet;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setString(1, "%" + username + "%");
            resultSet = preparedStatement.executeQuery();
        }
        catch (SQLException e){
            System.out.println("Get all users failed="+e.getMessage());
        }

        ArrayList<Candidate> list = new ArrayList<>();
        return list;
    }

    //ChatService
    public void createMessage(int conversationID, int authorID, String message){

    }
    public void createConversation(int userID, int secondaryID){

    }

    //LoginService
    public int getUserFromLogin(String username, String password){
        String selectStatement = "SELECT user_id FROM users WHERE username = ? AND password = ?";
        int userID = -1;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            userID = resultSet.getInt("user_id");
        }
        catch (SQLException e){
            System.out.println("Retriving userID from login failed="+e.getMessage());
        }
        return userID;
    }
    public boolean getAdminFromLogin(String username, String password){
        String selectStatement = "SELECT admin_id FROM admins WHERE admin_username = ? AND admin_password = ?";
        boolean admin = false;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                admin = true;
            }
        }
        catch (SQLException e){
            System.out.println("Retriving admin from login failed="+e.getMessage());
        }
        return admin;
    }

    //ProfileHandler
    public void createProfile(String email, String firstname, String surname, String username, String password, int sex, String birthdate){
        String insertStatement = "INSERT INTO users (email, firstname, surname, username, password, sex, birthdate, photo, description) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? )";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, firstname);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password);
            preparedStatement.setInt(6, sex);
            preparedStatement.setString(7, birthdate);
            preparedStatement.setString(8, "stock_photo");
            preparedStatement.setString(9, "");

            preparedStatement.executeUpdate();

            createKeywords(getLastCreatedID());
        }
        catch (SQLException e){
            System.out.println();
        }

    }

    public void createKeywords(int userID){
        String insertStatement = "INSERT INTO keywords (user_id, keyword_1) VALUES ( ? , ? )";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, "");
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Keyword creation error="+e.getMessage());
        }
    }

    public void changeProfile(int userID){
        String insertStatement = "INSERT INTO users () VALUES () WHERE user_id = ? ";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Changing Profile data failed="+e.getMessage());
        }
    }

    public void deleteProfile(int userID){

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

    //Other
    public int getLastCreatedID(){
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
