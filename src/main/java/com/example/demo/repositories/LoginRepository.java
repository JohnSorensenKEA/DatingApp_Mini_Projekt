package com.example.demo.repositories;

import java.sql.*;

public class LoginRepository {
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
            resultSet.next();
            userID = resultSet.getInt("user_id");
        }
        catch (SQLException e){
            System.out.println("Retrieving userID from login failed="+e.getMessage());
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
            System.out.println("Retrieving admin from login failed="+e.getMessage());
        }
        return admin;
    }
}
