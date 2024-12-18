package com.example.plantezemobileapplication.utils;

import java.util.Objects;

public class Emission {
    String id;
    double transportation;
    double food;
    double total;
    double consumption;

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

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "DailyEmission{" +
                "id='" + id + '\'' +
                ", transportation=" + transportation +
                ", food=" + food +
                ", total=" + total +
                ", consumption=" + consumption +
                '}';
    }
}
