package com.example.plantezemobileapplication.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class QuestionnaireModel {
    private FirebaseAuth auth;
    private String userId;
    private DatabaseReference ref;

    public QuestionnaireModel() {
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
    }

    public void saveQuestionnaireInfo(Map<String, Double> categoryEmissions, String userCountry, Map<String, Object> selectedAnswerIndexes) {
        FirebaseUser currUser = auth.getCurrentUser();

        if (currUser != null) {
            userId = currUser.getUid();
        }
        else {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2"; //TODO: UPDATE THIS CONDITION
        }
        ref.child("users").child(userId).child("annualEmissions").setValue(categoryEmissions);
        ref.child("users").child(userId).child("country").setValue(userCountry);
        ref.child("users").child(userId).updateChildren(selectedAnswerIndexes);
    }
}
