package com.example.plantezemobileapplication.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.MyViewHolder> {
    private ArrayList<Habit> habits;
    private ArrayList<Habit> originalHabits;
    public HabitAdapter(ArrayList<Habit> h) {
        this.habits = h;
        this.originalHabits = new ArrayList<>(h);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        public TableLayout habitCard;
        public TextView textViewName;
        public TextView textViewCategory;
        public TextView textViewImpact;
        public Button add;

        public MyViewHolder(View itemView) {
            super(itemView);
//            habitCard = itemView.findViewById(R.id.habit_card);
            textViewName = itemView.findViewById(R.id.habit_name);
            textViewCategory = itemView.findViewById(R.id.habit_category);
            textViewImpact = itemView.findViewById(R.id.habit_impact);
            add = itemView.findViewById(R.id.habit_add);

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
        Log.d("EcoTracker", "Binding habit: " + habits.get(position).name);
        holder.textViewName.setText(habits.get(position).name);
        holder.textViewCategory.setText(habits.get(position).category);
        holder.textViewImpact.setText(Integer.toString(habits.get(position).impact));

    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public void updateList(ArrayList<Habit> habits) {
        this.habits = habits;
        notifyDataSetChanged();
    }

    public void filterByName(String query) {
        ArrayList<Habit> filteredHabits = new ArrayList<Habit>();
        for (int i = 0; i < originalHabits.size(); i++) {
            if (originalHabits.get(i).name.toLowerCase().contains(query.toLowerCase())) {
                filteredHabits.add(originalHabits.get(i));
            }
        }
        habits = filteredHabits;
        notifyDataSetChanged();

    }

    public void filterByCategory(String query) {
        ArrayList<Habit> filteredHabits = new ArrayList<Habit>();
        for (int i = 0; i < habits.size(); i++) {
            if (habits.get(i).category.toLowerCase().contains(query.toLowerCase())) {
                filteredHabits.add(habits.get(i));
            }
        }
        habits = filteredHabits;
        notifyDataSetChanged();

    }

    public void filterByImpact(int query) {
        ArrayList<Habit> filteredHabits = new ArrayList<Habit>();
        for (int i = 0; i < habits.size(); i++) {
            if (habits.get(i).impact == query) {
                filteredHabits.add(habits.get(i));
            }
        }
        habits = filteredHabits;
        notifyDataSetChanged();
    }

    public void resetFilters() {
        habits = new ArrayList<>(originalHabits);
        notifyDataSetChanged();
    }

}
