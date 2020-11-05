package com.example.demo.models;

public class Profile {
    private String username;
    private String password;
    private String sex;
    private String email;
    private String firstName;
    private String surName;
    private String pictureName;
    private int userID;

    public Profile(String username, String password, String sex, String email, String firstName, String surName, String pictureName, int userID){
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.firstName = firstName;
        this.surName = surName;
        this.pictureName = pictureName;
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
