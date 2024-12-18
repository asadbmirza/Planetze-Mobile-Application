package com.example.plantezemobileapplication.view.ecogauge;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.User;
import com.example.plantezemobileapplication.presenter.EcoGaugePresenter;
import com.example.plantezemobileapplication.utils.JsonParser;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class EcoGaugeFragment extends Fragment implements EcoGaugeView{

    PieChart emissionsBreakdownPieChart;
    LineChart emissionsTrendLineChart;
    BarChart emissionsComparisonBarChart;

    TextView emissionsText, timePeriodText, comparisonStatement;
    Button todayBtn, thisWeekBtn, thisMonthBtn, dailyTrendBtn, weeklyTrendBtn, monthlyTrendBtn;
    String [] countries;
    EcoGaugePresenter presenter;
    AutoCompleteTextView dropdown;
    String regionToCompare = "World";

    public EcoGaugeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emissionsBreakdownPieChart = view.findViewById(R.id.emission_breakdown_chart);
        emissionsTrendLineChart = view.findViewById(R.id.emissions_trend_chart);
        emissionsComparisonBarChart = view.findViewById(R.id.emissions_comparison_chart);
        todayBtn = view.findViewById(R.id.today_btn);
        thisWeekBtn = view.findViewById(R.id.this_week_btn);
        thisMonthBtn = view.findViewById(R.id.this_month_btn);
        dailyTrendBtn = view.findViewById(R.id.daily_trend_btn);
        weeklyTrendBtn = view.findViewById(R.id.weekly_trend_btn);
        monthlyTrendBtn = view.findViewById(R.id.monthly_trend_btn);
        emissionsText = view.findViewById(R.id.emissions_text);
        timePeriodText = view.findViewById(R.id.time_period_text);
        comparisonStatement = view.findViewById(R.id.comparison_statemeent);
        dropdown = view.findViewById(R.id.autoCompleteTextView);
        countries = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, countries);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemClickListener((parent, view1, position, id) -> {
            regionToCompare = parent.getItemAtPosition(position).toString();
            renderEmissionsComparisons();
        });

        todayBtn.setOnClickListener(v -> renderEmissionsOverview(presenter.getTodayEmissions(), "today", presenter.getTwoDayRelativeDifference()));
        thisWeekBtn.setOnClickListener(v -> renderEmissionsOverview(presenter.getThisWeekEmissions(), "this week", presenter.getTwoWeekRelativeDifference()));
        thisMonthBtn.setOnClickListener(v -> renderEmissionsOverview(presenter.getThisMonthEmissions(), "this month", presenter.getTwoMonthRelativeDifference()));

        dailyTrendBtn.setOnClickListener(v -> renderEmissionsTrend(presenter.getDailyEmissionTrendDataSet()));
        weeklyTrendBtn.setOnClickListener(v -> renderEmissionsTrend(presenter.getWeeklyEmissionTrendDataSet()));
        monthlyTrendBtn.setOnClickListener(v -> renderEmissionsTrend(presenter.getMonthlyEmissionTrendDataSet()));

        if (getArguments() != null) {
            User user = (User) getArguments().getSerializable("user_key");
            presenter = new EcoGaugePresenter(user, emissionsTrendLineChart, this);
            renderEmissionsOverview(presenter.getTodayEmissions(), "today", presenter.getTwoDayRelativeDifference());
            renderEmissionsBreakdown();
            renderEmissionsTrend(presenter.getDailyEmissionTrendDataSet());
            renderEmissionsComparisons();
        } else {
            Log.e("EmissionsRetrieval", "Failed to get emissions");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        renderEmissionsOverview(presenter.getTodayEmissions(), "today", presenter.getTwoDayRelativeDifference());
        renderEmissionsBreakdown();
        renderEmissionsTrend(presenter.getDailyEmissionTrendDataSet());
        renderEmissionsComparisons();
    }

    @Override
    public void renderEmissionsOverview(float emissionsNumber, String timePeriod, float relativeDifference) {
        if (emissionsNumber == 0) {
            String noLogs = "No logs!";
            emissionsText.setText(noLogs);
        } else {
            emissionsText.setText(String.format("\uD83C\uDF0D %s kg CO₂e", emissionsNumber));
        }

        if (relativeDifference == 0) {
            String nothingToCompare = "No difference. Keep going—consistency leads to progress!";
            comparisonStatement.setText(nothingToCompare);
            comparisonStatement.setTextColor(Color.DKGRAY);
        } else {
            if (relativeDifference < 0) {
                comparisonStatement.setText(String.format("You're emitting %s%% less than yesterday. Keep it up!", Math.abs(relativeDifference)));
                comparisonStatement.setTextColor(Color.rgb(103, 154, 2));
            } else {
                comparisonStatement.setText(String.format("You're emitting %s%% more than yesterday. Every step counts, you can get back on track!", relativeDifference));
                comparisonStatement.setTextColor(Color.rgb(208, 21, 21));
            }
        }

        timePeriodText.setText(String.format("(%s)", timePeriod));
    }

    @Override
    public void renderEmissionsBreakdown() {
        PieDataSet pieDataSet = presenter.getEmissionsBreakdownDataSet();
        int[] pieChartColors = {
                Color.rgb(170,188,210),
                Color.rgb(0,153,153),
                Color.rgb(57,162,81),
                Color.rgb(217,219,228)};
        pieDataSet.setColors(ColorTemplate.createColors(pieChartColors));

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(14f);


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

    @Override
    public void renderEmissionsTrend(LineDataSet data) {
        data.setAxisDependency(YAxis.AxisDependency.LEFT);
        data.setLineWidth(5);
        data.setCircleRadius(6);
        data.setColor(Color.rgb(0,153,153));

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(data);

        LineData lineData = new LineData(dataSets);
        lineData.setValueTextColor(Color.BLACK);
        lineData.setValueTextSize(12f);

        Legend legend = emissionsTrendLineChart.getLegend();
        legend.setTextSize(14f);

        emissionsTrendLineChart.setData(lineData);
        emissionsTrendLineChart.setDrawGridBackground(false);
        emissionsTrendLineChart.setExtraOffsets(30f, 0f, 30f, 15f);

        // Simplify chart appearance
        emissionsTrendLineChart.setDescription(null);
        emissionsTrendLineChart.setTouchEnabled(false);
        // Disable grid lines and labels
        emissionsTrendLineChart.getAxisLeft().setDrawGridLines(false);
        emissionsTrendLineChart.getAxisLeft().setDrawLabels(false);
        emissionsTrendLineChart.getAxisRight().setDrawGridLines(false);
        emissionsTrendLineChart.getAxisRight().setDrawLabels(false);

        XAxis xAxis = emissionsTrendLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        emissionsTrendLineChart.animateY(500);
        emissionsTrendLineChart.invalidate();
    }

    @Override
    public void renderEmissionsComparisons() {
        JsonParser jsonParser = new JsonParser(getContext());
        BarDataSet dataSet = presenter.getComparedRegionAnnualEmissions(jsonParser, regionToCompare);
        dataSet.setColor(Color.rgb(0,153,153));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14f);

        BarData barData = new BarData(dataSet);
        emissionsComparisonBarChart.setData(barData);

        final String[] labels = {"You", regionToCompare};
        XAxis xAxis = emissionsComparisonBarChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return labels[(int) value];
            }
        });
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(14f);

        Legend legend = emissionsComparisonBarChart.getLegend();
        legend.setTextSize(14f);

        emissionsComparisonBarChart.setExtraOffsets(0f, 20f, 0f, 10f);
        emissionsComparisonBarChart.getAxisLeft().setAxisMinimum(0f);
        emissionsComparisonBarChart.getAxisRight().setEnabled(false);
        emissionsComparisonBarChart.getAxisLeft().setDrawGridLines(false);

        emissionsComparisonBarChart.setTouchEnabled(false);
        emissionsComparisonBarChart.getDescription().setEnabled(false);
        emissionsComparisonBarChart.animateY(500);
        emissionsComparisonBarChart.invalidate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eco_gauge, container, false);
    }
}