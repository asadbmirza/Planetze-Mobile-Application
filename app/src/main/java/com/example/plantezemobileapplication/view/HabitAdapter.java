package com.example.plantezemobileapplication.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.Habit;
import com.example.plantezemobileapplication.presenter.EcoTrackerHabitPresenter;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.MyViewHolder> {
    private ArrayList<Habit> habits;
    private ArrayList<Habit> filteredHabits;
    private ArrayList<Habit> originalHabits;
    private String searchQuery;
    private EcoTrackerHabitActivity view;
    private HabitNotification notification;

    public HabitAdapter(ArrayList<Habit> habits, Activity context) {
        this.habits = habits;
        this.originalHabits = new ArrayList<>(habits);
        this.filteredHabits = new ArrayList<>(habits);
        this.searchQuery = "";
        this.view = (EcoTrackerHabitActivity) context;
        this.notification = new HabitNotification(context);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        public TableLayout habitCard;
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

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String name =habits.get(position).getName();
        String category = habits.get(position).getCategory();
        int impact = habits.get(position).getImpact();

        holder.textViewName.setText(name);
        holder.textViewCategory.setText(category);
        holder.textViewImpact.setText("Impact: " + Integer.toString(impact));
        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Habit habit = new Habit(name, category, impact);
                view.displayHabitDialog(habit);


            }
        });

    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public void filterByName(String query) {
        searchQuery = query;
        ArrayList<Habit> newHabits = new ArrayList<Habit>();
        System.out.println("Filtered list query: " + query);
        for (int i = 0; i < filteredHabits.size(); i++) {
            if (filteredHabits.get(i).getName().toLowerCase().contains(query.toLowerCase())) {
                newHabits.add(filteredHabits.get(i));

            }
        }
        habits = newHabits;
        notifyDataSetChanged();


    }

    public void filterByCategory(ArrayList<String> querys) {
        if (querys.size() == 0) {
            return;
        }
        ArrayList<Habit> newHabits = new ArrayList<Habit>();

        for (int i = 0; i < originalHabits.size(); i++) {
            for (int j = 0; j < querys.size();j++) {
                if (originalHabits.get(i).getCategory().equals(querys.get(j))) {
                    newHabits.add(originalHabits.get(i));
                }
            }
        }
        filteredHabits = newHabits;
        filterByName(searchQuery);
        notifyDataSetChanged();

    }

    public void filterByImpact(ArrayList<Integer> querys) {
        if (querys.size() == 0) {
            return;
        }
        ArrayList<Habit> newHabits = new ArrayList<Habit>();
        for (int i = 0; i < filteredHabits.size(); i++) {
            for(int j = 0; j < querys.size();j++) {
                if (querys.get(j).equals(filteredHabits.get(i).getImpact())) {
                    newHabits.add(filteredHabits.get(i));
                }
            }
        }
        filteredHabits = newHabits;
        filterByName(searchQuery);
        notifyDataSetChanged();
    }

    public void resetFilters() {
        habits = new ArrayList<>(originalHabits);
        filteredHabits = new ArrayList<>(originalHabits);
        searchQuery = "";
        notifyDataSetChanged();
    }

    public void updateHabits(ArrayList<Habit> habits) {

        this.originalHabits = habits;
        this.habits = new ArrayList<>(habits);
        this.filteredHabits = new ArrayList<>(habits);
        notifyDataSetChanged();
    }



}
