package com.example.plantezemobileapplication.utils;

public class ActivityLog {
    private int id;
    private String questionTitle;
    private String category;
    private int enteredValue;
    private Answer selectedAnswer;

    public ActivityLog() {
    }

    public ActivityLog(int id, String questionTitle, String category, int enteredValue, Answer selectedAnswer) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.category = category;
        this.enteredValue = enteredValue;
        this.selectedAnswer = selectedAnswer;
    }

    @Override
    public String toString() {
        return questionTitle + " " + category + " " + enteredValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getEnteredValue() {
        return enteredValue;
    }

    public void setEnteredValue(int enteredValue) {
        this.enteredValue = enteredValue;
    }

    public Answer getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(Answer selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
