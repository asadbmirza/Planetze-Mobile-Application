package com.example.plantezemobileapplication.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.Habit;

import java.util.ArrayList;

public class InactiveHabitAdapter extends AbstractHabitAdapter<InactiveHabitAdapter.MyViewHolder> {
    protected EcoTrackerHabitActivity view;

    public InactiveHabitAdapter(ArrayList<Habit> habits, String[] originalCategories, Integer[] originalImpacts, Activity context) {
        super(habits, originalCategories, originalImpacts);
        this.view = (EcoTrackerHabitActivity) context;
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
