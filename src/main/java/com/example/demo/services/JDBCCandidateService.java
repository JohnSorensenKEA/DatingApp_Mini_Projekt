package com.example.demo.services;

import com.example.demo.models.Candidate;
import com.example.demo.models.SecondaryUser;

import java.sql.*;
import java.util.ArrayList;

public class JDBCCandidateService {
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
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                String secondaryUsername = resultSet.getString("username");
                String secondaryPhoto = resultSet.getString("photo");
                String secondaryDescription = resultSet.getString("description");
                int secondaryUserID = resultSet.getInt("user_id");
                int secondarySex = resultSet.getInt("sex");
 //FIX
                int secondaryAge = -1;
                String keyword1 = resultSet.getString("keyword_1");
                String keyword2 = resultSet.getString("keyword_2");
                String keyword3 = resultSet.getString("keyword_3");

                secondaryUser = new SecondaryUser(secondaryUsername,secondaryPhoto,secondaryDescription,secondaryUserID,secondarySex,secondaryAge,keyword1,keyword2,keyword3);
            }
        }
        catch (SQLException e){
            System.out.println("Failure trying to get random unliked user="+e.getMessage());
        }
        return secondaryUser;
    }

    public void deleteLike(int userID, int secondaryID){
        String deleteStatement =
                "DELETE likes FROM user_like_relations ul " +
                "JOIN likes li using(like_id) " +
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

    //Redundant
    public void deleteAllLikesWithUser(int userID){

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
