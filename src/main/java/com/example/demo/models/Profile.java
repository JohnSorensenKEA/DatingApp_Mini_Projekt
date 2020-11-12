package com.example.demo.models;

public class Profile {
    private String username;
    private String password;
    private int sex;
    private String birthdate;
    private String email;
    private String firstName;
    private String surName;
    private String description;
    private String pictureName;
    private int userID;
    private String keyword1;
    private String keyword2;
    private String keyword3;

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    public Profile(String username, String password, int sex, String birthdate, String email, String firstName, String surName, String description, String pictureName, int userID, String keyword1, String keyword2, String keyword3){
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.birthdate = birthdate;
        this.email = email;
        this.firstName = firstName;
        this.surName = surName;
        this.description = description;
        this.pictureName = pictureName;
        this.userID = userID;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthdate(){return birthdate;}

    public void setBirthdate(String birthdate){this.birthdate = birthdate;
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

    public String getDescription(){return description;}

    public void setDescription(String description){this.description = description;}

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

    public String getYear(){
        String[] arr = birthdate.split("-");
        return arr[0];
    }

    public String getMonth(){
        String[] arr = birthdate.split("-");
        return arr[1];
    }

    public String getDay(){
        String[] arr = birthdate.split("-");
        return arr[2];
    }
}
