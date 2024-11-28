package com.example.plantezemobileapplication.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EcoTrackerCalendarModel {
    final private FirebaseDatabase db;
    final private FirebaseAuth auth;
    private FirebaseUser user;
    private String userId;
    private DatabaseReference ref;
    public EcoTrackerCalendarModel() {

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2";
        } else {
            userId = user.getUid();

        }
        ref = db.getReference().child("users").child(userId).child("dailyEmissions");
        System.out.println(ref);
    }

    public void getDailyEmissions(final OnDailyEmissionsListener listener) {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<DailyEmission> dailyEmissionList = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DailyEmission dailyEmission = snapshot.getValue(DailyEmission.class);
                       
                        if (dailyEmission != null) {
                            dailyEmission.setId(snapshot.getKey());
                            dailyEmissionList.add(dailyEmission);
                        }
                    }
                }
                listener.onDailyEmissionsFetched(dailyEmissionList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });
    }




    public interface OnDailyEmissionsListener {
        void onDailyEmissionsFetched(ArrayList<DailyEmission> itemList);
    }

}
