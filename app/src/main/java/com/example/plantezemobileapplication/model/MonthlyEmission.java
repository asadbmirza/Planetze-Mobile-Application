package com.example.plantezemobileapplication.model;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MonthlyEmission extends Emission {

    private double energyEmissions;
    public MonthlyEmission() {

    }
    public MonthlyEmission(String id, double transportation, double food, double total,
                         double consumption, double energyEmissions) {
        this.id = id;
        this.transportation = transportation;
        this.food = food;
        this.total = total;
        this.consumption = consumption;
        this.energyEmissions = energyEmissions;
    }

    public double getEnergyEmissions() {
        return energyEmissions;
    }

    public void setEnergyEmissions(double energyEmissions) {
        this.energyEmissions = energyEmissions;
    }

    @Override
    public String toString() {
        return "MonthlyEmission{" +
                "energyEmissions=" + energyEmissions +
                ", id='" + id + '\'' +
                ", transportation=" + transportation +
                ", food=" + food +
                ", total=" + total +
                ", consumption=" + consumption +
                '}';
    }
}

