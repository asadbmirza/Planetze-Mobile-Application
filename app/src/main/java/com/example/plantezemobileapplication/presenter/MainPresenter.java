package com.example.plantezemobileapplication.presenter;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plantezemobileapplication.model.MainModel;
import com.example.plantezemobileapplication.view.main.MainView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainPresenter {
    private final MainView view;
    private final MainModel mainModel;

    public MainPresenter(MainView view, MainModel mainModel) {
        this.view = view;
        this.mainModel = mainModel;
    }

    public void loadUserData() {
        mainModel.loadUserData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("fullName").getValue(String.class);
                String welcomeText = name != null ? String.format("%s, %s", getTimeGreeting(), name.split(" ")[0]) : getTimeGreeting();
                view.setWelcomeText(welcomeText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("MainPresenter", "Failed to read value.", databaseError.toException());
            }
        });
    }

    public void setCurrentDate() {
        String date = getCurrentDate();
        view.setCurrentDate(date);
    }

    private String getTimeGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay >= 5 && hourOfDay < 12) {
            return "Good Morning";
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            return "Good Afternoon";
        } else {
            return "Good Evening";
        }
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd", Locale.CANADA);
        return dateFormat.format(calendar.getTime());
    }
}
