package com.example.plantezemobileapplication.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantezemobileapplication.R;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class EcoGaugeFragment extends Fragment {

    PieChart emissionsBreakdownPieChart;
    LineChart emissionsTrendLineChart;

    public EcoGaugeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emissionsBreakdownPieChart = view.findViewById(R.id.emission_breakdown_chart);
        emissionsTrendLineChart = view.findViewById(R.id.emissions_trend_chart);

        renderEmissionsBreakdown();
        renderEmissionsTrend();
    }

    private void renderEmissionsBreakdown() {
        PieDataSet pieDataSet = getEmissionsBreakdownDataSet();
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

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

        entries.add(new PieEntry(80f, "Transportation"));
        entries.add(new PieEntry(130f, "Consumption"));
        entries.add(new PieEntry(75f, "Food"));
        entries.add(new PieEntry(60f, "Housing"));

        return new PieDataSet(entries, "");
    }

    private void renderEmissionsTrend() {
        LineDataSet setComp1 = getEmissionsTrendDataSet();
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setLineWidth(5);
        setComp1.setCircleRadius(6);
        setComp1.setColor(Color.rgb(0,153,153));

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);

        LineData lineData = new LineData(dataSets);
        lineData.setValueTextColor(Color.BLACK);
        lineData.setValueTextSize(12f);
        lineData.setDrawValues(true);
        lineData.setValueTextSize(12f);
        lineData.setValueTextColor(Color.BLACK);

        emissionsTrendLineChart.setData(lineData);
        emissionsTrendLineChart.setDrawGridBackground(false);

        // Simplify chart appearance
        emissionsTrendLineChart.setDescription(null);
        emissionsTrendLineChart.getLegend().setEnabled(false);
        emissionsTrendLineChart.setTouchEnabled(false);
        // Disable grid lines and labels
        emissionsTrendLineChart.getAxisLeft().setDrawGridLines(false);
        emissionsTrendLineChart.getAxisLeft().setDrawLabels(false);
        emissionsTrendLineChart.getAxisRight().setDrawGridLines(false);
        emissionsTrendLineChart.getAxisRight().setDrawLabels(false);
        emissionsTrendLineChart.getXAxis().setDrawGridLines(false);

        emissionsTrendLineChart.getXAxis().setGranularity(1f); // Force whole number increments
        emissionsTrendLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        emissionsTrendLineChart.animateY(500);
        emissionsTrendLineChart.invalidate();
    }

    @NonNull
    private static LineDataSet getEmissionsTrendDataSet() {
        List<Entry> emissions = new ArrayList<>();

        emissions.add(new Entry(0f, 100000f));
        emissions.add(new Entry(1f, 140000f));
        emissions.add(new Entry(2f, 160000f));
        emissions.add(new Entry(3f, 180000f));

        return new LineDataSet(emissions, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eco_gauge, container, false);
    }
}