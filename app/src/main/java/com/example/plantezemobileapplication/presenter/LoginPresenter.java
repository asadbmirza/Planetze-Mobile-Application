package com.example.plantezemobileapplication.presenter;

import androidx.annotation.NonNull;

import com.example.plantezemobileapplication.view.login.ProcessView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter {
    private final ProcessView view;
    private final FirebaseAuth mAuth;

    public LoginPresenter(ProcessView view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public void loginUser(String email, String password) {
        if (view != null) {
            view.showLoading();
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (view != null) {
                            view.hideLoading();
                        }
                        if (task.isSuccessful()) {
                            verifyUser();
                        } else {
                            view.showProcessFailure("Authentication failed.");
                        }
                    }
                });
    }

    private void verifyUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            view.showProcessSuccess("Logged in.");
        } else {
            view.showProcessFailure("Please verify your email before logging in.");
        }
    }
}
