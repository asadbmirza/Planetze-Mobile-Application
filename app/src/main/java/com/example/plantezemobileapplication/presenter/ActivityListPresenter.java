package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.ActivityListModel;
import com.example.plantezemobileapplication.model.HabitModel;
import com.example.plantezemobileapplication.utils.Question;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityListPresenter {
    private ActivityListModel model;
    private HabitModel habitModel;

    public ActivityListPresenter() {
        this.model = new ActivityListModel(this);
        this.habitModel = new HabitModel();
    }

    public void calculateTodaysActivity(List<Question> questions, List<Integer> enteredValues, String day, String month, String week) {
        Map<String, Object> dailyTotals = new HashMap<>();
        Map<String, Object> monthlyTotals = new HashMap<>();
        Map<String, Object> loggedActivities = new HashMap<>();
        double dailyTotal = 0;
        double factor = 0;
        double energyTotal = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getSelectedAnswer() == -1) {
                continue;
            }
            if (questions.get(i).getCategory().equals("energyConsumption")) {
                factor = questions.get(i).getSelectedAnswerObject().getWeight();
                monthlyTotals.put("energyEmissions", enteredValues.get(i) * factor);
                energyTotal = energyTotal + enteredValues.get(i) * factor;
                continue;
            }
            if (enteredValues.get(i) != 0) {
                Map<String, Object> userInput = new HashMap<>();
                String questionName = questions.get(i).getTitle().substring(0, questions.get(i).getTitle().indexOf('$'));
                if (questions.get(i).getAnswers().length > 0) {
                    String selectedAnswer = questions.get(i).getSelectedAnswerObject().getAnswerText();
                    userInput.put(selectedAnswer, enteredValues.get(i));
                }
                else {
                    userInput.put("inputValue", enteredValues.get(i));
                }
                loggedActivities.put(questionName, userInput);
            }
            factor = questions.get(i).getSelectedAnswerObject().getWeight();
            dailyTotal += enteredValues.get(i) * factor;
        }
        dailyTotals.put(questions.get(0).getCategory(), dailyTotal);
        dailyTotals.put("total", dailyTotal);
        monthlyTotals.put(questions.get(0).getCategory(), dailyTotal);
        monthlyTotals.put("total", dailyTotal + energyTotal);
        model.uploadEmissions("dailyEmissions", day, dailyTotals);
        model.uploadEmissions("weeklyEmissions", week, dailyTotals);
        model.uploadEmissions("monthlyEmissions", month, monthlyTotals);
        model.uploadActivityLog(loggedActivities, day);
    }

    
}
