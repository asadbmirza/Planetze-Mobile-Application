package com.example.plantezemobileapplication.presenter;

import android.util.Log;

import com.example.plantezemobileapplication.model.EcoMonitorModel;
import com.example.plantezemobileapplication.utils.Answer;
import com.example.plantezemobileapplication.utils.Question;
import com.example.plantezemobileapplication.view.EcoTrackerMonitorFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcoTrackerMonitorPresenter {
    private EcoMonitorModel model;
    private EcoTrackerMonitorFragment view;
    //TODO: USE SHAREDPREFERENCES TO RETRIEVE/STORE DAILY EMISSIONS?

    public EcoTrackerMonitorPresenter() {}

    public EcoTrackerMonitorPresenter(EcoTrackerMonitorFragment view) {
        model = new EcoMonitorModel(this);
        this.view = view;
    }

    public EcoTrackerMonitorPresenter(EcoMonitorModel model, EcoTrackerMonitorFragment view) {
        this.model = model;
        this.view = view;
    }

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
                new Answer("Short-Haul (<1,500km)", 225),
                new Answer("Long-Haul (>1,500km)", 825),
        };
        transportationQuestions.add(new Question("Drive Personal Vehicle$Enter distance driven (in km)", answers1,"transportation"));
        transportationQuestions.add(new Question("Take Public Transportation$Enter time spent (in hours)", answers2,"transportation"));
        transportationQuestions.add(new Question("Cycling or Walking$Enter distance travelled (in km)", new Answer[0],"transportation"));
        transportationQuestions.add(new Question("Flight (Short-Haul or Long-Haul)$Enter number of flights taken", answers3,"transportation"));

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
        foodQuestions.add(new Question("Meal$Enter number of servings consumed", answers1,"food"));

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
        double energyFactors[] = {0.9, 0.7, 2.5, 1.5, 0.4};
        int chosenEnergyIndex = 0; // TODO: GET MODEL TO FIGURE OUT WHICH ENERGY WAS CHOSEN
        Answer[] answers4 = {
                new Answer("Electricity", 0.7),
                new Answer("Gas", energyFactors[chosenEnergyIndex]),
                new Answer("Water", 0.4),
        };
        consumptionQuestions.add(new Question("Buy New Clothes$Enter number of clothing items purchased", answers1,"consumption"));
        consumptionQuestions.add(new Question("Buy Electronics$Enter number of devices purchased", answers2,"consumption"));
        consumptionQuestions.add(new Question("Other Purchases$Enter number of purchases", answers3,"consumption"));
        consumptionQuestions.add(new Question("Energy Bills$Enter the specific bill amount", answers4,"consumption"));

        consumptionQuestions.get(0).setSelectedAnswer(0);
        return consumptionQuestions;
    }

}
