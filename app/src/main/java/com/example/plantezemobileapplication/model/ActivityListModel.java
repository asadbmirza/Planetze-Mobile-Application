package com.example.plantezemobileapplication.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.plantezemobileapplication.presenter.ActivityListPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Map;

public class ActivityListModel {
    private FirebaseAuth auth;
    private ActivityListPresenter presenter;
    private DatabaseReference ref;
    private String userId;

    public ActivityListModel() {
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
    }

    public ActivityListModel(ActivityListPresenter presenter) {
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        this.presenter = presenter;
    }

    public void uploadEmissions(String path, String date, Map<String, Object> emissions) {
        FirebaseUser currUser = auth.getCurrentUser();
        if (currUser != null) {
            userId = currUser.getUid();
        }
        else {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2"; //TODO: UPDATE THIS CONDITION
        }

        for (String activity : emissions.keySet()) {
            ref.child("users").child(userId).child(path).child(date).child(activity).runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                    Object value = currentData.getValue();
                    // If value already exists
                    if (value instanceof Number) {
                        currentData.setValue(((Number) value).doubleValue() + (double) emissions.get(activity));
                    }
                    else {
                        currentData.setValue(emissions.get(activity));
                    }
                    return Transaction.success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                    if (error != null) {
                        System.out.println("Transaction failed: " + error.getMessage());
                    } else {
                        System.out.println("Transaction successful for activity: " + activity);
                    }
                }
            });
        }
    }

    public void uploadActivityLog(Map<String, Object> activities) {
        FirebaseUser currUser = auth.getCurrentUser();

        if (currUser != null) {
            userId = currUser.getUid();
        }
        else {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2"; //TODO: UPDATE THIS CONDITION
        }

        ref.child("users").child(userId).child("dailyEmissions").child(presenter.getCurrentDay()).child("activities").updateChildren(activities);
    }
}
