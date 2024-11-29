package com.example.plantezemobileapplication.model;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginModel {
    private final FirebaseAuth mAuth;
    //Temp
    private LoginActivity view;

    public LoginModel(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    // Temp
    public LoginModel(LoginActivity view, FirebaseAuth auth) {
        this.mAuth = auth;
        this.view = view;
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

    public boolean isLoggedIn() {
        return getUser() != null;
    }

//    Temp
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
