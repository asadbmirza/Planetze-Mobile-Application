package com.example.plantezemobileapplication.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.plantezemobileapplication.Emissions;
import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.User;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EcoGaugeFragment extends Fragment {

    PieChart emissionsBreakdownPieChart;
    static LineChart emissionsTrendLineChart;
    Button dailyTrendBtn, weeklyTrendBtn, monthlyTrendBtn;
    static User user;
    static Emissions annualEmissions;

    public EcoGaugeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emissionsBreakdownPieChart = view.findViewById(R.id.emission_breakdown_chart);
        emissionsTrendLineChart = view.findViewById(R.id.emissions_trend_chart);
        dailyTrendBtn = view.findViewById(R.id.daily_trend_btn);
        weeklyTrendBtn = view.findViewById(R.id.weekly_trend_btn);
        monthlyTrendBtn = view.findViewById(R.id.monthly_trend_btn);

        dailyTrendBtn.setOnClickListener(v -> {
            renderEmissionsTrend(getDailyEmissionTrendDataSet());
        });

        weeklyTrendBtn.setOnClickListener(v -> {
            renderEmissionsTrend(getWeeklyEmissionTrendDataSet());
        });

        monthlyTrendBtn.setOnClickListener(v -> {
            renderEmissionsTrend(getMonthlyEmissionTrendDataSet());
        });

        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user_key");
            annualEmissions = user.getAnnualEmissions();
            if(annualEmissions == null) {
                Log.e("EmissionsRetrieval", "Emissions is null.");
                user.setAnnualEmissions(new Emissions());
            }
            renderEmissionsBreakdown();
            renderEmissionsTrend(getDailyEmissionTrendDataSet());
        } else {
            Log.e("EmissionsRetrieval", "Failed to get emissions");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (annualEmissions != null) {
            renderEmissionsBreakdown();
            renderEmissionsTrend(getDailyEmissionTrendDataSet());
        } else {
            Log.e("EmissionsRetrieval", "Emissions is null in onResume.");
        }
    }

    private void renderEmissionsBreakdown() {
        PieDataSet pieDataSet = getEmissionsBreakdownDataSet();
        int[] pieChartColors = {
                Color.rgb(170,188,210),
                Color.rgb(0,153,153),
                Color.rgb(57,62,81),
                Color.rgb(217,219,228)};
        pieDataSet.setColors(ColorTemplate.createColors(pieChartColors));

        PieData pieData = new PieData(pieDataSet);
        emissionsBreakdownPieChart.setData(pieData);
        emissionsBreakdownPieChart.getDescription().setEnabled(false);
        emissionsBreakdownPieChart.setEntryLabelTextSize(0f);
        emissionsBreakdownPieChart.animateY(500);

        // Label styling
        Legend l = emissionsBreakdownPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setXEntrySpace(15);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setTextSize(15f);

        emissionsBreakdownPieChart.invalidate();
    }

    @NonNull
    private static PieDataSet getEmissionsBreakdownDataSet() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(annualEmissions.getTransportation(), "Transportation"));
        entries.add(new PieEntry(annualEmissions.getConsumption(), "Consumption"));
        entries.add(new PieEntry(annualEmissions.getFood(), "Food"));
        entries.add(new PieEntry(annualEmissions.getHousing(), "Housing"));

        return new PieDataSet(entries, "");
    }

    private void renderEmissionsTrend(LineDataSet data) {
        data.setAxisDependency(YAxis.AxisDependency.LEFT);
        data.setLineWidth(5);
        data.setCircleRadius(6);
        data.setColor(Color.rgb(0,153,153));

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(data);

        LineData lineData = new LineData(dataSets);
        lineData.setValueTextColor(Color.BLACK);
        lineData.setValueTextSize(12f);
        lineData.setDrawValues(true);
        lineData.setValueTextSize(12f);
        lineData.setValueTextColor(Color.BLACK);

        emissionsTrendLineChart.setData(lineData);
        emissionsTrendLineChart.setDrawGridBackground(false);
        emissionsTrendLineChart.setExtraOffsets(50f, 0f, 50f, 0f);

        // Simplify chart appearance
        emissionsTrendLineChart.setDescription(null);
        emissionsTrendLineChart.getLegend().setEnabled(false);
        emissionsTrendLineChart.setTouchEnabled(false);
        // Disable grid lines and labels
        emissionsTrendLineChart.getAxisLeft().setDrawGridLines(false);
        emissionsTrendLineChart.getAxisLeft().setDrawLabels(false);
        emissionsTrendLineChart.getAxisRight().setDrawGridLines(false);
        emissionsTrendLineChart.getAxisRight().setDrawLabels(false);

        XAxis xAxis = emissionsTrendLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Position the X-axis at the bottom
        xAxis.setGranularity(1f); // Ensure values are displayed at regular intervals
        xAxis.setDrawGridLines(false);
        xAxis.setGranularityEnabled(true);

        emissionsTrendLineChart.animateY(500);
        emissionsTrendLineChart.invalidate();
    }

    private static LineDataSet getDailyEmissionTrendDataSet() {
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

    private static LineDataSet getWeeklyEmissionTrendDataSet() {
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

    private static LineDataSet getMonthlyEmissionTrendDataSet() {
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
        }

        return new LineDataSet(emissions, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eco_gauge, container, false);
    }
}