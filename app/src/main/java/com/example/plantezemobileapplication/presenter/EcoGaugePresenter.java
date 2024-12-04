package com.example.plantezemobileapplication.presenter;

import androidx.annotation.NonNull;

import com.example.plantezemobileapplication.Emissions;
import com.example.plantezemobileapplication.User;
import com.example.plantezemobileapplication.utils.JsonParser;
import com.example.plantezemobileapplication.view.ecogauge.EcoGaugeView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public float getTodayEmissions() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String formattedTodayDate = sdf.format(calendar.getTime());

        HashMap<String, Emissions> dailyEmissions = user.getDailyEmissions();

        if (dailyEmissions == null) {
            return 0;
        }

        Emissions todayEmissions = dailyEmissions.get(formattedTodayDate);

        if (todayEmissions == null) {
            return 0;
        }
        return todayEmissions.getTotal();
    }

    public float getTwoDayRelativeDifference() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        HashMap<String, Emissions> dailyEmissions = user.getDailyEmissions();

        String todayDate = sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        String yesterdayDate = sdf.format(calendar.getTime());

        if (dailyEmissions == null) {
            return 0;
        }

        Emissions todayEmissions = dailyEmissions.get(todayDate);
        Emissions yesterdayEmissions = dailyEmissions.get(yesterdayDate);

        if (todayEmissions == null || yesterdayEmissions == null) {
            return 0;
        }
        return (float) Math.round(((todayEmissions.getTotal() - yesterdayEmissions.getTotal()) / yesterdayEmissions.getTotal()) * 1000) / 10;
    }

    public float getThisWeekEmissions() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-w", Locale.CANADA);
        HashMap<String, Emissions> weeklyEmissions = user.getWeeklyEmissions();

        String thisYearWeek = dateFormat.format(calendar.getTime());

        if (weeklyEmissions == null) {
            return 0;
        }

        Emissions thisWeekEmissions = weeklyEmissions.get(thisYearWeek);

        if (thisWeekEmissions == null) {
            return 0;
        }
        return thisWeekEmissions.getTotal();
    }

    public float getTwoWeekRelativeDifference() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-w", Locale.CANADA);
        HashMap<String, Emissions> weeklyEmissions = user.getWeeklyEmissions();

        String thisYearWeek = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        String lastYearWeek = dateFormat.format(calendar.getTime());

        if (weeklyEmissions == null) {
            return 0;
        }

        Emissions thisWeekEmission = weeklyEmissions.get(thisYearWeek);
        Emissions lastWeekEmission = weeklyEmissions.get(lastYearWeek);

        if (thisWeekEmission == null || lastWeekEmission == null) {
            return 0;
        }
        return (float) Math.round(((thisWeekEmission.getTotal() - lastWeekEmission.getTotal()) / lastWeekEmission.getTotal()) * 1000) / 10;
    }

    public float getThisMonthEmissions() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.CANADA);
        String thisYearMonth = dateFormat.format(calendar.getTime());

        HashMap<String, Emissions> monthlyEmissions = user.getMonthlyEmissions();

        if (monthlyEmissions == null) {
            return 0;
        }

        Emissions thisMonthEmissions = monthlyEmissions.get(thisYearMonth);

        if (thisMonthEmissions == null) {
            return 0;
        }

        return thisMonthEmissions.getTotal();
    }

    public float getTwoMonthRelativeDifference() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.CANADA);
        HashMap<String, Emissions> weeklyEmissions = user.getWeeklyEmissions();

        String thisYearMonth = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        String lastYearMonth = dateFormat.format(calendar.getTime());

        if (weeklyEmissions == null) {
            return 0;
        }

        Emissions thisMonthEmission = weeklyEmissions.get(thisYearMonth);
        Emissions lastMonthEmission = weeklyEmissions.get(lastYearMonth);

        if (thisMonthEmission == null || lastMonthEmission == null) {
            return 0;
        }
        return (float) Math.round(((thisMonthEmission.getTotal() - lastMonthEmission.getTotal()) / lastMonthEmission.getTotal()) * 1000) / 10;
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

        return new PieDataSet(entries, "Emissions (kg CO₂e)");
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
                        return "";
                    }
                }
            });
        }

        return new LineDataSet(emissions, "Emissions (kg CO₂e)");
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

        return new LineDataSet(emissions, "Emissions (kg CO₂e)");
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

        return new LineDataSet(emissions, "Emissions (kg CO₂e)");
    }

    public BarDataSet getComparedRegionAnnualEmissions(JsonParser parser, String region) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        double regionEmissions = parser.getEmissionByCountry("countryEmissions.json", region) * 1000;

        entries.add(new BarEntry(0f, user.getAnnualEmissions().getTotal()));
        entries.add(new BarEntry(1f, (float) regionEmissions));

        return new BarDataSet(entries, "Emissions (kg CO₂e)");
    }
}
