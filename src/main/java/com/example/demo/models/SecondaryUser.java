package com.example.demo.models;

public class SecondaryUser {
    private String SecondaryUserName;
    private String SecoundaryPhoto;
    private String SecoundaryDescription;
    private String SecoundaryUserid;
    private String SecoundarySex;
    private String SecoundaryFavorites;
    private int SecoundaryAge;

    public SecondaryUser(String secondaryUserName, String secoundaryPhoto, String secoundaryDescription,
                         String secoundaryUserid, String secoundarySex, String secoundaryFavorites, int secoundaryAge) {
        SecondaryUserName = secondaryUserName;
        SecoundaryPhoto = secoundaryPhoto;
        SecoundaryDescription = secoundaryDescription;
        SecoundaryUserid = secoundaryUserid;
        SecoundarySex = secoundarySex;
        SecoundaryFavorites = secoundaryFavorites;
        SecoundaryAge = secoundaryAge;
    }

    public String getSecondaryUserName() {
        return SecondaryUserName;
    }

    public void setSecondaryUserName(String secondaryUserName) {
        SecondaryUserName = secondaryUserName;
    }

    public String getSecoundaryPhoto() {
        return SecoundaryPhoto;
    }

    public void setSecoundaryPhoto(String secoundaryPhoto) {
        SecoundaryPhoto = secoundaryPhoto;
    }

    public String getSecoundaryDescription() {
        return SecoundaryDescription;
    }

    public void setSecoundaryDescription(String secoundaryDescription) {
        SecoundaryDescription = secoundaryDescription;
    }

    public String getSecoundaryUserid() {
        return SecoundaryUserid;
    }

    public void setSecoundaryUserid(String secoundaryUserid) {
        SecoundaryUserid = secoundaryUserid;
    }

    public String getSecoundarySex() {
        return SecoundarySex;
    }

    public void setSecoundarySex(String secoundarySex) {
        SecoundarySex = secoundarySex;
    }

    public String getSecoundaryFavorites() {
        return SecoundaryFavorites;
    }

    public void setSecoundaryFavorites(String secoundaryFavorites) {
        SecoundaryFavorites = secoundaryFavorites;
    }

    public int getSecoundaryAge() {
        return SecoundaryAge;
    }

    public void setSecoundaryAge(int secoundaryAge) {
        SecoundaryAge = secoundaryAge;
    }
}
