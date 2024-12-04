package com.example.plantezemobileapplication.view.ecoTracker;

import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;

public class EventDecorator implements DayViewDecorator {

    private final int color;
    private final ArrayList<CalendarDay> days;

    public EventDecorator(int color, ArrayList<CalendarDay> days) {
        this.color = color;
        this.days = days;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // Return true if the day is in the activityDays list
        return days.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        // Set the background color for the day
        view.addSpan(new ForegroundColorSpan(color));
    }
}

