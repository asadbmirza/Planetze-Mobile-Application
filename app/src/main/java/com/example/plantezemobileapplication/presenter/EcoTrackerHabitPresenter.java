package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.EcoTrackerModel;
import com.example.plantezemobileapplication.view.EcoTrackerHabitActivity;

public class EcoTrackerHabitPresenter {
    private EcoTrackerHabitActivity view;
    private EcoTrackerModel model;
    public EcoTrackerHabitPresenter(EcoTrackerHabitActivity view, EcoTrackerModel model) {
        this.view = view;
        this.model = model;
    }

    public void redirectUser() {
        boolean filledSurvey = false; //Will be replaced with firebase auth call later

        if (filledSurvey) {
            //navigate to dashboard page
        }
        //stay on this page
    }

//    public void nextCard(int currentIndex, String[] contentArray) {
//
//        //Ensure info is all filled in submit to db, then run the following lines
//        view.setCurrentIndex( currentIndex + 1);
//        view.setContentText(contentArray[view.getCurrentIndex()]);
//    }


}
