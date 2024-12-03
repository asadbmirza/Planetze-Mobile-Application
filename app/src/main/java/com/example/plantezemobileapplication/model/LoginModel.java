package com.example.plantezemobileapplication.model;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginModel {
    private final FirebaseAuth mAuth;

    public LoginModel(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    public void loginUser(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    public boolean isVerified() {
        FirebaseUser user = getUser();
        return user != null && user.isEmailVerified();
    }
}
