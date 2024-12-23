package com.example.record_tool.model;

public class UserRegPage  {

    private String email;
    private String password;
    private String name;
    private boolean isMaster;
    private String user_uid;


    public UserRegPage() {
    }

    public UserRegPage(String email, String password, String name, boolean isMaster,String user_uid) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.isMaster = isMaster;
        this.user_uid = user_uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }
}
