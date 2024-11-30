package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.DailyEmission;
import com.example.plantezemobileapplication.model.EcoTrackerModel;
import com.example.plantezemobileapplication.view.EcoTrackerCalendarActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;

public class EcoTrackerEmissionsPresenter {
    EcoTrackerModel model;
    EcoTrackerCalendarActivity view;

    public EcoTrackerEmissionsPresenter(EcoTrackerCalendarActivity view, EcoTrackerModel model) {
        this.model = model;
        this.view = view;
    }

    public Task<Void> fetchDailyEmissions() {
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();
        this.model.getDailyEmissions(new EcoTrackerModel.OnDailyEmissionsListener() {
            @Override
            public void onDailyEmissionsFetched(ArrayList<DailyEmission> itemList) {
                view.setDailyEmissions(itemList);
                taskCompletionSource.setResult(null);
            }
        });
        return taskCompletionSource.getTask();
    }
}
