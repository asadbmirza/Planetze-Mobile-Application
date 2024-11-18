package com.example.plantezemobileapplication.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.EcoTrackerModel;
import com.example.plantezemobileapplication.model.Habit;
import com.example.plantezemobileapplication.presenter.EcoTrackerHabitPresenter;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Arrays;


public class EcoTrackerHabitActivity extends AppCompatActivity {

    private EcoTrackerHabitPresenter presenter;
    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private SearchView searchView;

    private String habitNames[] = {"Cycling", "Walking", "Donating electronics", "Vegan"};
    private String categories[] = {"transportation", "recycling", "energy"};
    private int impacts[] = {1, 2, 3, 4};
    private boolean checkboxInit = false;
    private CheckBox[] cCheckboxes;
    private CheckBox[] iCheckboxes;
    private TextView noHabitsMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eco_tracker_habit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.eco_tracker_habit), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        presenter = new EcoTrackerHabitPresenter(this, new EcoTrackerModel());

        ArrayList<Habit> habits = new ArrayList<>(Arrays.asList(
                new Habit(habitNames[0], categories[0], impacts[3]),
                new Habit(habitNames[1], categories[0], impacts[1]),
                new Habit(habitNames[2], categories[1], impacts[2]),
                new Habit(habitNames[3], categories[2], impacts[2]))
        );
        habitAdapter = new HabitAdapter(habits, presenter);

        cCheckboxes = new CheckBox[categories.length];
        iCheckboxes = new CheckBox[impacts.length];
        recyclerView = findViewById(R.id.recycler_view_habit);
        recyclerView.setAdapter(habitAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.search_view_habit);
        Button filterButton = findViewById(R.id.filter_button);
        noHabitsMessage = findViewById(R.id.no_habit_text);


        Dialog filterDialog = new Dialog(this);
        filterDialog.setContentView(R.layout.filter_dialog);
        initCheckBoxes(filterDialog);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilterDialog(habitAdapter, filterDialog);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                habitAdapter.filterByName(query);
                toggleNoHabitsMessage(habitAdapter.getItemCount());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                habitAdapter.filterByName(newText);
                toggleNoHabitsMessage(habitAdapter.getItemCount());
                return false;
            }
        });


    }
    public HabitAdapter getHabitAdapter() {
        return habitAdapter;
    }

    private void displayFilterDialog(HabitAdapter habitAdapter, Dialog filterDialog) {

        Button submit = filterDialog.findViewById(R.id.submit_filter_button);
        Button cancel = filterDialog.findViewById(R.id.cancel_filter_button);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.cancel();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> cFilters = new ArrayList<String>();
                ArrayList<Integer> iFilters = new ArrayList<Integer>();
                int cCount = 0;
                int iCount = 0;
                for (int i = 0; i < cCheckboxes.length; i++) {
                    if (cCheckboxes[i].isChecked()) {

                        cFilters.add(categories[i]);
                        cCount++;
                    }
                }
                for (int i = 0; i < iCheckboxes.length; i++) {
                    if (iCheckboxes[i].isChecked()) {
                        iFilters.add(Integer.valueOf(impacts[i]));
                        iCount++;
                    }
                }

                habitAdapter.filterByCategory(cFilters);
                habitAdapter.filterByImpact(iFilters);
                toggleNoHabitsMessage(habitAdapter.getItemCount());

                filterDialog.dismiss();
            }
        });

        filterDialog.show();

    }


    private CheckBox initCheckBox(String query) {
        CheckBox c = new CheckBox(this);
        c.setText(query);
        return c;
    }

    private void initCheckBoxes(Dialog filterDialog) {
        LinearLayout cLayout = filterDialog.findViewById(R.id.filters_category);
        LinearLayout iLayout = filterDialog.findViewById(R.id.filters_impact);

        for (int i = 0; i < categories.length; i++) {
            cCheckboxes[i] = initCheckBox(categories[i]);
            cCheckboxes[i].setChecked(true);
            cLayout.addView(cCheckboxes[i]);
        }
        for (int i = 0; i < impacts.length; i++) {
            iCheckboxes[i] = initCheckBox(Integer.toString(impacts[i]));
            iCheckboxes[i].setChecked(true);
            iLayout.addView(iCheckboxes[i]);
        }
    }

    private void toggleNoHabitsMessage(int itemCount) {
        if (itemCount == 0) {
            noHabitsMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noHabitsMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }



}
