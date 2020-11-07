package com.example.demo.services;

import com.example.demo.models.Candidate;
import com.example.demo.models.Profile;
import com.example.demo.models.SecondaryUser;

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
        String selectStatement =
                "SELECT uu2.user_id, uu2.username, uu2.photo, uu2.sex, uu2.birthdate " +
                "FROM users uu " +
                "JOIN user_like_relations ul ON uu.user_id = ul.user_id " +
                "JOIN likes li ON ul.like_id = li.like_id " +
                "JOIN users uu2 ON li.user_id = uu2.user_id " +
                "WHERE uu.user_id = ? " +
                "ORDER BY uu2.username";
        ResultSet resultSet;
        ArrayList<Candidate> list = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1,userID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String secondaryUsername = resultSet.getString("username");
                String secondaryPhoto = resultSet.getString("photo");
                int secondaryID = resultSet.getInt("user_id");
                int sex = resultSet.getInt("sex");
                String birthdate = resultSet.getString("birthdate");
                Candidate candidate = new Candidate(secondaryUsername,secondaryPhoto,secondaryID,sex,birthdate);
                list.add(candidate);
            }
        }
        catch (SQLException e){
            System.out.println("Failed getting users candidates="+e.getMessage());
        }
        return list;
    }

    public ArrayList<Candidate> getAllUsersLikeUsername(String username){
        String selectStatement =
                "SELECT user_id, username, photo, sex, birthdate FROM users " +
                "WHERE username LIKE ? " +
                "ORDER BY username";
        ResultSet resultSet;
        ArrayList<Candidate> list = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setString(1, "%" + username + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String secondaryUsername = resultSet.getString("username");
                String secondaryPhoto = resultSet.getString("photo");
                int secondaryID = resultSet.getInt("user_id");
                int sex = resultSet.getInt("sex");
                String birthdate = resultSet.getString("birthdate");
                Candidate candidate = new Candidate(secondaryUsername,secondaryPhoto,secondaryID,sex,birthdate);
                list.add(candidate);
            }
        }
        catch (SQLException e){
            System.out.println("Get all users failed="+e.getMessage());
        }
        return list;
    }

    public SecondaryUser getRandomUnlikedUser(int userID){
        String selectStatement =
                "SELECT uu2.user_id, uu2.username, uu2.photo, uu2.description, uu2.sex, ke.keyword_1, ke.keyword_2, ke.keyword_3, uu2.birthdate " +
                "FROM users uu " +
                "JOIN user_like_relations ul ON uu.user_id = ul.user_id " +
                "JOIN likes li ON ul.like_id = li.like_id " +
                "RIGHT JOIN users uu2 ON li.user_id = uu2.user_id " +
                "JOIN keywords ke ON uu2.user_id = ke.user_id " +
                "WHERE uu.user_id != ? AND uu2.user_id != ? " +
                "GROUP BY uu2.user_id " +
                "ORDER BY RAND() " +
                "LIMIT 1;";
        SecondaryUser secondaryUser = null;
        ResultSet resultSet = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, userID);

            resultSet = preparedStatement.executeQuery();


//add            secondaryUser = new SecondaryUser();
        }
        catch (SQLException e){
            System.out.println("Failure trying to get random unliked user="+e.getMessage());
        }

        if (resultSet != null){
//Add
        }
        return secondaryUser;
    }

    //ChatService
    public void createMessage(int conversationID, int authorID, String message){

    }
    public void createConversation(int userID, int secondaryID){

    }

    //LoginService
    public int getUserFromLogin(String username, String password){
        String selectStatement =
                "SELECT user_id FROM users " +
                "WHERE username = ? AND password = ?";
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
        String selectStatement =
                "SELECT admin_id FROM admins " +
                "WHERE admin_username = ? AND admin_password = ?";
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

    public void deleteKeywords(int userID){

    }

//CheckUsage
    public void changeProfile(int userID){
        String updateStatement =
                "UPDATE users SET firstname = ?, surname = ? , password = ?, description = ? " +
                "WHERE user_id = ? ";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Changing Profile data failed="+e.getMessage());
        }
    }

    public void changePhoto(int userID, String photoName){
        String updateStatement =
                "UPDATE users SET photo = ? " +
                "WHERE user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setString(1,"'" + photoName + "'");
            preparedStatement.setInt(2,userID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Photo update failed="+e.getMessage());
        }
    }

    public void changeKeywords(int userID, String keyword_1){
        String updateStatement =
                "UPDATE keywords SET keyword_1 = ? " +
                "WHERE user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setString(1,"'" + keyword_1 + "'");
            preparedStatement.setInt(2,userID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Change keywords failed="+e.getMessage());
        }
    }

    public void deleteProfile(int userID){

    }

    //Change return type
    public Profile getUserInfo(int userID){
        ResultSet res = null;
        String selectSQL =
                "SELECT * FROM users " +
                "JOIN keywords using(user_id) " +
                "WHERE user_id = ?";

        String username = null;
        String password = null;
        int sex = -1;
        String email = null;
        String firstName = null;
        String surName = null;
        String pictureName = null;
        int profileID = -1;
        String keyword1 = null;
        String keyword2 = null;
        String keyword3 = null;

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, userID);
            res =preparedStatement.executeQuery();

            username = res.getString("username");
            password = res.getString("password");
            sex = res.getInt("sex");
            email = res.getString("email");
            firstName = res.getString("firstname");
            surName = res.getString("surname");
            pictureName = res.getString("photo");
            profileID = res.getInt("user_id");
            keyword1 = res.getString("keyword_1");
            keyword2 = res.getString("keyword_2");
            keyword3 = res.getString("keyword_3");
        }
        catch (SQLException e){
            System.out.println("GetUserInfoError="+e.getMessage());
        }

        Profile profile = new Profile(username,password,sex,email,firstName,surName,pictureName,profileID,keyword1,keyword2,keyword3);
        return profile;
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
