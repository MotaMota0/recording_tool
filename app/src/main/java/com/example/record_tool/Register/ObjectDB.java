package com.example.record_tool.Register;

import static java.lang.String.valueOf;

import android.widget.EditText;

import java.io.Serializable;

public class ObjectDB implements Serializable {
    private EditText email;
    private EditText password;

    public ObjectDB() {
    }

    public ObjectDB(EditText email, EditText password) {
        this.email = email;
        this.password = password;
    }

    public EditText  getEmail() {
        return email;
    }

    public void setEmail(EditText email) {
        this.email = email;
    }

    public EditText getPassword() {
        return password;
    }

    public void setPassword(EditText password) {
        this.password = password;
    }
}
