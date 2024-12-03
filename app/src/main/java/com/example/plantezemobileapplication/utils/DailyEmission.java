package com.example.plantezemobileapplication.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyEmission extends Emission {
    // Map of date to list of activity details
    private Map<String, List<ActivityDetail>> activities;

    public DailyEmission() {
        this.activities = new HashMap<>();
    }

    public DailyEmission(String id, double transportation, double food, double total,
                         double consumption, Map<String, List<ActivityDetail>> activities) {
        this.id = id;
        this.transportation = transportation;
        this.food = food;
        this.total = total;
        this.consumption = consumption;
        this.activities = activities;
    }

    public Map<String, List<ActivityDetail>> getActivities() {
        return activities;
    }

    public void setActivities(Map<String, List<ActivityDetail>> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "DailyEmission{" +
                "activities=" + activities +
                ", id='" + id + '\'' +
                ", transportation=" + transportation +
                ", food=" + food +
                ", total=" + total +
                ", consumption=" + consumption +
                '}';
    }

    public static class ActivityDetail {
        private String category;
        private int enteredValue;
        private String questionTitle;
        private SelectedAnswer selectedAnswer;

        public ActivityDetail() {
        }

        public ActivityDetail(String category, int enteredValue, String questionTitle, SelectedAnswer selectedAnswer) {
            this.category = category;
            this.enteredValue = enteredValue;
            this.questionTitle = questionTitle;
            this.selectedAnswer = selectedAnswer;
        }

        // Getters and Setters
        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getEnteredValue() {
            return enteredValue;
        }

        public void setEnteredValue(int enteredValue) {
            this.enteredValue = enteredValue;
        }

        public String getQuestionTitle() {
            return questionTitle;
        }

        public void setQuestionTitle(String questionTitle) {
            this.questionTitle = questionTitle;
        }

        public SelectedAnswer getSelectedAnswer() {
            return selectedAnswer;
        }

        public void setSelectedAnswer(SelectedAnswer selectedAnswer) {
            this.selectedAnswer = selectedAnswer;
        }

        @Override
        public String toString() {
            return "ActivityDetail{" +
                    "category='" + category + '\'' +
                    ", enteredValue=" + enteredValue +
                    ", questionTitle='" + questionTitle + '\'' +
                    ", selectedAnswer=" + selectedAnswer +
                    '}';
        }
    }

    public static class SelectedAnswer {
        private String answerText;
        private double weight;

        public SelectedAnswer() {
        }

        public SelectedAnswer(String answerText, double weight) {
            this.answerText = answerText;
            this.weight = weight;
        }

        // Getters and Setters
        public String getAnswerText() {
            return answerText;
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "SelectedAnswer{" +
                    "answerText='" + answerText + '\'' +
                    '}';
        }
    }
}
