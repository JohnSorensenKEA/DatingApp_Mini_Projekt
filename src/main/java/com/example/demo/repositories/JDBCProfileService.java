package com.example.demo.repositories;

import com.example.demo.models.Profile;

import java.sql.*;

public class JDBCProfileService {
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

    //ProfileHandler
    public int createProfile(String email, String firstname, String surname, String username, String password, int sex, String birthdate){
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
            preparedStatement.setString(8, "stock_photo.png");
            preparedStatement.setString(9, "");

            preparedStatement.executeUpdate();

            createKeywords(getLastCreatedID());
        }
        catch (SQLException e){
            System.out.println("Profile creation failed="+e.getMessage());
        }
        //t(''t)
        return sex;
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

    //Redundant...
    public void deleteKeywords(int userID){
        String deleteStatement = "DELETE FROM keywords WHERE user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1,userID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Failed to delete keywords="+e.getMessage());
        }
    }

    //CheckUsage
    public void changeProfile(int userID, String firstName, String surName, String password, String description){
        String updateStatement =
                "UPDATE users SET firstname = ?, surname = ? , password = ?, description = ? " +
                        "WHERE user_id = ? ";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
//add
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

    public void changeKeywords(int userID, String keyword1, String keyword2, String keyword3){
        String updateStatement =
                "UPDATE keywords SET keyword_1 = ?, keyword_2 = ?, keyword_3 = ?" +
                        "WHERE user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setString(1,"'" + keyword1 + "'");
            preparedStatement.setString(2, "'" + keyword2 + "'");
            preparedStatement.setString(3, "'" + keyword3 + "'");
            preparedStatement.setInt(4,userID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Change keywords failed="+e.getMessage());
        }
    }

    public void deleteProfile(int userID){
        String deleteStatement = "DELETE FROM users WHERE user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1,userID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Failed to delete profile="+e.getMessage());
        }
    }

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
