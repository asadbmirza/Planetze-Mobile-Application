package com.example.plantezemobileapplication.presenter;

import static com.example.plantezemobileapplication.presenter.JSONParsing.loadJSONFromAsset;
import static com.example.plantezemobileapplication.presenter.JSONParsing.parseJSON;

import androidx.fragment.app.FragmentActivity;

import com.example.plantezemobileapplication.model.EcoTrackerModel;
import com.example.plantezemobileapplication.model.Habit;
import com.example.plantezemobileapplication.model.MonthlyEmission;
import com.example.plantezemobileapplication.utils.TaskResult;
import com.example.plantezemobileapplication.view.EcoTrackerHabitFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import org.json.JSONObject;
import org.json.*;

import java.util.ArrayList;


public class EcoTrackerHabitPresenter {
    private EcoTrackerHabitFragment view;
    private EcoTrackerModel model;
    private ArrayList<Habit> habits;
    public EcoTrackerHabitPresenter(EcoTrackerHabitFragment view, EcoTrackerModel model, FragmentActivity context) {
        this.view = view;
        this.model = model;
        this.habits = new ArrayList<Habit>();

        try {
            String json = loadJSONFromAsset(context);
            JSONArray j = parseJSON(json, "habits");
            if (j == null) throw new Exception("Habits not found");
            for (int i = 0; i < j.length(); i++) {
                JSONObject habitObject = j.getJSONObject(i);
                String name = habitObject.getString("name");
                String category = habitObject.getString("category");
                String impact = habitObject.getString("impact");
                String activity = habitObject.getString("activity");
                Habit habit = new Habit(name, category, activity, Integer.parseInt(impact));
                this.habits.add(habit);
            }
            fetchMonthlyEmissions().addOnCompleteListener(task ->  {
                fetchActiveHabits();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Task<TaskResult> addHabit(Habit habit) {
        if (habits.contains(habit)) {
            return model.addHabit(habit);
        }
        else {
            return updateHabit(habit);
        }
    }
    public Task<TaskResult> updateHabit(Habit habit) {
        return model.updateHabit(habit);
    }

    public void fetchActiveHabits() {

        this.model.getActiveHabits(activeHabitList -> {
            for (int i = 0; i < activeHabitList.size(); i++) {
                System.out.println(activeHabitList.get(i).getDays());
                habits.remove(activeHabitList.get(i));
            }
            System.out.println("Habits fetched: " + habits.size());
            view.setHabits(habits);
            view.setActiveHabits(activeHabitList);
            view.createNotifications(activeHabitList);
        });
    }

    public void removeActiveHabit(Habit habit) {
        String id = habit.getId();
        System.out.println(id);
        this.model.removeActiveHabit(id);
        habits.add(habit);
        view.setHabits(habits);
    }

    public Task<Void> fetchMonthlyEmissions() {
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();
        this.model.getMonthlyEmissions(new EcoTrackerModel.OnMonthlyEmissionsListener() {
            @Override
            public void onMonthlyEmissionsFetched(ArrayList<MonthlyEmission> itemList) {
                view.setMonthlyEmissions(itemList);
                taskCompletionSource.setResult(null);
            }
        });
        return taskCompletionSource.getTask();
    }

    public void redirectUser() {
        boolean filledSurvey = false; //Will be replaced with firebase auth call later

        if (filledSurvey) {
            //navigate to dashboard page
        }
        //stay on this page
    }




}
