package com.example.plantezemobileapplication.model;

import android.util.Log;

import com.example.plantezemobileapplication.presenter.EcoTrackerHabitPresenter;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EcoTrackerModel {
    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userId;
    private DatabaseReference ref;
    public EcoTrackerModel(){

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2";
        } else {
            userId = user.getUid();

        }
        ref = db.getReference().child("users").child(userId);
    }

    public void addHabit(Habit habit) {

        DatabaseReference newRef = ref.child("active_habits").push();
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();


        newRef.setValue(habit);
    }

    public void getActiveHabits(final OnActiveHabitsListener listener) {
        ref.child("active_habits").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Habit> habitList = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Habit habit = snapshot.getValue(Habit.class);
                        System.out.println("Hello");
                        if (habit != null) {
                            habitList.add(habit);
                        }
                    }
                }
                System.out.println("Goodbye");
                listener.onHabitsFetched(habitList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });
    }


    public interface OnActiveHabitsListener {
        void onHabitsFetched(ArrayList<Habit> itemList);
    }
}
