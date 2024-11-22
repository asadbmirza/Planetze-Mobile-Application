package com.example.plantezemobileapplication.presenter;

import android.content.Context;

import com.example.plantezemobileapplication.model.EcoTrackerModel;
import com.example.plantezemobileapplication.model.Habit;
import com.example.plantezemobileapplication.view.EcoTrackerHabitActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import org.json.JSONObject;
import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class EcoTrackerHabitPresenter {
    private EcoTrackerHabitActivity view;
    private EcoTrackerModel model;
    private ArrayList<Habit> habits;
    public EcoTrackerHabitPresenter(EcoTrackerHabitActivity view, EcoTrackerModel model) {
        this.view = view;
        this.model = model;
        this.habits = new ArrayList<Habit>();

        try {
            String json = loadJSONFromAsset(view);
            if (json != null) {
                parseJSON(json);
            }
            fetchActiveHabits();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String loadJSONFromAsset(Context context) throws IOException {
        String json = null;
        InputStream is = context.getAssets().open("habitList.json");
        int size = is.available();  // Get the file size
        byte[] buffer = new byte[size];
        is.read(buffer);  // Read the file
        is.close();
        json = new String(buffer, StandardCharsets.UTF_8);  // Convert byte array to String
        return json;
    }

    private void parseJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray habitsArray = jsonObject.getJSONArray("habits");

            // Parse habits data and add to habitList
            for (int i = 0; i < habitsArray.length(); i++) {
                JSONObject habitObject = habitsArray.getJSONObject(i);
                // Parse each habit object, and add it to habitList
                String name = habitObject.getString("name");
                String category = habitObject.getString("category");
                String impact = habitObject.getString("impact");
                String activity = habitObject.getString("activity");
                Habit habit = new Habit(name, category, activity, Integer.parseInt(impact));
                this.habits.add(habit);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Task<Boolean> addHabit(Habit habit) {
        if (habits.contains(habit)) {
            return model.addHabit(habit);
        }
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();
        taskCompletionSource.setResult(false);
        return taskCompletionSource.getTask();
    }

    public void fetchActiveHabits() {

        this.model.getActiveHabits(activeHabitList -> {
            for (int i = 0; i < activeHabitList.size(); i++) {
                System.out.println(activeHabitList.get(i).getDays());
                habits.remove(activeHabitList.get(i));
            }
            System.out.println("Habits fetched: " + habits.size());
            view.setHabits(habits);
            view.createNotifications(activeHabitList);
        });
    }
    public void redirectUser() {
        boolean filledSurvey = false; //Will be replaced with firebase auth call later

        if (filledSurvey) {
            //navigate to dashboard page
        }
        //stay on this page
    }




}
