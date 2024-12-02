package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.utils.DailyEmission;
import com.example.plantezemobileapplication.model.HabitModel;
import com.example.plantezemobileapplication.view.EcoTrackerCalendarActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;

public class EcoTrackerEmissionsPresenter {
    HabitModel model;
    EcoTrackerCalendarActivity view;

    public EcoTrackerEmissionsPresenter(EcoTrackerCalendarActivity view, HabitModel model) {
        this.model = model;
        this.view = view;
    }

    public Task<Void> fetchDailyEmissions() {
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();
        this.model.getDailyEmissions(new HabitModel.OnDailyEmissionsListener() {
            @Override
            public void onDailyEmissionsFetched(ArrayList<DailyEmission> itemList) {
                view.setDailyEmissions(itemList);
                taskCompletionSource.setResult(null);
            }
        });
        return taskCompletionSource.getTask();
    }
}
