package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.EcoMonitorModel;
import com.example.plantezemobileapplication.utils.Question;
import com.example.plantezemobileapplication.view.ActivityListFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityListPresenter {
    private EcoMonitorModel model;
    private ActivityListFragment view;

    public ActivityListPresenter() {}

    public ActivityListPresenter(ActivityListFragment view) {
        this.model = new EcoMonitorModel();
        this.view = view;
    }

    public ActivityListPresenter(EcoMonitorModel model, ActivityListFragment view) {
        this.model = model;
        this.view = view;
    }

    public void calculateTodaysActivity(List<Question> questions, List<Integer> enteredValues) {
        Map<String, Object> totals = new HashMap<>();
        Map<String, Object> loggedActivities = new HashMap<>();
        double total = 0;
        double factor = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getSelectedAnswer() == -1) {
                continue;
            }
            if (enteredValues.get(i) != 0) {
                String questionName = questions.get(i).getTitle().substring(0, questions.get(i).getTitle().indexOf('$'));
                loggedActivities.put(questionName, enteredValues.get(i));
            }
            factor = questions.get(i).getSelectedAnswerObject().getWeight();
            total += enteredValues.get(i) * factor;
        }
        totals.put(questions.get(0).getCategory(), total);
        totals.put("total", total);
        model.uploadActivityEmissions(totals);
        model.uploadActivityLog(loggedActivities);
    }
}
