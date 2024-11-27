package com.example.plantezemobileapplication.view.main;

import com.example.plantezemobileapplication.Emissions;

public interface MainView {
    void displayUserData(Emissions emissions);
    void setWelcomeText(String text);
    void setCurrentDate(String date);
}
