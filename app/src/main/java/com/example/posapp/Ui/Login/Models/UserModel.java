package com.example.posapp.Ui.Login.Models;

public class UserModel {
    public String employeeId;
    public String username;
    public String fName;

    public UserModel() {}
    public UserModel(String employeeId, String username, String fName) {
        this.employeeId = employeeId;
        this.username = username;
        this.fName = fName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getUsername() {
        return username;
    }

    public String getFName() {
        return username;
    }

    public void setEmployeeId(String employeeId) {
       this.employeeId = employeeId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }
}
