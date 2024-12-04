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
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser currUser;

    public ActivityListModel() {
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        currUser = auth.getCurrentUser();
        if (currUser != null) {
            userId = currUser.getUid();
        }

    }

    public ActivityListModel(ActivityListPresenter presenter) {
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        currUser = auth.getCurrentUser();
        if (currUser != null) {
            userId = currUser.getUid();
        }

    }

    public void uploadEmissions(String path, String date, Map<String, Object> emissions) {
        System.out.println(emissions);

        for (String activity : emissions.keySet()) {
            System.out.println(userId);
            System.out.println(path);
            System.out.println(date);
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

    public void uploadActivityLog(Map<String, Object> activities, String day) {
        System.out.println(day + " activities: " + activities);
        ref.child("users").child(userId).child("dailyEmissions").child(day).child("activities").updateChildren(activities);
    }
}
