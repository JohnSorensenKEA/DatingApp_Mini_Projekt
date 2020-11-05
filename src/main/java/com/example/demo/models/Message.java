package com.example.demo.models;

import java.sql.Time;
import java.util.Date;

public class Message {
    private String text;
    private Date date;
    private Time time;
    private int author_id;

    public Message(String text, Date date, Time time, int author_id) {
        this.text = text;
        this.date = date;
        this.time = time;
        this.author_id = author_id;
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

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }
}
