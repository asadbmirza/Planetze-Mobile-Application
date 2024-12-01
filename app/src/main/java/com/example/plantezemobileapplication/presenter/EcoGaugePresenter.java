package com.example.plantezemobileapplication.presenter;

import androidx.annotation.NonNull;

import com.example.plantezemobileapplication.Emissions;
import com.example.plantezemobileapplication.User;
import com.example.plantezemobileapplication.view.ecogauge.EcoGaugeView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EcoGaugePresenter {
    EcoGaugeView view;
    User user;
    Emissions annualEmissions;
    LineChart emissionsTrendLineChart;

    public EcoGaugePresenter(User user, LineChart emissionsTrendLineChart, EcoGaugeView view) {
        this.user = user;
        this.emissionsTrendLineChart = emissionsTrendLineChart;
        this.view = view;
        this.annualEmissions = user.getAnnualEmissions();
    }

    @NonNull
    public PieDataSet getEmissionsBreakdownDataSet() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        if  (annualEmissions != null ){
            entries.add(new PieEntry(annualEmissions.getTransportation(), "Transportation"));
            entries.add(new PieEntry(annualEmissions.getConsumption(), "Consumption"));
            entries.add(new PieEntry(annualEmissions.getFood(), "Food"));
            entries.add(new PieEntry(annualEmissions.getHousing(), "Housing"));
        }

        return new PieDataSet(entries, "");
    }

    public LineDataSet getDailyEmissionTrendDataSet() {
        List<Entry> emissions = new ArrayList<>();
        HashMap<String, Emissions> dailyEmissions = user.getDailyEmissions();

        if (dailyEmissions != null) {
            List<String> dailyDates = new ArrayList<>(dailyEmissions.keySet());
            Collections.sort(dailyDates);
            int index = 0;

            for (String date : dailyDates) {
                emissions.add(new Entry(index, dailyEmissions.get(date).getTotal()));
                index++;
            }

            // Formats x-axis labels to shown only the month and date of each log
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd", Locale.getDefault());
            XAxis xAxis = emissionsTrendLineChart.getXAxis();
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int index = (int) value;
                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA).parse(dailyDates.get(index));
                        return dateFormat.format(date);
                    } catch (Exception e) {
                        return dailyDates.get(index);
                    }
                }
            });
        }

        return new LineDataSet(emissions, "");
    }

    public LineDataSet getWeeklyEmissionTrendDataSet() {
        List<Entry> emissions = new ArrayList<>();
        HashMap<String, Emissions> weeklyEmissions = user.getWeeklyEmissions();

        if (weeklyEmissions != null) {
            List<String> weeklyDates = new ArrayList<>(weeklyEmissions.keySet());
            Collections.sort(weeklyDates);
            int index = 0;

            for (String date : weeklyDates) {
                emissions.add(new Entry(index, weeklyEmissions.get(date).getTotal()));
                index++;
            }

            XAxis xAxis = emissionsTrendLineChart.getXAxis();
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int index = (int) value;
                    if (index >= 0 && index < weeklyDates.size()) {
                        return weeklyDates.get(index);
                    } else {
                        return "";
                    }
                }
            });
        }

        return new LineDataSet(emissions, "");
    }

    public LineDataSet getMonthlyEmissionTrendDataSet() {
        List<Entry> emissions = new ArrayList<>();
        HashMap<String, Emissions> monthlyEmissions = user.getMonthlyEmissions();

        if (monthlyEmissions != null) {
            List<String> monthlyDates = new ArrayList<>(monthlyEmissions.keySet());
            Collections.sort(monthlyDates);
            int index = 0;

            for (String date : monthlyDates) {
                emissions.add(new Entry(index, monthlyEmissions.get(date).getTotal()));
                index++;
            }

            XAxis xAxis = emissionsTrendLineChart.getXAxis();
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int index = (int) value;
                    if (index >= 0 && index < monthlyDates.size()) {
                        return monthlyDates.get(index);
                    } else {
                        return "";
                    }
                }
            });
        }

        return new LineDataSet(emissions, "");
    }
}
