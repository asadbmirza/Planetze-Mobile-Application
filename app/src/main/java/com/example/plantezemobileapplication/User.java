package com.example.plantezemobileapplication;

import java.io.Serializable;

public class User  {
    private String fullName;
    private Emissions annualEmissions;
    private String email;

    public User() { }

    public User(String fullName, String email, Emissions annualEmissions) {
        this.fullName = fullName;
        this.email = email;
        this.annualEmissions = annualEmissions;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Emissions getAnnualEmissions() {
        return annualEmissions;
    }

    public void setAnnualEmissions(Emissions annualEmissions) {
        this.annualEmissions = annualEmissions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
