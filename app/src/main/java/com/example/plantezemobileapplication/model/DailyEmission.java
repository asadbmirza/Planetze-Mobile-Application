package com.example.plantezemobileapplication.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class DailyEmission extends Emission {
    private Map<String, Integer> activities;

    public DailyEmission() {

    }
    public DailyEmission(String id, double transportation, double food, double total,
                         double consumption, Map<String, Integer> activities) {
        this.id = id;
        this.transportation = transportation;
        this.food = food;
        this.total = total;
        this.consumption = consumption;
        this.activities = activities;
    }

    public Map<String, Integer> getActivities() {
        return activities;
    }

    public void setActivities(Map<String, Integer> activities) {
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
}
