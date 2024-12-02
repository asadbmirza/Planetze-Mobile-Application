package com.example.plantezemobileapplication.utils;

import java.util.Map;

public class DailyEmission extends Emission {
    private Map<String, ActivityDetail> activities;

    public DailyEmission() {

    }
    public DailyEmission(String id, double transportation, double food, double total,
                         double consumption, Map<String, ActivityDetail> activities) {
        this.id = id;
        this.transportation = transportation;
        this.food = food;
        this.total = total;
        this.consumption = consumption;
        this.activities = activities;
    }

    public Map<String, ActivityDetail> getActivities() {
        return activities;
    }

    public void setActivities(Map<String, ActivityDetail> activities) {
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
        private int enteredValue;
        private String selectedOption;

        // Getters and setters
        public int getEnteredValue() {
            return enteredValue;
        }

        public void setEnteredValue(int enteredValue) {
            this.enteredValue = enteredValue;
        }

        public String getSelectedOption() {
            return selectedOption;
        }

        public void setSelectedOption(String selectedOption) {
            this.selectedOption = selectedOption;
        }
    }
}
