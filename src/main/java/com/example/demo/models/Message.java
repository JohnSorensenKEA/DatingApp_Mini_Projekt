package com.example.demo.models;

public class Message {
    private String text;
    private String dateTime;
    private int authorID;

    public Message(String text, String dateTime, int authorID) {
        this.text = text;
        this.dateTime = dateTime;
        this.authorID = authorID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }
}
