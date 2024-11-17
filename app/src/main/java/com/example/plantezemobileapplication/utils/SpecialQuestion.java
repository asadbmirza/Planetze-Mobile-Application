package com.example.plantezemobileapplication.utils;

public class SpecialQuestion extends Question {
    private int previousQuestionIndex;

    public SpecialQuestion() {}

    public SpecialQuestion(String title, Answer[] answers, String category, int previousQuestionIndex) {
        super(title, answers, category);
        this.previousQuestionIndex = previousQuestionIndex;
    }

    public int getPreviousQuestionIndex() {
        return previousQuestionIndex;
    }

    public void setPreviousQuestionIndex(int index) {
        this.previousQuestionIndex = index;
    }
}
