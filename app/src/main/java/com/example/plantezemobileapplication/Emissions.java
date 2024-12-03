package com.example.plantezemobileapplication;

import java.io.Serializable;

public class Emissions implements Serializable {
    private float consumption;
    private float food;
    private float housing;
    private float transportation;

    public Emissions() {
        this.consumption = 0;
        this.food = 0;
        this.housing = 0;
        this.transportation = 0;
    }

    public Emissions(float consumption, float food, float housing, float transportation) {
        this.consumption = consumption;
        this.food = food;
        this.housing = housing;
        this.transportation = transportation;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }

    public float getFood() {
        return food;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public float getHousing() {
        return housing;
    }

    public void setHousing(float housing) {
        this.housing = housing;
    }

    public float getTransportation() {
        return transportation;
    }

    public void setTransportation(float transportation) {
        this.transportation = transportation;
    }

    public float getTotal() {
        return transportation + housing + food + consumption;
    }
}
