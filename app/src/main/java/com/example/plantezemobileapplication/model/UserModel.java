package com.example.plantezemobileapplication.model;

public class UserModel {
    public String fullName;
    public String email;

    public UserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserModel(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
}
