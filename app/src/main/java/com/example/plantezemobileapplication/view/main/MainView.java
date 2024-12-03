package com.example.plantezemobileapplication.view.main;

import com.example.plantezemobileapplication.User;

public interface MainView {
    void goToQuestionnaire();
    void displayUserData(User user);
    void setWelcomeText(String text);
    void setCurrentDate(String date);
}
