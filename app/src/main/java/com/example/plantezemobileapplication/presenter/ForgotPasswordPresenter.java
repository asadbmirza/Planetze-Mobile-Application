package com.example.plantezemobileapplication.presenter;

import androidx.annotation.NonNull;

import com.example.plantezemobileapplication.view.login.ProcessView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordPresenter {

    FirebaseAuth auth;
    ProcessView view;

    public ForgotPasswordPresenter(ProcessView view) {
        this.view = view;
        auth = FirebaseAuth.getInstance();
    }


    public void sendPasswordReset(String email) {
        if(view != null) {
            view.showLoading();
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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
