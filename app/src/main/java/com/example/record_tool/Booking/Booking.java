package com.example.record_tool.Booking;

import android.view.MenuItem;

public class Booking {



    private String name;
    private String service;
    private String date;
    private String time;
    private String title;

    // Constructor
    public Booking(String service, String date, String time, String title, String name) {
        this.service = service;
        this.date = date;
        this.time = time;
        this.title = title;
        this.name = name;
    }

    // Getters and Setters
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


