package com.example.demo.models;

import java.sql.Time;
import java.util.Date;

public class Message {
    private String text;
    private Date date;
    private Time time;
    private int authorID;

    public Message(String text, Date date, Time time, int authorID) {
        this.text = text;
        this.date = date;
        this.time = time;
        this.authorID = authorID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }
}
