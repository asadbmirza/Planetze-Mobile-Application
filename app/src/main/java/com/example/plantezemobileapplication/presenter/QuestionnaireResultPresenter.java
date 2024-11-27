package com.example.plantezemobileapplication.presenter;


import com.example.plantezemobileapplication.model.QuestionnaireModel;
import com.example.plantezemobileapplication.utils.JsonParser;
import com.example.plantezemobileapplication.view.questionnaire.QuestionnaireResultActivity;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class QuestionnaireResultPresenter {
    private QuestionnaireModel model;
    private QuestionnaireResultActivity view;


    public QuestionnaireResultPresenter() {}


    public QuestionnaireResultPresenter(QuestionnaireModel model, QuestionnaireResultActivity view) {
        this.model = model;
        this.view = view;
    }


    public List<Map.Entry<String, Double>> sortCategories(Map<String, Double> categories) {
        List<Map.Entry<String, Double>> sortedCategories = new ArrayList<Map.Entry<String, Double>>(categories.entrySet());
        Collections.sort(sortedCategories, Collections.reverseOrder(new Comparator<Map.Entry<String,Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        }));
        return sortedCategories;
    }


    private double calculatePercentage(double emissions, double average) {
        double userEmissions = emissions * 0.001;
        return ((userEmissions - average) / average) * 100;
    }


    public void displayCountryComparison(Map<String, Double> totalEmissions, String country) {
        JsonParser jsonParser = new JsonParser(view);
        double countryEmissions = jsonParser.getEmissionByCountry("countryEmissions.json", country);
        double percentage = calculatePercentage(totalEmissions.get("Total"), countryEmissions);
        if (percentage >= 0) {
            view.setCountryComparison(String.format("%.0f", percentage) + "%");
            view.setCountryComparisonSubtext("ABOVE the national average for " + country);
        }
        else {
            percentage = Math.abs(percentage);
            view.setCountryComparison(String.format("%.0f", percentage) + "%");
            view.setCountryComparisonSubtext("BELOW the national average for " + country);
        }
    }


    public void displayGlobalTargetComparison(Map<String, Double> totalEmissions) {
        double userEmissions = totalEmissions.get("Total") * 0.001;
        double percentage = calculatePercentage(totalEmissions.get("Total"), 2);;
        if (percentage >= 0) {
            view.setGlobalTargetComparison(String.format("%.0f", percentage) + "%");
            view.setGlobalTargetComparisonSubtext("ABOVE the global target (under 2 tonnes/yr)");
        }
        else {
            percentage = Math.abs(percentage);
            view.setGlobalTargetComparison(String.format("%.0f", percentage) + "%");
            view.setGlobalTargetComparisonSubtext("BELOW the global target (under 2 tonnes/yr)");
        }
    }


    public void displayTotalEmissions(Map<String, Double> totalEmissions) {
        view.setUserTotalText(String.format("%.3f", totalEmissions.get("Total") * 0.001));
    }


    public void uploadUserResults(Map<String, Double> totalEmissions, String country, Map<String, Object> additionalUserInfo) {
        model.saveQuestionnaireInfo(totalEmissions, country, additionalUserInfo);
    }
}