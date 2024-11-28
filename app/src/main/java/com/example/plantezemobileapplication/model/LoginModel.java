package com.example.plantezemobileapplication.model;

import androidx.annotation.NonNull;

import com.example.plantezemobileapplication.view.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginModel {
    private final FirebaseAuth mAuth;
    private LoginActivity view;

    public LoginModel(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    public LoginModel(LoginActivity view, FirebaseAuth auth) {
        this.mAuth = auth;
        this.view = view;
    }

    public void loginUser(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public boolean isVerified() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null && user.isEmailVerified();
    }

    public void navigateUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(userId).child("annualEmissions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    view.navigateToMainMenu();
                }
                else {
                    view.navigateToQuestionnaire();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.navigateToQuestionnaire();
            }
        });
    }
}
