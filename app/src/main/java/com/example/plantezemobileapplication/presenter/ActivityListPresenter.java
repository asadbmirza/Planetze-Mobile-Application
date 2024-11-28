package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.EcoMonitorModel;
import com.example.plantezemobileapplication.utils.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityListPresenter {
    private EcoMonitorModel model;

    public ActivityListPresenter() {
        this.model = new EcoMonitorModel();
    }

    public void calculateTodaysActivity(List<Question> questions, List<Integer> enteredValues) {
        Map<String, Object> dailyTotals = new HashMap<>();
        Map<String, Object> monthlyTotals = new HashMap<>();
        Map<String, Object> loggedActivities = new HashMap<>();
        double dailyTotal = 0;
        double factor = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getSelectedAnswer() == -1) {
                continue;
            }
            if (questions.get(i).getCategory().equals("energyConsumption")) {
                factor = questions.get(i).getSelectedAnswerObject().getWeight();
                monthlyTotals.put("energyEmissions", enteredValues.get(i) * factor);
            }
            else {
                if (enteredValues.get(i) != 0) {
                    String questionName = questions.get(i).getTitle().substring(0, questions.get(i).getTitle().indexOf('$'));
                    loggedActivities.put(questionName, enteredValues.get(i));
                }
                factor = questions.get(i).getSelectedAnswerObject().getWeight();
                dailyTotal += enteredValues.get(i) * factor;
            }
        }
        dailyTotals.put(questions.get(0).getCategory(), dailyTotal);
        dailyTotals.put("total", dailyTotal);
        monthlyTotals.put(questions.get(0).getCategory(), dailyTotal);
        monthlyTotals.put("total", dailyTotal);
        model.uploadDailyEmissions(dailyTotals);
        model.uploadMonthlyEmissions(monthlyTotals);
        model.uploadActivityLog(loggedActivities);
    }
}
