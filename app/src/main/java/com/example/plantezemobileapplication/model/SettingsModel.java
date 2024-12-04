package com.example.plantezemobileapplication.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsModel {
    DatabaseReference mDatabase;
    FirebaseUser user;

    public SettingsModel() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void saveUserFullName(String name) {
        mDatabase.child("users")
                .child(user.getUid())
                .child("fullName")
                .setValue(name);
    }

    public void saveUserCountry(String country) {
        mDatabase.child("users")
                .child(user.getUid())
                .child("country")
                .setValue(country);
    }

}
