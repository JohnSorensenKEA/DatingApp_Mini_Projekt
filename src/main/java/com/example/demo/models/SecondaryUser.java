package com.example.demo.models;

public class SecondaryUser {
    private String secondaryUsername;
    private String secondaryPhoto;
    private String secondaryDescription;
    private int secondaryUserID;
    private int secondarySex;
    private int secondaryAge;
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

    public SecondaryUser(String secondaryUsername, String secondaryPhoto, String secondaryDescription, int secondaryUserID, int secondarySex, int secondaryAge, String keyword1, String keyword2, String keyword3) {
        this.secondaryUsername = secondaryUsername;
        this.secondaryPhoto = secondaryPhoto;
        this.secondaryDescription = secondaryDescription;
        this.secondaryUserID = secondaryUserID;
        this.secondarySex = secondarySex;
        this.secondaryAge = secondaryAge;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
    }

    public String getSecondaryUsername() {
        return secondaryUsername;
    }

    public void setSecondaryUsername(String secondaryUsername) {
        this.secondaryUsername = secondaryUsername;
    }

    public String getSecondaryPhoto() {
        return secondaryPhoto;
    }

    public void setSecondaryPhoto(String secondaryPhoto) {
        this.secondaryPhoto = secondaryPhoto;
    }

    public String getSecondaryDescription() {
        return secondaryDescription;
    }

    public void setSecondaryDescription(String secondaryDescription) {
        this.secondaryDescription = secondaryDescription;
    }

    public int getSecondaryUserID() {
        return secondaryUserID;
    }

    public void setSecondaryUserID(int secondaryUserID) {
        this.secondaryUserID = secondaryUserID;
    }

    public int getSecondarySex() {
        return secondarySex;
    }

    public void setSecondarySex(int secondarySex) {
        this.secondarySex = secondarySex;
    }

    public int getSecondaryAge() {
        return secondaryAge;
    }

    public void setSecondaryAge(int secondaryAge) {
        this.secondaryAge = secondaryAge;
    }

    @Override
    public String toString() {
        return "SecondaryUser{" +
                "secondaryUsername='" + secondaryUsername + '\'' +
                ", secondaryPhoto='" + secondaryPhoto + '\'' +
                ", secondaryDescription='" + secondaryDescription + '\'' +
                ", secondaryUserID=" + secondaryUserID +
                ", secondarySex=" + secondarySex +
                ", secondaryAge=" + secondaryAge +
                ", keyword1='" + keyword1 + '\'' +
                ", keyword2='" + keyword2 + '\'' +
                ", keyword3='" + keyword3 + '\'' +
                '}';
    }
}
