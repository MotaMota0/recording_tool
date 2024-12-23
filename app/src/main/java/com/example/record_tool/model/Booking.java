package com.example.record_tool.model;

public class Booking {

    private String name;
    private String service;
    private String date;
    private String time;
    private String nameMaster;


    // Constructor
    public Booking(String service, String date, String time, String name,String nmaeMaster) {
        this.service = service;
        this.date = date;
        this.time = time;
        this.name = name;
        this.nameMaster = nmaeMaster;
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

    public String getNameMaster() {
        return nameMaster;
    }

    public void setNameMaster(String nameMaster) {
        this.nameMaster = nameMaster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


