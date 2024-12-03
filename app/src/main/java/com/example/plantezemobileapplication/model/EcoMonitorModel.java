package com.example.plantezemobileapplication.model;

import androidx.annotation.NonNull;

import com.example.plantezemobileapplication.presenter.EcoMonitorCallback;
import com.example.plantezemobileapplication.utils.ActivityLog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcoMonitorModel {
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser currUser;
    private EcoMonitorCallback callback;

    public EcoMonitorModel() {
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        currUser = auth.getCurrentUser();
        userId = currUser != null ? currUser.getUid() : "hRGBz0zBIGRbm6wJm9RA5Jii97M2"; // TODO: Update this condition

    }

    public void setCallback(EcoMonitorCallback callback) {
        this.callback = callback;
    }

    public String getFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public void getDefaultVehicle() {
        ref.child("users").child(userId).child("defaultVehicle").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int defaultVehicle = snapshot.exists() && snapshot.getValue() != null
                        ? snapshot.getValue(int.class)
                        : -1;
                callback.onDefaultVehicleFetched(defaultVehicle);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFetchError("Failed to fetch default vehicle");
            }
        });
    }

    public void getUserEnergy() {
        ref.child("users").child(userId).child("energySource").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getValue() != null) {
                    callback.onUserEnergyFetched(snapshot.getValue(int.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFetchError("Failed to fetch user energy");
            }
        });
    }

    public void getCurrentEmissions(Date date) {
        String formattedDate = getFormattedDate(date);
        System.out.println(formattedDate);

        ref.child("users").child(userId).child("dailyEmissions").child(formattedDate)
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        if (snapshot.child("transportation").exists()) {
                            callback.onEmissionFetched("transportation", snapshot.child("transportation").getValue(double.class));
                        }
                        if (snapshot.child("food").exists()) {
                            callback.onEmissionFetched("food", snapshot.child("food").getValue(double.class));
                        }
                        if (snapshot.child("consumption").exists()) {
                            callback.onEmissionFetched("consumption", snapshot.child("consumption").getValue(double.class));
                        }
                        if (snapshot.child("total").exists()) {
                            callback.onEmissionFetched("total", snapshot.child("total").getValue(double.class));
                        }
                    } else {
                        // Create a new entry with default values if no data exists
                        Map<String, Object> defaultValues = new HashMap<>();
                        defaultValues.put("transportation", 0.0);
                        defaultValues.put("food", 0.0);
                        defaultValues.put("consumption", 0.0);
                        defaultValues.put("total", 0.0);

                        ref.child("users").child(userId).child("dailyEmissions").child(formattedDate)
                            .setValue(defaultValues)
                            .addOnSuccessListener(aVoid -> {
                                // Data created successfully
                                callback.onEmissionFetched("transportation", 0.0);
                                callback.onEmissionFetched("food", 0.0);
                                callback.onEmissionFetched("consumption", 0.0);
                                callback.onEmissionFetched("total", 0.0);
                            })
                            .addOnFailureListener(e -> callback.onFetchError("Failed to create today's activities"));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onFetchError("Failed to fetch today's emissions!");
                }
            });
    }

    public void getTodaysActivities(Date date) {
        String formattedDate = getFormattedDate(date);

        ref.child("users").child(userId).child("dailyEmissions").child(formattedDate).child("activities")
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<ActivityLog> activityLogList = new ArrayList<>();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        ActivityLog activityLog = childSnapshot.getValue(ActivityLog.class);
                        if (activityLog != null) {
                            activityLog.setId(Integer.parseInt(childSnapshot.getKey()));
                            activityLogList.add(activityLog);
                        }
                    }
                    callback.onActivitiesFetched(activityLogList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onFetchError("Failed to fetch today's activities");
                }
            });
    }

    public void updateActivityLog(ActivityLog activityLog, int newEnteredValue, String day, String week, String month) {
        System.out.println("UPDATED");
    }

    public void deleteActivityLog(ActivityLog activityLog, String day, String week, String month) {
        removeOldEmissions(activityLog, day, week, month);
        ref.child("users").child(userId).child("dailyEmissions").child(day).child("activities").child(String.valueOf(activityLog.getId())).removeValue();
        System.out.println("DELETED");
    }

    private void removeOldEmissions(ActivityLog activityLog, String day, String week, String month) {
        ref.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Map<String, Object> updates = new HashMap<>();
                    String category = activityLog.getCategory();
                    double valueToRemove = activityLog.getEnteredValue() * activityLog.getSelectedAnswer().getWeight();

                    addNewEmissionsToUpdates(updates, snapshot, "dailyEmissions", day, category, valueToRemove);
                    addNewEmissionsToUpdates(updates, snapshot, "dailyEmissions", day, "total", valueToRemove);
                    addNewEmissionsToUpdates(updates, snapshot, "weeklyEmissions", week, category, valueToRemove);
                    addNewEmissionsToUpdates(updates, snapshot, "weeklyEmissions", week, "total", valueToRemove);
                    addNewEmissionsToUpdates(updates, snapshot, "monthlyEmissions", month, category, valueToRemove);
                    addNewEmissionsToUpdates(updates, snapshot, "monthlyEmissions", month, "total", valueToRemove);

                    ref.child("users").child(userId).updateChildren(updates);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addNewEmissions(ActivityLog activityLog, double newEmissions, String day, String week, String month) {
        ref.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addNewEmissionsToUpdates(Map<String, Object> updates, @NonNull DataSnapshot snapshot, String emissionType, String date, String category, double valueToRemove) {
        if (snapshot.child(emissionType).child(date).child(category).getValue() instanceof Number) {
            double currentEmission = snapshot.child(emissionType).child(date).child(category).getValue(double.class);
            updates.put(emissionType + "/" + date + "/" + category, currentEmission - valueToRemove);
        }
    }

}
