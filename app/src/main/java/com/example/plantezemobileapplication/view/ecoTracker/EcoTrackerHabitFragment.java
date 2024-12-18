package com.example.plantezemobileapplication.view.ecoTracker;

import static com.example.plantezemobileapplication.view.ecoTracker.HabitNotification.REQUEST_CODE_POST_NOTIFICATIONS;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.HabitModel;
import com.example.plantezemobileapplication.utils.Habit;
import com.example.plantezemobileapplication.utils.MonthlyEmission;
import com.example.plantezemobileapplication.presenter.EcoTrackerHabitPresenter;
import com.example.plantezemobileapplication.utils.TaskResult;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Calendar;

import android.Manifest;
import android.os.Build;
import android.widget.ToggleButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class EcoTrackerHabitFragment extends Fragment {

    private EcoTrackerHabitPresenter presenter;
    private RecyclerView recyclerView;
    private InactiveHabitAdapter inactiveHabitAdapter;
    private ActiveHabitAdapter activeHabitAdapter;
    private AbstractHabitAdapter displayedHabitAdapter;
    private SearchView searchView;
    private String categories[] = {
                "Food Consumption",
                "Consumption and Shopping",
                "Transportation",
                "Home and Energy"
    };
    ;
    private Integer impacts[] = {1, 2, 3, 4, 5};
    private ArrayList<String> cFilters;
    private ArrayList<Integer> iFilters;
    private CheckBox[] cCheckboxes;
    private CheckBox[] iCheckboxes;
    private TextView noHabitsMessage;
    private TextView suggestedHabitsMessage;
    private Dialog dialog;
    private HabitNotification notification;
    private ToggleButton toggleHabitButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_eco_tracker_habit, container, false);
        FirebaseApp.initializeApp(requireContext());

        inactiveHabitAdapter = new InactiveHabitAdapter(new ArrayList<Habit>(), categories, impacts, this);
        activeHabitAdapter = new ActiveHabitAdapter(new ArrayList<Habit>(), categories, impacts, this);
        presenter = new EcoTrackerHabitPresenter(this, new HabitModel(), getActivity());
        displayedHabitAdapter = inactiveHabitAdapter;

        cCheckboxes = new CheckBox[categories.length];
        cFilters = new ArrayList<>();
        iCheckboxes = new CheckBox[impacts.length];
        iFilters = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view_habit);
        recyclerView.setAdapter(displayedHabitAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView = view.findViewById(R.id.search_view_habit);
        noHabitsMessage = view.findViewById(R.id.no_habit_text);
        suggestedHabitsMessage = view.findViewById(R.id.suggested_habits_text);
        dialog = new Dialog(getContext());
        notification = new HabitNotification(getActivity());
        toggleHabitButton = view.findViewById(R.id.toggleHabitButton);

        Button filterButton = view.findViewById(R.id.filter_button);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                displayedHabitAdapter.filterByName(query);
                toggleNoHabitsMessage(displayedHabitAdapter.getItemCount());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                displayedHabitAdapter.filterByName(newText);
                toggleNoHabitsMessage(displayedHabitAdapter.getItemCount());
                return false;
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilterDialog();
            }
        });

        toggleHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleHabitButton.isChecked()) {
                    displayedHabitAdapter = activeHabitAdapter;
                    suggestedHabitsMessage.setVisibility(View.GONE);
                } else {
                    displayedHabitAdapter = inactiveHabitAdapter;
                    suggestedHabitsMessage.setVisibility(View.VISIBLE);


                }
                recyclerView.setAdapter(displayedHabitAdapter);


                searchView.setQuery(displayedHabitAdapter.getSearchQuery(), false);
                cFilters = displayedHabitAdapter.getcFilters();
                iFilters = displayedHabitAdapter.getiFilters();
                System.out.println(cFilters);
                System.out.println(iFilters);
                toggleNoHabitsMessage(displayedHabitAdapter.getItemCount());

            }
        });
        return view;
    }

    public void setHabits(ArrayList<Habit> habits) {

        inactiveHabitAdapter.updateHabits(habits);
        inactiveHabitAdapter.filterByCategory(cFilters);
        inactiveHabitAdapter.filterByImpact(iFilters);
    }

    public void removeActiveHabit(Habit habit) {
        this.presenter.removeActiveHabit(habit);
        notification.removeWeeklyNotification(habit);
    }

    public void setActiveHabits(ArrayList<Habit> habits) {
        activeHabitAdapter.updateHabits(habits);
        inactiveHabitAdapter.filterByCategory(cFilters);
        inactiveHabitAdapter.filterByImpact(iFilters);
        createNotifications(habits);
    }

    public void updateHabit(Habit habit) {
        presenter.updateHabit(habit);
    }

    public void createNotifications(ArrayList<Habit> habits) {
        for (Habit habit: habits) {
            notification.createWeeklyNotifications(habit);
        }
    }



    public void setMonthlyEmissions(ArrayList<MonthlyEmission> emissions) {
        ArrayList<Double> consumption = new ArrayList<>();
        ArrayList<Double> transportation = new ArrayList<>();
        ArrayList<Double> food = new ArrayList<>();
        ArrayList<Double> energyEmissions = new ArrayList<>();

        for (MonthlyEmission emission : emissions) {
            if (emission.getConsumption() != 0) {
                consumption.add(emission.getConsumption());
            }
            if (emission.getFood() != 0) {
                food.add(emission.getFood());
            }
            if (emission.getTransportation() != 0) {
                transportation.add(emission.getTransportation());
            }
            if (emission.getEnergyEmissions() != 0) {
                energyEmissions.add(emission.getEnergyEmissions());
            }
        }
        inactiveHabitAdapter.setFood(food);
        inactiveHabitAdapter.setEnergyEmissions(energyEmissions);
        inactiveHabitAdapter.setConsumption(consumption);
        inactiveHabitAdapter.setTransportation(transportation);
    }

    //For enabling permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission granted. Notifications enabled.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission denied. Notifications disabled.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showTimePickerDialog(String[] selectedTime) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
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
    public void displayHabitDialog(Habit habit) {
        dialog.setContentView(R.layout.new_habit_dialog);


        ChipGroup chipGroup = dialog.findViewById(R.id.chipGroupDays);
        Button submitHabit = dialog.findViewById(R.id.submit_habit_button);
        Button exitHabit = dialog.findViewById(R.id.exit_habit_button);
        Button pickTime = dialog.findViewById(R.id.time_picker_button);
        String selectedTime[] = {habit.getTime()}; // store selected time
        if (selectedTime[0] != "") {
            pickTime.setText(selectedTime[0]);
        }
        System.out.println(habit.getDays());
        System.out.println(chipGroup.getChildCount());
        for (int i = 0; i < habit.getDays().size(); i++) {

            Chip chip = (Chip) chipGroup.getChildAt(habit.getDays().get(i) - 1);
            chip.setChecked(true);
        }
        pickTime.setOnClickListener(v -> showTimePickerDialog(selectedTime));

        submitHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1 is sunday, 2 is monday, etc
                ArrayList<Integer> activeDays = new ArrayList<>();
                for (int i = 0; i < chipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) chipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        activeDays.add(i + 1);
                    }
                }


                if (activeDays.size() == 0) {
                    Toast.makeText(getContext(), "Need at least one active day", Toast.LENGTH_SHORT).show();
                }
                else if (selectedTime[0].equals("")) {
                    Toast.makeText(getContext(), "Need to select a time", Toast.LENGTH_SHORT).show();
                }
                else {
                    System.out.println(selectedTime[0] + " " + activeDays.toString());
                    habit.setDays(activeDays);
                    habit.setTime(selectedTime[0]);
                    presenter.addHabit(habit).addOnCompleteListener(completedTask -> {
                        if (completedTask.isSuccessful() && completedTask.getResult() != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                                }
                            }
                            TaskResult taskResult = completedTask.getResult();
                            Toast.makeText(getContext(), taskResult.getMessage(), Toast.LENGTH_SHORT).show();
                            notification.removeCreateWeeklyNotification(habit);
                        } else {
                            Toast.makeText(getContext(), "An unexpected error occurred", Toast.LENGTH_SHORT).show();
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

    public void displayLoggedHabit(Habit habit) {
        Toast.makeText(getContext(), "Fantastic job completing the habit \"" + habit.getName() + "\"", Toast.LENGTH_SHORT).show();
    }
    public void displayFilterDialog() {
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

                displayedHabitAdapter.filterByCategory(cFilters);
                displayedHabitAdapter.filterByImpact(iFilters);
                toggleNoHabitsMessage(displayedHabitAdapter.getItemCount());

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void displayRemoveDialog(Habit habit) {
        dialog.setContentView(R.layout.remove_habit_dialog);
        Button remove = dialog.findViewById(R.id.submit_remove_habit_button);
        Button cancel = dialog.findViewById(R.id.exit_remove_button);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeActiveHabit(habit);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private CheckBox initCheckBox(String query) {
        CheckBox c = new CheckBox(getContext());
        c.setText(query);
        return c;
    }

    private void initCheckBoxes() {
        LinearLayout cLayout = dialog.findViewById(R.id.filters_category);
        LinearLayout iLayout = dialog.findViewById(R.id.filters_impact);

        for (int i = 0; i < categories.length; i++) {

            cCheckboxes[i] = initCheckBox(categories[i]);
            cCheckboxes[i].setChecked(false);
            if (cFilters.contains(categories[i]) && cFilters.size() != categories.length) {
                cCheckboxes[i].setChecked(true);
            }
            cLayout.addView(cCheckboxes[i]);
        }

        for (int i = 0; i < impacts.length; i++) {
            iCheckboxes[i] = initCheckBox(Integer.toString(impacts[i]));
            iCheckboxes[i].setChecked(false);
            if (iFilters.contains(impacts[i]) && iFilters.size() != impacts.length) {
                iCheckboxes[i].setChecked(true);
            }
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

    public EcoTrackerHabitPresenter getPresenter() {
        return presenter;
    }


}
