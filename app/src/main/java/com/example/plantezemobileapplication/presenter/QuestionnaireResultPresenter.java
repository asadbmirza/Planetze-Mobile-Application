package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.QuestionnaireModel;
import com.example.plantezemobileapplication.utils.JsonParser;
import com.example.plantezemobileapplication.view.questionnaire.QuestionnaireResultActivity;

import java.util.Map;

public class QuestionnaireResultPresenter {
    private QuestionnaireModel model;
    private QuestionnaireResultActivity view;

    public QuestionnaireResultPresenter() {}

    public QuestionnaireResultPresenter(QuestionnaireModel model, QuestionnaireResultActivity view) {
        this.model = model;
        this.view = view;
    }

    public void setTotalEmissionDisplay(Map<String, Double> totalEmissions, String country) {
        JsonParser jsonParser = new JsonParser(view);
        double countryEmissions = jsonParser.getEmissionByCountry("countryEmissions.json", country);
        view.setUserTotalText(String.format("%.3g%n", totalEmissions.get("Total") * 0.001));
        view.setCountryEmissions(String.format("%.3g%n", countryEmissions));
    }

    public void uploadUserResults(Map<String, Double> totalEmissions, String country) {
        model.saveQuestionnaireInfo(totalEmissions, country);
    }
}
