package com.example.demo.models;

public class Candidate {
    private String Secondary_username;
    private String Secondary_photo;
    private String relation_id;

    public Candidate(String secondary_username, String secondary_photo, String relation_id) {
        Secondary_username = secondary_username;
        Secondary_photo = secondary_photo;
        this.relation_id = relation_id;
    }

    public String getSecondary_username() {
        return Secondary_username;
    }

    public void setSecondary_username(String secondary_username) {
        Secondary_username = secondary_username;
    }

    public String getSecondary_photo() {
        return Secondary_photo;
    }

    public void setSecondary_photo(String secondary_photo) {
        Secondary_photo = secondary_photo;
    }

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }
}
