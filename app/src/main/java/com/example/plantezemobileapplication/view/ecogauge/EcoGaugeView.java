package com.example.plantezemobileapplication.view.ecogauge;

import com.github.mikephil.charting.data.LineDataSet;

public interface EcoGaugeView {
    void renderEmissionsOverview(float emissionsNumber, String timePeriod, float relativeDifference);
    void renderEmissionsBreakdown();
    void renderEmissionsTrend(LineDataSet data);
    void renderEmissionsComparisons();
}
