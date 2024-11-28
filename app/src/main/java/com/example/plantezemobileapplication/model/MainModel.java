package com.example.plantezemobileapplication.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainModel {
    private final FirebaseAuth auth;
    private final DatabaseReference myRef;

    public MainModel(FirebaseAuth auth, DatabaseReference myRef) {
        this.auth = auth;
        this.myRef = myRef;
    }

    public void loadUserData(ValueEventListener listener) {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            // Temporary
            myRef.child("hRGBz0zBIGRbm6wJm9RA5Jii97M2").addValueEventListener(listener);
        }
    }
}
