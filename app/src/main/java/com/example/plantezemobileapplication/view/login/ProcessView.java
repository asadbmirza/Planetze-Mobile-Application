package com.example.plantezemobileapplication.view.login;

public interface ProcessView {
    void showProcessSuccess(String message);
    void showProcessFailure(String message);
    void showLoading();
    void hideLoading();
}
