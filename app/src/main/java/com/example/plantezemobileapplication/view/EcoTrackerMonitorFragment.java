package com.example.plantezemobileapplication.view;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.EcoMonitorModel;
import com.example.plantezemobileapplication.model.HabitModel;
import com.example.plantezemobileapplication.presenter.EcoTrackerEmissionsPresenter;
import com.example.plantezemobileapplication.presenter.EcoTrackerMonitorPresenter;
import com.example.plantezemobileapplication.utils.DailyEmission;
import com.example.plantezemobileapplication.utils.Question;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EcoTrackerMonitorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EcoTrackerMonitorFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_DAY = "currentDay";
    private static final String ARG_MONTH = "currentMonth";

    // TODO: Rename and change types of parameters
    TextView totalDailyEmissionView;
    TextView transportationEmissionView;
    TextView foodEmissionView;
    TextView consumptionEmissionView;
    TextView montitorHeading;
    EcoTrackerMonitorPresenter presenter;
    private ArrayList<DailyEmission> dailyEmissions;
    private String currentDay;
    private String currentMonth;
    private String currentWeek;
    private MaterialCalendarView calendarView;
    private Dialog dialog;

    public EcoTrackerMonitorFragment() {
        // Required empty public constructor
    }

    public static EcoTrackerMonitorFragment newInstance(String currentDay, String currentMonth, String currentWeek) {
        EcoTrackerMonitorFragment fragment = new EcoTrackerMonitorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DAY, currentDay);
        args.putString(ARG_MONTH, currentMonth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentDay = getArguments().getString(ARG_DAY);
            currentMonth = getArguments().getString(ARG_MONTH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco_tracker_monitor, container, false);;
        Button transporationActivityBtn = view.findViewById(R.id.transportation_log_btn);
        Button foodActivityBtn = view.findViewById(R.id.food_consumption_log);
        Button consumptionActivityBtn = view.findViewById(R.id.consumption_log_btn);
        Button historyBtn = view.findViewById(R.id.history_button);


        //Update CO2 tracker display
        montitorHeading = view.findViewById(R.id.monitor_heading);
        totalDailyEmissionView = view.findViewById(R.id.daily_emission_text);
        transportationEmissionView = view.findViewById(R.id.transportation_emission_text);
        foodEmissionView = view.findViewById(R.id.food_emission_text);
        consumptionEmissionView = view.findViewById(R.id.consumption_emission_text);

        presenter = new EcoTrackerMonitorPresenter(this, new EcoMonitorModel());

        Date currentDate = new Date();
        currentDay = getCurrentDay(currentDate);
        currentMonth = getCurrentMonth(currentDate);
        currentWeek = getCurrentWeek(currentDate);

        presenter.getTodaysActivities(currentDate);
        presenter.getDefaultVehicle();
        presenter.getUserEnergy();

        transporationActivityBtn.setOnClickListener(this);
        foodActivityBtn.setOnClickListener(this);
        consumptionActivityBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);

        //Set up history calendar
        dailyEmissions = new ArrayList<>();
        dialog = new Dialog(requireContext());

        // Inflate the layout for this fragment
        return view;
    }

    public void setTotalDailyEmissionView(String text) {

        System.out.println(text);
        totalDailyEmissionView.setText(text);

    }

    public void setTransportationEmissionView(String text) {
        transportationEmissionView.setText(text);
    }

    public void setFoodEmissionView(String text) {
        foodEmissionView.setText(text);
    }

    public void setConsumptionEmissionView(String text) {
        consumptionEmissionView.setText(text);
    }

    @Override
    public void onClick(View v) {
        Button clickedBtn = (Button) v;
        List<Question> questionList = null;
        String activityName = "";

        if (clickedBtn.getId() == R.id.transportation_log_btn) {
            questionList = presenter.initializeTransportationQuestions();
            activityName = "Transportation Activities";
        }
        else if (clickedBtn.getId() == R.id.food_consumption_log) {
            questionList = presenter.initializeFoodQuestions();
            activityName = "Food Consumption Activities";
        }
        else if (clickedBtn.getId() == R.id.consumption_log_btn) {
            questionList = presenter.initializeConsumptionQuestions();
            activityName = "Shopping & Consumption Activities";
        } else if (clickedBtn.getId() == R.id.history_button) {
            displayHistoryDialog();
            return;
        }



        Fragment targetFragment = ActivityListFragment.newInstance(activityName, questionList, currentDay, currentMonth, currentWeek);
        FragmentTransaction transaction = requireActivity()
                .getSupportFragmentManager()
                .beginTransaction();



        transaction.replace(R.id.fragmentContainerView, targetFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public String getCurrentDay(Date currentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }

    public String getCurrentMonth(Date currentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(currentDate);
    }

    public String getCurrentWeek(Date currentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ww");
        return dateFormat.format(currentDate);
    }

    private void displayHistoryDialog() {
        dialog.setContentView(R.layout.eco_tracker_calendar);
        calendarView = dialog.findViewById(R.id.calendarView);
        this.presenter.fetchDailyEmissions().addOnSuccessListener(task -> {
            addEmissionsToCalendar();
            addCalenderListeners();
        });
        dialog.show();
    }

    public void setDailyEmissions(ArrayList<DailyEmission> dailyEmissions) {
        this.dailyEmissions = dailyEmissions;
    }

    public void addEmissionsToCalendar() {
        ArrayList<CalendarDay> activityDays = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (DailyEmission dailyEmission : dailyEmissions) {
            Date date;
            try {
                date = sdf.parse(dailyEmission.getId()); // e.g., "2024-11-24"

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                CalendarDay calendarDay = CalendarDay.from(year, month, day);
                activityDays.add(calendarDay);  // Add the CalendarDay to the list

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        calendarView.addDecorator(new EventDecorator(Color.GREEN, activityDays));
    }

    public void addCalenderListeners() {
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // Get the year, month, and day from the selected date
                int year = date.getYear();
                int month = date.getMonth(); // Note: month is 0-based
                int day = date.getDay();

                Toast.makeText(getContext(), "Selected: " + year + "-" + (month + 1) + "-" + day, Toast.LENGTH_SHORT).show();

                showDailyEmissionDetails(date);
            }
        });

    }

    public void showDailyEmissionDetails(CalendarDay date) {
        presenter.getTodaysActivities(date.getDate());
        presenter.getDefaultVehicle();
        presenter.getUserEnergy();
        currentDay = getCurrentDay(date.getDate());
        currentMonth = getCurrentMonth(date.getDate());
        currentWeek = getCurrentWeek(date.getDate());
        displayNewDay();
        dialog.dismiss();

    }

    private void displayNewDay() {
        if (currentDay.equals(getCurrentDay(new Date()))) {
            montitorHeading.setText("Today's Emissions");
            return;
        }

        try {
            // Parse the input date
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = inputFormat.parse(currentDay);

            // Format to desired output
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US);
            String formattedDate = outputFormat.format(date);
            montitorHeading.setText("Emissions on " + formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}