package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.EcoMonitorModel;
import com.example.plantezemobileapplication.model.HabitModel;
import com.example.plantezemobileapplication.utils.Answer;
import com.example.plantezemobileapplication.utils.DailyEmission;
import com.example.plantezemobileapplication.utils.Question;
import com.example.plantezemobileapplication.view.EcoTrackerMonitorFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EcoTrackerMonitorPresenter {
    private int defaultVehicleIndex;
    private int energyTypeIndex;

    private EcoTrackerMonitorFragment view;
    private EcoMonitorModel model;
    private HabitModel habitModel;

    public EcoTrackerMonitorPresenter(EcoTrackerMonitorFragment view, EcoMonitorModel model) {
        this.view = view;
        this.model = model;
        this.habitModel = new HabitModel();

        this.model.setCallback(new EcoMonitorCallback() {
            @Override
            public void onDefaultVehicleFetched(int vehicleIndex) {
                setDefaultVehicle(vehicleIndex);
            }

            @Override
            public void onUserEnergyFetched(int energyIndex) {
                setUserEnergy(energyIndex);
            }

            @Override
            public void onEmissionFetched(String category, double emission) {
                String formattedEmission = String.format("%.1f kg", emission);
                switch (category) {
                    case "transportation":
                        view.setTransportationEmissionView(formattedEmission);
                        break;
                    case "food":
                        view.setFoodEmissionView(formattedEmission);
                        break;
                    case "consumption":
                        view.setConsumptionEmissionView(formattedEmission);
                        break;
                    case "total":
                        view.setTotalDailyEmissionView(formattedEmission);
                        break;
                }
            }

            @Override
            public void onFetchError(String errorMessage) {
                System.err.println(errorMessage);
            }
        });
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
                new Answer("Walking", 0),
                new Answer("Cycling", 0),
        };
        Answer[] answers4 = {
                new Answer("Short-Haul (<1,500km)", 225),
                new Answer("Long-Haul (>1,500km)", 825),
        };
        transportationQuestions.add(new Question("Drive Personal Vehicle$Distance driven (in km)", answers1,"transportation"));
        transportationQuestions.add(new Question("Take Public Transportation$Time spent (in hours)", answers2,"transportation"));
        transportationQuestions.add(new Question("Active Transportation$Distance travelled (in km)", answers3,"transportation"));
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
        foodQuestions.add(new Question("Meal$Enter number of servings consumed", answers1,"food"));

        return foodQuestions;
    }

    public List<Question> initializeConsumptionQuestions() {
        List<Question> consumptionQuestions = new ArrayList<>();
        Answer[] answers1 = {
                new Answer("!Clothing factor", 20),
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
        consumptionQuestions.add(new Question("Buy New Clothes$Enter number of clothing items purchased", answers1,"consumption"));
        consumptionQuestions.add(new Question("Buy Electronics$Enter number of devices purchased", answers2,"consumption"));
        consumptionQuestions.add(new Question("Other Purchases$Enter number of purchases", answers3,"consumption"));
        consumptionQuestions.add(new Question("Energy Bills$Enter the specific bill amount", answers4,"energyConsumption"));

        // Set new clothes factor since user cannot choose
        consumptionQuestions.get(0).setSelectedAnswer(0);
        return consumptionQuestions;
    }

    public void getTodaysActivities(Date date) {
        this.model.getTodaysActivities(date);
    }
    public void setDefaultVehicle(int defaultVehicleIndex) {
        this.defaultVehicleIndex = defaultVehicleIndex;
    }
    public void getDefaultVehicle() {
        this.model.getDefaultVehicle();
    }

    public void setUserEnergy(int energyTypeIndex) {
        this.energyTypeIndex = energyTypeIndex;
    }

    public void getUserEnergy() {
        this.model.getUserEnergy();
    }
    public Task<Void> fetchDailyEmissions() {
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();
        this.habitModel.getDailyEmissions(new HabitModel.OnDailyEmissionsListener() {
            @Override
            public void onDailyEmissionsFetched(ArrayList<DailyEmission> itemList) {
                System.out.println(itemList);

                view.setDailyEmissions(itemList);
                if (!taskCompletionSource.getTask().isComplete()) {
                    taskCompletionSource.setResult(null);
                }
            }
        });
        return taskCompletionSource.getTask();
    }
}

