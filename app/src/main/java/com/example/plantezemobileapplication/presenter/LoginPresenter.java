package com.example.plantezemobileapplication.presenter;

import androidx.annotation.NonNull;

import com.example.plantezemobileapplication.model.LoginModel;
import com.example.plantezemobileapplication.view.login.ProcessView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginPresenter {
    private final ProcessView view;
    private final LoginModel loginModel;

    public LoginPresenter(ProcessView view) {
        this.view = view;
        this.loginModel = new LoginModel();
    }

    public void loginUser(String email, String password) {
        if (view != null) {
            view.showLoading();
        }

        loginModel.loginUser(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (view != null) {
                    view.hideLoading();

                    if (task.isSuccessful()) {
                        verifyUser();
                    } else {
                        view.showProcessFailure("Authentication failed.");
                    }
                }

            }
        });
    }

    private void verifyUser() {
        if (loginModel.isVerified()) {
            view.showProcessSuccess("Logged in.");
        } else {
            view.showProcessFailure("Please verify your email before logging in.");
        }
    }
}
