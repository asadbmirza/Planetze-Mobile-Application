package com.example.plantezemobileapplication.questionnaire;

import java.util.ArrayList;

public class Question {
    private String title;
    private ArrayList<QuestionnaireAnswer> answers;
    private Question nextQuestion;
    private String category;

    public Question() {}

    public Question(String title, ArrayList<QuestionnaireAnswer> answers, String category) {
        this.title = title;
        this.answers = answers;
        this.category = category;
    }

    public void setNextQuestion(Question nextQuestion) {
        this.nextQuestion = nextQuestion;
    }
}
