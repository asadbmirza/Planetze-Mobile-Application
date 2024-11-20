package com.example.plantezemobileapplication.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class QuestionnaireModel {
    FirebaseAuth auth;

    public QuestionnaireModel() {
        auth = FirebaseAuth.getInstance();
    }

    public void saveQuestionnaireInfo(Map<String, Double> categoryEmissions, String userCountry) {
        FirebaseUser currUser = auth.getCurrentUser();
        String userId = "";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        if (currUser != null) {
            userId = currUser.getUid();
        }
        else {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2";
        }
        ref.child("users").child(userId).child("annualEmissions").setValue(categoryEmissions);
        ref.child("users").child(userId).child("country").setValue(userCountry);
    }
}
