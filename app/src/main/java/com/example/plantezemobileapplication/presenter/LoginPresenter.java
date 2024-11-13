package com.example.plantezemobileapplication.presenter;

import androidx.annotation.NonNull;

import com.example.plantezemobileapplication.view.ProcessView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
                            view.showProcessSuccess();
                        } else {
                            view.showProcessFailure();
                        }
                    }
                });
    }
}
