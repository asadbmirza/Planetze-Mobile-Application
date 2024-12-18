package com.example.plantezemobileapplication.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plantezemobileapplication.utils.DailyEmission;
import com.example.plantezemobileapplication.utils.Habit;
import com.example.plantezemobileapplication.utils.MonthlyEmission;
import com.example.plantezemobileapplication.utils.TaskResult;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabitModel {
    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userId;
    private DatabaseReference ref;
    public HabitModel(){

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

    public Task<TaskResult> addHabit(Habit habit) {

        DatabaseReference newRef = ref.child("active_habits").push();
        TaskCompletionSource<TaskResult> taskCompletionSource = new TaskCompletionSource<>();
        habit.setId(newRef.getKey());
        newRef.setValue(habit).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                taskCompletionSource.setResult(new TaskResult(true, "Habit added successfully!"));
            } else {
                taskCompletionSource.setResult(new TaskResult(false, "Failed to add habit: " + task.getException().getMessage()));
            }

        });

        return taskCompletionSource.getTask();
    }

    public Task<TaskResult> updateHabit(Habit habit) {

        DatabaseReference newRef = ref.child("active_habits").child(habit.getId());
        TaskCompletionSource<TaskResult> taskCompletionSource = new TaskCompletionSource<>();
        newRef.setValue(habit).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                taskCompletionSource.setResult(new TaskResult(true, "Habit updated successfully!"));
            } else {
                taskCompletionSource.setResult(new TaskResult(false, "Failed to update habit: " + task.getException().getMessage()));
            }

        });

        return taskCompletionSource.getTask();
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

    public void removeActiveHabit(String id) {

        ref.child("active_habits").child(id).removeValue()
            .addOnSuccessListener(aVoid -> {
                System.out.println("Habit successfully removed from Firebase!");
            })
            .addOnFailureListener(e -> {
                System.err.println("Error removing habit: " + e.getMessage());
            });
    }
    public void getDailyEmissions(final OnDailyEmissionsListener listener) {
        ref.child("dailyEmissions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<DailyEmission> dailyEmissionList = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                        try {
                            DailyEmission dailyEmission = new DailyEmission();
                            dailyEmission.setId(dateSnapshot.getKey());

                            // Parse top-level fields
                            dailyEmission.setTransportation(dateSnapshot.child("transportation").getValue(Double.class));
                            dailyEmission.setFood(dateSnapshot.child("food").getValue(Double.class));
                            dailyEmission.setTotal(dateSnapshot.child("total").getValue(Double.class));
                            dailyEmission.setConsumption(dateSnapshot.child("consumption").getValue(Double.class));

                            // Parse activities
                            DataSnapshot activitiesSnapshot = dateSnapshot.child("activities");
                            if (activitiesSnapshot.exists()) {
                                Map<String, List<DailyEmission.ActivityDetail>> activitiesMap = new HashMap<>();

                                for (DataSnapshot activityIdSnapshot : activitiesSnapshot.getChildren()) {
                                    String activityId = activityIdSnapshot.getKey(); // Example: "155990586"
                                    DailyEmission.ActivityDetail activityDetail = new DailyEmission.ActivityDetail();

                                    // Populate fields for ActivityDetail
                                    activityDetail.setCategory(activityIdSnapshot.child("category").getValue(String.class));
                                    activityDetail.setEnteredValue(
                                            activityIdSnapshot.child("enteredValue").getValue(Integer.class) != null
                                                    ? activityIdSnapshot.child("enteredValue").getValue(Integer.class)
                                                    : 0
                                    );
                                    activityDetail.setQuestionTitle(activityIdSnapshot.child("questionTitle").getValue(String.class));

                                    // Parse selectedAnswer
                                    DataSnapshot selectedAnswerSnapshot = activityIdSnapshot.child("selectedAnswer");
                                    if (selectedAnswerSnapshot.exists()) {
                                        String answerText = selectedAnswerSnapshot.child("answerText").getValue(String.class);
                                        Double weight = selectedAnswerSnapshot.child("weight").getValue(Double.class);

                                        if (answerText != null) {
                                            DailyEmission.SelectedAnswer selectedAnswer = new DailyEmission.SelectedAnswer(answerText, weight != null ? weight : 0.0);
                                            activityDetail.setSelectedAnswer(selectedAnswer);
                                        }
                                    }

                                    // Add to the activities map
                                    List<DailyEmission.ActivityDetail> activityDetailsList = activitiesMap.getOrDefault(activityId, new ArrayList<>());
                                    activityDetailsList.add(activityDetail);
                                    activitiesMap.put(activityId, activityDetailsList);
                                }

                                // Add parsed activities to DailyEmission object
                                dailyEmission.setActivities(activitiesMap);
                            }

                            dailyEmissionList.add(dailyEmission);
                        } catch (Exception e) {
                            Log.e("Firebase", "Error parsing data: " + e.getMessage());
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




    public void getMonthlyEmissions(final OnMonthlyEmissionsListener listener) {
        ref.child("monthlyEmissions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<MonthlyEmission> monthlyEmissionList = new ArrayList<>();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MonthlyEmission monthlyEmission = snapshot.getValue(MonthlyEmission.class);

                        if (monthlyEmission != null) {
                            monthlyEmission.setId(snapshot.getKey());
                            monthlyEmissionList.add(monthlyEmission);
                        }
                    }
                }
                listener.onMonthlyEmissionsFetched(monthlyEmissionList);
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

    public interface OnMonthlyEmissionsListener {
        void onMonthlyEmissionsFetched(ArrayList<MonthlyEmission> itemList);
    }

    public interface OnActiveHabitsListener {
        void onHabitsFetched(ArrayList<Habit> itemList);
    }
}
