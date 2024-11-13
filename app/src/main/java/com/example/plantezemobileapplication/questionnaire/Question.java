package com.example.plantezemobileapplication.questionnaire;

public class Question {
    private String title;
    private Answer[] answers;
    private String category;
    private int selectedAnswer = -1; // index of the selected answer in answers

    public Question() {}

    public Question(String title, Answer[] answers, String category) {
        this.title = title;
        this.answers = answers;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
