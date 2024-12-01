package com.example.plantezemobileapplication;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class User  {
    private String fullName;
    private String email;
    private Emissions annualEmissions;
    private HashMap<String, Emissions> dailyEmissions, weeklyEmissions, monthlyEmissions;

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

    public HashMap<String, Emissions> getDailyEmissions() {
        return dailyEmissions;
    }

    public void setDailyEmissions(HashMap<String, Emissions> dailyEmissions) {
        this.dailyEmissions = dailyEmissions;
    }

    public HashMap<String, Emissions> getWeeklyEmissions() {
        return weeklyEmissions;
    }

    public void setWeeklyEmissions(HashMap<String, Emissions> weeklyEmissions) {
        this.weeklyEmissions = weeklyEmissions;
    }

    public HashMap<String, Emissions> getMonthlyEmissions() {
        return monthlyEmissions;
    }

    public void setMonthlyEmissions(HashMap<String, Emissions> monthlyEmissions) {
        this.monthlyEmissions = monthlyEmissions;
    }
}
