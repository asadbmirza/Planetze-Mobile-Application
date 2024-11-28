package com.example.plantezemobileapplication.view;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.DailyEmission;
import com.example.plantezemobileapplication.model.EcoTrackerCalendarModel;
import com.example.plantezemobileapplication.presenter.EcoTrackerCalendarPresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EcoTrackerCalendarActivity extends AppCompatActivity {

    private ArrayList<DailyEmission> dailyEmissions;
    private MaterialCalendarView calendarView;
    private EcoTrackerCalendarPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.eco_tracker_calendar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.eco_tracker_calendar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        calendarView = findViewById(R.id.calendarView);
        dailyEmissions = new ArrayList<>();
        this.presenter = new EcoTrackerCalendarPresenter(this, new EcoTrackerCalendarModel());
        this.presenter.fetchDailyEmissions().addOnSuccessListener(task -> {
            addEmissionsToCalendar();
            addCalenderListeners();
        });


    }

    public void setDailyEmissions(ArrayList<DailyEmission> dailyEmissions) {
        this.dailyEmissions = dailyEmissions;

    }

    private void addEmissionsToCalendar() {
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

    private void addCalenderListeners() {
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // Get the year, month, and day from the selected date
                int year = date.getYear();
                int month = date.getMonth(); // Note: month is 0-based
                int day = date.getDay();

                Toast.makeText(getApplicationContext(), "Selected: " + year + "-" + (month + 1) + "-" + day, Toast.LENGTH_SHORT).show();

                showDailyEmissionDetails(date);
            }
        });

    }

    private void showDailyEmissionDetails(CalendarDay date) {
        String formattedDate = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDay();
        for (DailyEmission dailyEmission: dailyEmissions) {
            System.out.println(dailyEmission.getId());
            System.out.println(formattedDate);
            if (dailyEmission.getId().equals(formattedDate)) {
                System.out.println(dailyEmission);
                return;
            }
        }
        //If Not found
        //Create brand new day in firebase


    }

}

