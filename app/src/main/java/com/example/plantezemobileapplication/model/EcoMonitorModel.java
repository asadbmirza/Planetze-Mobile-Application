package com.example.plantezemobileapplication.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.plantezemobileapplication.presenter.EcoTrackerMonitorPresenter;
import com.example.plantezemobileapplication.view.EcoTrackerMonitorFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class EcoMonitorModel {
    private FirebaseAuth auth;
    private EcoTrackerMonitorPresenter presenter;
    private EcoTrackerMonitorFragment view;
    private DatabaseReference ref;
    private String userId;

    public EcoMonitorModel() {
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
    }

    public EcoMonitorModel(EcoTrackerMonitorFragment view, EcoTrackerMonitorPresenter presenter) {
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        this.view = view;
        this.presenter = presenter;
    }

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    public String getCurrentMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    public void setDefaultVehicle() {
        FirebaseUser currUser = auth.getCurrentUser();

        if (currUser != null) {
            userId = currUser.getUid();
        }
        else {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2"; //TODO: UPDATE THIS CONDITION
        }

        ref.child("users").child(userId).child("defaultVehicle").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getValue() != null) {
                    presenter.setDefaultVehicle(snapshot.getValue(int.class));
                }
                else {
                    presenter.setDefaultVehicle(-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                presenter.setDefaultVehicle(-1);
            }
        });
    }

    public void setUserEnergy() {
        FirebaseUser currUser = auth.getCurrentUser();

        if (currUser != null) {
            userId = currUser.getUid();
        }
        else {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2"; //TODO: UPDATE THIS CONDITION
        }

        ref.child("users").child(userId).child("energySource").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getValue() != null) {
                    presenter.setUserEnergy(snapshot.getValue(int.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void uploadDailyEmissions(Map<String, Object> emissions) {
        FirebaseUser currUser = auth.getCurrentUser();

        if (currUser != null) {
            userId = currUser.getUid();
        }
        else {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2"; //TODO: UPDATE THIS CONDITION
        }

        String formattedDate = getFormattedDate();

        for (String activity : emissions.keySet()) {
            ref.child("users").child(userId).child("dailyEmissions").child(formattedDate).child(activity).runTransaction(new Transaction.Handler() {
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

    public void uploadMonthlyEmissions(Map<String, Object> emissions) {
        FirebaseUser currUser = auth.getCurrentUser();

        if (currUser != null) {
            userId = currUser.getUid();
        }
        else {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2"; //TODO: UPDATE THIS CONDITION
        }

        String currentMonth = getCurrentMonth();

        for (String activity : emissions.keySet()) {
            ref.child("users").child(userId).child("monthlyEmissions").child(currentMonth).child(activity).runTransaction(new Transaction.Handler() {
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

        String formattedDate = getFormattedDate();

        ref.child("users").child(userId).child("dailyEmissions").child(formattedDate).child("activities").updateChildren(activities);
    }

    public void getTodaysActivities() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);

        FirebaseUser currUser = auth.getCurrentUser();

        if (currUser != null) {
            userId = currUser.getUid();
        }
        else {
            userId = "hRGBz0zBIGRbm6wJm9RA5Jii97M2"; //TODO: UPDATE THIS CONDITION
        }

        ref.child("users").child(userId).child("dailyEmissions").child(formattedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot transportationEmissions = snapshot.child("transportation");
                    DataSnapshot foodEmissions = snapshot.child("food");
                    DataSnapshot consumptionEmissions = snapshot.child("consumption");
                    DataSnapshot totalEmissions = snapshot.child("total");

                    if (transportationEmissions.exists()) {
                        String formattedEmission = String.format("%.1f", transportationEmissions.getValue(double.class));
                        view.setTransportationEmissionView(formattedEmission + "kg");
                    }
                    if (foodEmissions.exists()) {
                        String formattedEmission = String.format("%.1f", foodEmissions.getValue(double.class));
                        view.setFoodEmissionView(formattedEmission + "kg");
                    }
                    if (consumptionEmissions.exists()) {
                        String formattedEmission = String.format("%.1f", consumptionEmissions.getValue(double.class));
                        view.setConsumptionEmissionView(formattedEmission + "kg");
                    }
                    if (totalEmissions.exists()) {
                        String formattedEmission = String.format("%.1f", totalEmissions.getValue(double.class));
                        view.setTotalDailyEmissionView(formattedEmission);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
