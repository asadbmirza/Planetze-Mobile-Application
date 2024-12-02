package com.example.plantezemobileapplication.presenter;

import android.util.Log;

import com.example.plantezemobileapplication.model.EcoMonitorModel;
import com.example.plantezemobileapplication.utils.Answer;
import com.example.plantezemobileapplication.utils.Question;

import java.util.ArrayList;
import java.util.List;

public class EcoTrackerMonitorPresenter {
    private int defaultVehicleIndex;
    private int energyTypeIndex;

    public EcoTrackerMonitorPresenter() {}


    public List<Question> initializeTransportationQuestions() {
        List<Question> transportationQuestions = new ArrayList<>();
        Answer[] answers1 = {
                new Answer("Gasoline", 0.24),
                new Answer("Diesel", 0.27),
                new Answer("Hybrid", 0.16),
                new Answer("Electric", 0.05),
        };
        Answer[] answers2 = {
                new Answer("Bus", 6),
                new Answer("Train", 4.25),
                new Answer("Subway", 3),
        };
        Answer[] answers3 = {
                new Answer("Walking", 0),
                new Answer("Cycling", 0),
        };
        Answer[] answers4 = {
                new Answer("Short-Haul (<1,500km)", 225),
                new Answer("Long-Haul (>1,500km)", 825),
        };
        transportationQuestions.add(new Question("Drive Personal Vehicle$Distance driven (in km)", answers1,"transportation"));
        transportationQuestions.add(new Question("Take Public Transportation$Time spent (in hours)", answers2,"transportation"));
        transportationQuestions.add(new Question("Active Transportation (Walking or Cycling)$Distance travelled (in km)", answers3,"transportation"));
        transportationQuestions.add(new Question("Flight (Short-Haul or Long-Haul)$Number of flights taken", answers4,"transportation"));

        // Set user's default vehicle if already chosen from annual questionnaire
        transportationQuestions.get(0).setSelectedAnswer(defaultVehicleIndex);
        return transportationQuestions;
    }

    public List<Question> initializeFoodQuestions() {
        List<Question> foodQuestions = new ArrayList<>();
        Answer[] answers1 = {
                new Answer("Beef", 50),
                new Answer("Pork", 12),
                new Answer("Chicken", 7),
                new Answer("Fish", 12.5),
                new Answer("Plant-Based", 1.5),
        };
        foodQuestions.add(new Question("Meal$Number of servings", answers1,"food"));

        return foodQuestions;
    }

    public List<Question> initializeConsumptionQuestions() {
        List<Question> consumptionQuestions = new ArrayList<>();
        Answer[] answers1 = {
                new Answer("#Clothing factor", 20),
        };
        Answer[] answers2 = {
                new Answer("Smartphone", 70),
                new Answer("Laptop", 300),
                new Answer("TV", 600),
        };
        Answer[] answers3 = {
                new Answer("Furniture", 200),
                new Answer("Appliances", 500),
        };
        final double[] energyFactors = {0.9, 0.7, 2.5, 1.5, 0.4};
        Answer[] answers4 = {
                new Answer("Electricity", 0.7),
                new Answer("Gas", energyFactors[energyTypeIndex]),
                new Answer("Water", 0.4),
        };
        consumptionQuestions.add(new Question("Buy New Clothes$Number of clothing items", answers1,"consumption"));
        consumptionQuestions.add(new Question("Buy Electronics$Number of devices", answers2,"consumption"));
        consumptionQuestions.add(new Question("Other Purchases$Number of purchases", answers3,"consumption"));
        consumptionQuestions.add(new Question("Energy Bills$Specific bill amount", answers4,"energyConsumption"));

        // Set new clothes factor since user cannot choose
        consumptionQuestions.get(0).setSelectedAnswer(0);
        return consumptionQuestions;
    }

    public void setDefaultVehicle(int defaultVehicleIndex) {
        this.defaultVehicleIndex = defaultVehicleIndex;
    }

    public void setUserEnergy(int energyTypeIndex) {
        this.energyTypeIndex = energyTypeIndex;
    }
}
