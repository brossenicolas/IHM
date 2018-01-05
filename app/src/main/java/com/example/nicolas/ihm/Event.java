package com.example.nicolas.ihm;

public class Event {
    private String id;
    private String type;
    private String date;
    private String time;
    private String description;

    public Event(String id, String type, String date, String time, String description) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public String getId() { return id; }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}