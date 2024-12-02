package com.example.plantezemobileapplication.utils;

import androidx.annotation.NonNull;

public class Answer {
    private String answerText;
    private double weight; // The amount of co2 emissions for a specific answer

    public Answer() {}

    public Answer(String answerText, double weight) {
        this.answerText = answerText;
        this.weight = weight;
    }

    public String getAnswerText() {
        return answerText;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @NonNull
    @Override
    public String toString() {
        return answerText;
    }

}
