package com.example.plantezemobileapplication.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Question implements Parcelable {
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

    protected Question(Parcel in) {
        title = in.readString();
        category = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public Answer getSelectedAnswerObject() {
        if (selectedAnswer == -1 || selectedAnswer >= answers.length) {
            return null;
        }
        return answers[selectedAnswer];
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeArray(answers);
        dest.writeString(category);
    }
}
