package com.example.plantezemobileapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.utils.Habit;

import java.util.ArrayList;
import java.util.HashMap;

public class InactiveHabitAdapter extends AbstractHabitAdapter<InactiveHabitAdapter.MyViewHolder> {
    protected EcoTrackerHabitFragment view;

    private ArrayList<Double> consumption;
    private ArrayList<Double> transportation;
    private ArrayList<Double> food;
    private ArrayList<Double> energyEmissions;
    private double highest;

    public InactiveHabitAdapter(ArrayList<Habit> habits, String[] originalCategories, Integer[] originalImpacts, EcoTrackerHabitFragment view) {
        super(habits, originalCategories, originalImpacts);
        this.view = view;
        this.consumption = new ArrayList<>();
        this.transportation = new ArrayList<>();
        this.food = new ArrayList<>();
        this.energyEmissions = new ArrayList<>();
    }

    public void setConsumption(ArrayList<Double> consumption) {
        this.consumption = consumption;
    }

    public ArrayList<Double> getConsumption() {
        return consumption;
    }

    public void setTransportation(ArrayList<Double> transportation) {
        this.transportation = transportation;
    }
    public ArrayList<Double> getTransportation() {
        return transportation;
    }

    public void setFood(ArrayList<Double> food) {
        this.food = food;
    }

    public ArrayList<Double> getFood() {
        return food;
    }

    public void setEnergyEmissions(ArrayList<Double> energyEmissions) {
        this.energyEmissions = energyEmissions;
    }

    public ArrayList<Double> getEnergyEmissions() {
        return energyEmissions;
    }

    public void suggestHabitSort() {
        double tAvg = computeAvg(transportation);
        double fAvg = computeAvg(food);
        double cAvg = computeAvg(consumption);
        double eAvg = computeAvg(energyEmissions);

        HashMap<String, Double> hashs = new HashMap<>();
        hashs.put("Transportation", tAvg);
        hashs.put("Food Consumption", fAvg);
        hashs.put("Consumption and Shopping", cAvg);
        hashs.put("Home and Energy", eAvg);

        ArrayList<String> order  = new ArrayList<>();
        for (String key : hashs.keySet()) {
            if (hashs.get(key) == - 1 || order.isEmpty()) {
                order.add(0, key);
            }
            else {
                int count = 0;
                while (count < order.size() && hashs.get(key) < hashs.get(order.get(count))) {
                    count ++;

                }
                order.add(count, key);
            }
        }

        ArrayList<Habit> newHabits = new ArrayList<>();

        for (String key: order) {
            for (int i = 0; i < habits.size(); i ++) {
                if (habits.get(i).getCategory().equals(key)) {
                    newHabits.add(habits.get(i));
                }
            }
        }
        super.updateHabits(newHabits);
    }

    @Override
    public void updateHabits(ArrayList<Habit> habits) {
        super.updateHabits(habits);
        suggestHabitSort();
    }

    private double computeAvg(ArrayList<Double> ds) {
        double sum = 0;
        for (Double d : ds) {
            sum = sum + d;
        }
        if (ds.isEmpty()) {
            return -1;
        }
        return sum / ds.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewCategory;
        private TextView textViewImpact;
        private Button buttonAdd;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.habit_name);
            textViewCategory = itemView.findViewById(R.id.habit_category);
            textViewImpact = itemView.findViewById(R.id.habit_impact);
            buttonAdd = itemView.findViewById(R.id.habit_add);
        }


    }



    @NonNull
    @Override
    public InactiveHabitAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InactiveHabitAdapter.MyViewHolder holder, int position) {

        String name =habits.get(position).getName();
        String category = habits.get(position).getCategory();
        int impact = habits.get(position).getImpact();

        holder.textViewName.setText(name);
        holder.textViewCategory.setText(category);
        holder.textViewImpact.setText("Impact: " + Integer.toString(impact));
        final int finalPosition = position;

        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view.displayHabitDialog(habits.get(finalPosition));


            }
        });

    }




}
