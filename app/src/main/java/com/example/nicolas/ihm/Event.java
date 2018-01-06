package com.example.nicolas.ihm;

public class Event {
    private String id_user;
    private String id;
    private String type;
    private String date;
    private String time;
    private String description;

    Event(String id_user, String id, String type, String date, String time, String description) {
        this.id_user = id_user;
        this.id = id;
        this.type = type;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    String getIdUser() { return id_user; }

    public String getId() { return id; }

    String getType() {
        return type;
    }

    String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    String getDescription() {
        return description;
    }

    void setType(String type) {
        this.type = type;
    }

    void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    void setDescription(String description) {
        this.description = description;
    }
}