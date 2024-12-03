package com.example.plantezemobileapplication.view.ecoTracker;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import com.example.plantezemobileapplication.utils.Habit;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractHabitAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected ArrayList<Habit> habits;
    protected ArrayList<Habit> filteredHabits;
    protected ArrayList<Habit> originalHabits;
    protected String searchQuery;
    protected ArrayList<String> cFilters;
    protected ArrayList<Integer> iFilters;

    protected String[] originalCategories;
    protected Integer[] originalImpacts;

    // Constructor
    public AbstractHabitAdapter(ArrayList<Habit> habits, String[] originalCategories, Integer[] originalImpacts) {
        this.habits = habits;
        this.originalHabits = new ArrayList<>(habits);
        this.filteredHabits = new ArrayList<>(habits);
        this.searchQuery = "";
        this.cFilters = new ArrayList<>();
        this.iFilters = new ArrayList<>();
        this.originalCategories = originalCategories;
        this.originalImpacts = originalImpacts;
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public void filterByName(String query) {
        searchQuery = query;
        ArrayList<Habit> newHabits = new ArrayList<>();
        System.out.println("Filtered list query: " + query);

        for (Habit habit : filteredHabits) {
            if (habit.getName().toLowerCase().contains(query.toLowerCase())) {
                newHabits.add(habit);
            }
        }
        habits = newHabits;
        notifyDataSetChanged();
    }

    public void filterByCategory(ArrayList<String> queries) {
        cFilters = new ArrayList<>(queries);
        if (queries.isEmpty()) {
            cFilters = new ArrayList<>(Arrays.asList(originalCategories));
        }

        filterHabits();
    }

    public void filterByImpact(ArrayList<Integer> queries) {
        iFilters = new ArrayList<>(queries);
        if (queries.isEmpty()) {
            iFilters = new ArrayList<>(Arrays.asList(originalImpacts));
        }

        filterHabits();
    }

    private void filterHabits() {
        ArrayList<Habit> newHabits = new ArrayList<>();

        for (Habit habit : originalHabits) {
            if(cFilters.contains(habit.getCategory()) && iFilters.contains(habit.getImpact())) {
                newHabits.add(habit);
            }
        }
        filteredHabits = newHabits;
        filterByName(searchQuery);
        notifyDataSetChanged();
    }

    public void resetFilters() {
        habits = new ArrayList<>(originalHabits);
        filteredHabits = new ArrayList<>(originalHabits);
        cFilters.clear();
        iFilters.clear();
        searchQuery = "";
        notifyDataSetChanged();
    }



    public void updateHabits(ArrayList<Habit> habits) {
        this.originalHabits = new ArrayList<>(habits);
        this.habits = new ArrayList<>(habits);
        this.filteredHabits = new ArrayList<>(habits);
        notifyDataSetChanged();
    }

    public static abstract class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public ArrayList<String> getcFilters() {
        return cFilters;
    }
    public ArrayList<Integer> getiFilters() {
        return iFilters;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH holder, int position);


}
