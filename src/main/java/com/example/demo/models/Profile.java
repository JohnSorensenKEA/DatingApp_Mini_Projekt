package com.example.demo.models;

public class Profile {
    private String username;
    private String password;
    private String sex;
    private String email;
    private String firstName;
    private String surName;
    private String picture_name;
    private int user_id;

    public Profile(String username, String password, String sex, String email, String firstName, String surName, String picture_name, int user_id){
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.firstName = firstName;
        this.surName = surName;
        this.picture_name = picture_name;
        this.user_id = user_id;
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

    public String getPicture_name() {
        return picture_name;
    }

    public void setPicture_name(String picture_name) {
        this.picture_name = picture_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
