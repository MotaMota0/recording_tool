package com.example.record_tool.MasterReg;

public class MasterRegPage {

    private String email;
    private String password;
    private String name;
    private boolean isMaster;


    public MasterRegPage() {
    }

    public MasterRegPage(String email, String password, String name,boolean isMaster) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.isMaster = isMaster;
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

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    public void setName(String name) {
        this.name = name;
    }
}
