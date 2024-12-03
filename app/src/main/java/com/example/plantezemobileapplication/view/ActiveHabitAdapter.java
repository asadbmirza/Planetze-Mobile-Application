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

public class ActiveHabitAdapter extends AbstractHabitAdapter<ActiveHabitAdapter.MyViewHolder> {

    protected EcoTrackerHabitFragment view;

    public ActiveHabitAdapter(ArrayList<Habit> habits, String[] originalCategories, Integer[] originalImpacts, EcoTrackerHabitFragment view) {
        super(habits, originalCategories, originalImpacts);
        this.view = view;

    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewCategory;
        private TextView textViewImpact;
        private TextView textViewCompleted;
        private Button buttonRemove;
        private Button buttonUpdate;

        private Button buttonLogHabit;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.habit_name);
            textViewCategory = itemView.findViewById(R.id.habit_category);
            textViewImpact = itemView.findViewById(R.id.habit_impact);
            textViewCompleted = itemView.findViewById(R.id.habit_completed);
            buttonRemove = itemView.findViewById(R.id.habit_remove);
            buttonUpdate = itemView.findViewById(R.id.habit_update);
            buttonLogHabit = itemView.findViewById(R.id.log_habit_button);

        }


    }

    @NonNull
    @Override
    public ActiveHabitAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_habit_card, parent, false);
        return new ActiveHabitAdapter.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ActiveHabitAdapter.MyViewHolder holder, int position) {

        String name = habits.get(position).getName();
        String category = habits.get(position).getCategory();
        int impact = habits.get(position).getImpact();
        int timesCompleted = habits.get(position).getTimesCompleted();

        holder.textViewName.setText(name);
        holder.textViewCategory.setText(category);
        holder.textViewImpact.setText("Impact: " + Integer.toString(impact));
        holder.textViewCompleted.setText("Times Completed: " + timesCompleted);
        final int finalPosition = position;

        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.displayRemoveDialog(habits.get(finalPosition));
            }
        });

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.displayHabitDialog(habits.get(finalPosition));
            }
        });

        holder.buttonLogHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timesCompleted = habits.get(finalPosition).getTimesCompleted();
                habits.get(finalPosition).setTimesCompleted(timesCompleted + 1);
                System.out.println(timesCompleted);
                view.updateHabit(habits.get(finalPosition));
                view.displayLoggedHabit(habits.get(finalPosition));
            }
        });

    }



}

