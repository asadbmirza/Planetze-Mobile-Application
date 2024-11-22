package com.example.plantezemobileapplication.model;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginModel {
    private final FirebaseAuth mAuth;
    private final FirebaseUser user;

    public LoginModel(FirebaseAuth auth) {
        this.mAuth = auth;
        this.user = mAuth.getCurrentUser();
    }

    public void loginUser(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public boolean isVerified() {
        return user != null && user.isEmailVerified();
    }

    public boolean isLoggedIn() {
        return user != null;
    }

}
