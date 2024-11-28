package com.example.plantezemobileapplication.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class DailyEmission {
    String id;
    double transportation;
    double food;
    double total;
    double consumption;
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

    public double getTransportation() {
        return transportation;
    }

    public void setTransportation(double transportation) {
        this.transportation = transportation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Map<String, Integer> getActivities() {
        return activities;
    }

    public void setActivities(Map<String, Integer> activities) {
        this.activities = activities;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyEmission that = (DailyEmission) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DailyEmission{" +
                "id='" + id + '\'' +
                ", transportation=" + transportation +
                ", food=" + food +
                ", total=" + total +
                ", consumption=" + consumption +
                ", activities=" + activities +
                '}';
    }
}
