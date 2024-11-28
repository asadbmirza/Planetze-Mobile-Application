package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.DailyEmission;
import com.example.plantezemobileapplication.model.EcoTrackerCalendarModel;
import com.example.plantezemobileapplication.view.EcoTrackerCalendarActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;

public class EcoTrackerCalendarPresenter {
    EcoTrackerCalendarModel model;
    EcoTrackerCalendarActivity view;

    public EcoTrackerCalendarPresenter(EcoTrackerCalendarActivity view, EcoTrackerCalendarModel model) {
        this.model = model;
        this.view = view;
    }

    public Task<Void> fetchDailyEmissions() {
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();
        this.model.getDailyEmissions(new EcoTrackerCalendarModel.OnDailyEmissionsListener() {
            @Override
            public void onDailyEmissionsFetched(ArrayList<DailyEmission> itemList) {
                view.setDailyEmissions(itemList);
                taskCompletionSource.setResult(null);
            }
        });
        return taskCompletionSource.getTask();
    }
}
