package com.example.plantezemobileapplication.questionnaire;

public class QuestionnaireAnswer {
    private String answerText;
    private int weight; // The amount of co2 emissions for a specific answer

    public QuestionnaireAnswer() {}

    public QuestionnaireAnswer(String answerText, int weight) {
        this.answerText = answerText;
        this.weight = weight;
    }
}
