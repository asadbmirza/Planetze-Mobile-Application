package com.example.plantezemobileapplication.view;

import static com.example.plantezemobileapplication.view.HabitNotification.REQUEST_CODE_POST_NOTIFICATIONS;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Calendar;


public class EcoTrackerHabitActivity extends AppCompatActivity {

    private EcoTrackerHabitPresenter presenter;
    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private SearchView searchView;
    private boolean checkboxInit = false;
    private String categories[] = {"transportation", "recycling", "energy", "waste", "food", "nature"};
    private int impacts[] = {1, 2, 3, 4};
    private ArrayList<String> cFilters;
    private ArrayList<Integer> iFilters;
    private CheckBox[] cCheckboxes;
    private CheckBox[] iCheckboxes;
    private TextView noHabitsMessage;
    private Dialog dialog;


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

        habitAdapter = new HabitAdapter(new ArrayList<Habit>(), this);

        cCheckboxes = new CheckBox[categories.length];
        cFilters = new ArrayList<>();
        iCheckboxes = new CheckBox[impacts.length];
        iFilters = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_habit);
        recyclerView.setAdapter(habitAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.search_view_habit);
        noHabitsMessage = findViewById(R.id.no_habit_text);
        dialog = new Dialog(this);

        Button filterButton = findViewById(R.id.filter_button);

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

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilterDialog();
            }
        });

    }

    public void setHabits(ArrayList<Habit> habits) {
        habitAdapter.updateHabits(habits);
        habitAdapter.filterByCategory(cFilters);
        habitAdapter.filterByImpact(iFilters);
    }

    //For enabling permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted. Notifications enabled.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied. Notifications disabled.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showTimePickerDialog(String[] selectedTime) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minuteOfHour) -> {
                    // Format selected time (HH:mm)
                    selectedTime[0] = String.format("%02d:%02d", hourOfDay, minuteOfHour);

                    Button timePickerButton = dialog.findViewById(R.id.time_picker_button);
                    timePickerButton.setText(selectedTime[0]);
                },
                hour,
                minute,
                false
        );

        timePickerDialog.show();
    }
    void displayHabitDialog(Habit habit) {
        dialog.setContentView(R.layout.new_habit_dialog);


        ChipGroup chipGroup = dialog.findViewById(R.id.chipGroupDays);
        Button submitHabit = dialog.findViewById(R.id.submit_habit_button);
        Button exitHabit = dialog.findViewById(R.id.exit_habit_button);

        Button pickTime = dialog.findViewById(R.id.time_picker_button);
        String selectedTime[] = {""}; // store selected time
        pickTime.setOnClickListener(v -> showTimePickerDialog(selectedTime));

        submitHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                ArrayList<String> activeDays = new ArrayList<String>();
                for (int i = 0; i < chipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) chipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        activeDays.add(days[i]);
                    }
                }


                if (activeDays.size() == 0) {
                    Toast.makeText(EcoTrackerHabitActivity.this, "Need at least one active day", Toast.LENGTH_SHORT).show();
                }
                else if (selectedTime[0].equals("")) {
                    Toast.makeText(EcoTrackerHabitActivity.this, "Need to select a time", Toast.LENGTH_SHORT).show();
                }
                else {
                    System.out.println(selectedTime[0] + " " + activeDays.toString());
                    presenter.addHabit(habit).addOnCompleteListener(completedTask -> {
                        if (completedTask.isSuccessful()) {
                            Boolean result = completedTask.getResult();
                            if (result != null && result) {
                                Toast.makeText(EcoTrackerHabitActivity.this, "Habit added successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EcoTrackerHabitActivity.this, "Habit addition failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EcoTrackerHabitActivity.this, "An unexpected error occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }

            }
        });

        exitHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();





    }
    void displayFilterDialog() {
        dialog.setContentView(R.layout.filter_dialog);
        initCheckBoxes();



        Button submit = dialog.findViewById(R.id.submit_filter_button);
        Button cancel = dialog.findViewById(R.id.cancel_filter_button);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cFilters.clear();
                iFilters.clear();

                for (int i = 0; i < cCheckboxes.length; i++) {
                    if (cCheckboxes[i].isChecked()) {

                        cFilters.add(categories[i]);
                    }
                }
                for (int i = 0; i < iCheckboxes.length; i++) {
                    if (iCheckboxes[i].isChecked()) {
                        iFilters.add(Integer.valueOf(impacts[i]));
                    }
                }

                habitAdapter.filterByCategory(cFilters);
                habitAdapter.filterByImpact(iFilters);
                toggleNoHabitsMessage(habitAdapter.getItemCount());

                dialog.dismiss();
            }
        });

        dialog.show();

    }


    private CheckBox initCheckBox(String query) {
        CheckBox c = new CheckBox(this);
        c.setText(query);
        return c;
    }

    private void initCheckBoxes() {
        LinearLayout cLayout = dialog.findViewById(R.id.filters_category);
        LinearLayout iLayout = dialog.findViewById(R.id.filters_impact);

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
