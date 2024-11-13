package com.example.plantezemobileapplication.questionnaire;

public class SpecialAnswer extends Answer {
    private int nextQuestionIndex;
    public SpecialAnswer() {}

    public SpecialAnswer(String answerText, int weight, int nextQuestionIndex) {
        super(answerText, weight);
        this.nextQuestionIndex = nextQuestionIndex;
    }

    public int getNextQuestionIndex() {
        return nextQuestionIndex;
    }
}
