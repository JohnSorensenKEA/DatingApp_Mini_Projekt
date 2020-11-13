package com.example.demo.repositories;

import com.example.demo.models.Candidate;
import com.example.demo.models.SecondaryUser;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class CandidateRepository {
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
        String insertStatement1 = "INSERT INTO likes (user_id) VALUES ( ? )";
        String insertStatement2 = "INSERT INTO user_like_relations (user_id, like_id) VALUES ( ?, ?)";
        int lastID;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement1);
            preparedStatement.setInt(1, secondaryID);
            preparedStatement.executeUpdate();

            lastID = getLastCreatedID();

            preparedStatement = connection.prepareStatement(insertStatement2);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, lastID);
            preparedStatement.executeUpdate();
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


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateTime = LocalDate.parse(birthdate, formatter);
                LocalDate now = LocalDate.now();
                //ChronoUnit age = new ChronoUnit();

                int age = (int) ChronoUnit.YEARS.between(dateTime,now);

                Candidate candidate = new Candidate(secondaryUsername,secondaryPhoto,secondaryID,sex,age);
                list.add(candidate);
            }
        }
        catch (SQLException e){
            System.out.println("Failed getting users candidates="+e.getMessage());
            return null;
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

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateTime = LocalDate.parse(birthdate, formatter);
                LocalDate now = LocalDate.now();
                int age = (int) ChronoUnit.YEARS.between(dateTime,now);

                Candidate candidate = new Candidate(secondaryUsername,secondaryPhoto,secondaryID,sex,age);
                list.add(candidate);
            }
        }
        catch (SQLException e){
            System.out.println("Get all users failed="+e.getMessage());
        }
        return list;
    }

    //Exception may occur if user has like all other users
    public SecondaryUser getRandomUnlikedUser(int userID){
        String selectStatement =
                "SELECT uu.user_id, uu.username, uu.photo, uu.description, uu.sex, ke.keyword_1, ke.keyword_2, ke.keyword_3, uu.birthdate FROM users uu " +
                "JOIN keywords ke ON uu.user_id = ke.user_id " +
                "WHERE NOT EXISTS " +
                "(SELECT * FROM users uu2 " +
                "JOIN likes li ON uu.user_id = li.user_id " +
                "JOIN user_like_relations ul ON li.like_id = ul.like_id " +
                "WHERE ul.user_id = ?) " +
                "AND uu.user_id != ? " +
                "ORDER BY RAND() " +
                "LIMIT 1";

        SecondaryUser secondaryUser = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            String secondaryUsername = resultSet.getString("username");
            String secondaryPhoto = resultSet.getString("photo");
            String secondaryDescription = resultSet.getString("description");
            int secondaryUserID = resultSet.getInt("user_id");
            int secondarySex = resultSet.getInt("sex");
            String secondaryAge = resultSet.getString("birthdate");

            //String str = "1994-03-04";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateTime = LocalDate.parse(secondaryAge, formatter);
            LocalDate now = LocalDate.now();
            //ChronoUnit age = new ChronoUnit();

                int age = (int) ChronoUnit.YEARS.between(dateTime,now);
                String keyword1 = resultSet.getString("keyword_1");
                String keyword2 = resultSet.getString("keyword_2");
                String keyword3 = resultSet.getString("keyword_3");
                secondaryUser = new SecondaryUser(secondaryUsername,secondaryPhoto,secondaryDescription,secondaryUserID,secondarySex,age,keyword1,keyword2,keyword3);

        }
        catch (SQLException e){
            //Maybe acceptable
            System.out.println("{May occur if user has liked all other users}Failure trying to get random unliked user="+e.getMessage());
        }
        return secondaryUser;
    }

    public void deleteLike(int userID, int secondaryID){
        String deleteStatement =
                "DELETE li FROM likes li " +
                "JOIN user_like_relations ul using(like_id) " +
                "WHERE ul.user_id = ? AND li.user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, secondaryID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Failed to delete like="+e.getMessage());
        }
    }

    public boolean checkIfMatch(int userID, int secondayID){
        String selectStatement = "SELECT * FROM user_like_relations ul " +
                "JOIN likes li ON ul.like_id = li.like_id " +
                "WHERE (ul.user_id = ? AND li.user_id = ?) " +
                "OR (ul.user_id = ? AND li.user_id = ?)";
        int count = 0;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, secondayID);
            preparedStatement.setInt(3, secondayID);
            preparedStatement.setInt(4, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                count++;
            }
        }
        catch (SQLException e){
            System.out.println("Failed trying to check if match="+e.getMessage());
        }
        System.out.println(count);
        if(count == 2){
            return true;
        }
        else {
            return false;
        }
    }

    //Other
    public int getLastCreatedID(){ //Returns AI ID of last added row
        String selectStatement = "SELECT last_insert_id()";
        int res = -1;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            res = resultSet.getInt("last_insert_id()");
        }
        catch (SQLException e){
            System.out.println("last_insert_id() error="+e.getMessage());
        }
        return res;
    }

}