package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.EcoTrackerModel;
import com.example.plantezemobileapplication.model.Habit;
import com.example.plantezemobileapplication.view.EcoTrackerHabitActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;


public class EcoTrackerHabitPresenter {
    private EcoTrackerHabitActivity view;
    private EcoTrackerModel model;
    public EcoTrackerHabitPresenter(EcoTrackerHabitActivity view, EcoTrackerModel model) {
        this.view = view;
        this.model = model;
    }

    public void addHabit(Habit habit) {
        Task<Boolean> task = model.addHabit(habit);
        task.addOnCompleteListener(taskResult -> {
            if (taskResult.isSuccessful()) {
                //send to frontend and remove habit

            }
            else {
                //display error message on frontend
            }
        });
    }
    public void redirectUser() {
        boolean filledSurvey = false; //Will be replaced with firebase auth call later

        if (filledSurvey) {
            //navigate to dashboard page
        }
        //stay on this page
    }




}
