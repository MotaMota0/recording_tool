package com.example.record_tool.model;

public class Client {
    private String name;
    private String time;
    private boolean selected;

    public Client(){}

    public Client(String name, String time){
        this.name = name;
        this.time = time;
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
