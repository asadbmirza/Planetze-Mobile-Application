package com.example.plantezemobileapplication.utils;

import java.util.HashMap;
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
        private HashMap<String, Double> values;

        // Getters and setters
        public HashMap<String, Double> getValues() {
            return values;
        }

        public void setValues(HashMap<String, Double> values) {
            this.values = values;
        }

        public void addSubcategory(String subcategory, double value) {
            this.values.put(subcategory, value);
        }

        public Double getSubcategoryValue(String subcategory) {
            return this.values.get(subcategory);
        }

        @Override
        public String toString() {
            return "ActivityDetail{" +
                    "values=" + values +
                    '}';
        }
    }
}
