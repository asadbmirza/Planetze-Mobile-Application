package com.example.plantezemobileapplication.view;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;

import java.util.ArrayList;
import java.util.Arrays;


public class EcoTrackerHabitActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eco_tracker_habit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.eco_tracker_habit), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<Habit> habits = new ArrayList<>(Arrays.asList(
                new Habit("Cycling", "transportation", 3),
                new Habit("Walking", "transportation", 1),
                new Habit("Donating electronics", "recycling", 2),
                new Habit("Vegan", "energy", 2))
        );
        habitAdapter = new HabitAdapter(habits);
        recyclerView = findViewById(R.id.recycler_view_habit);
        recyclerView.setAdapter(habitAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.search_view_habit);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                habitAdapter.filterByName(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                habitAdapter.filterByName(newText);
                System.out.println("Changed!");
                return false;
            }
        });

    }
}
