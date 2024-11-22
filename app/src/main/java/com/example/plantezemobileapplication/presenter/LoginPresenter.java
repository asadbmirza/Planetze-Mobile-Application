package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.LoginModel;
import com.example.plantezemobileapplication.view.login.LoginView;

public class LoginPresenter {
    private final LoginView view;
    private final LoginModel loginModel;

    public LoginPresenter(LoginView view, LoginModel loginModel) {
        this.view = view;
        this.loginModel = loginModel;
    }

    public void loginUser(String email, String password) {
        if (view != null) {
            view.showLoading();
        }

        loginModel.loginUser(email, password, task -> {
            if (view != null) {
                view.hideLoading();
                if (task.isSuccessful()) {
                    verifyUser();
                } else {
                    view.showProcessFailure("Authentication failed.");
                }
            }
        });
    }

    private void verifyUser() {
        if (loginModel.isVerified()) {
            view.showProcessSuccess("Logged in.");
            view.goToHomepage();
        } else {
            view.showProcessFailure("Please verify your email before logging in.");
        }
    }
}
