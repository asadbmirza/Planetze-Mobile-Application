package com.example.plantezemobileapplication.questionnaire;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.R;

import java.io.Serializable;
import java.util.Map;

public class QuestionnaireActivity extends AppCompatActivity implements View.OnClickListener {
    TextView categoryTextView;
    TextView questionTextView;
    Button previousBtn;
    Button nextBtn;
    LinearLayout answerLayout;
    RelativeLayout.LayoutParams params;
    QuestionSet questionSet = new QuestionSet();
    int currQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questionnaire);
        answerLayout = findViewById(R.id.answers_layout);
        categoryTextView = findViewById(R.id.category);
        questionTextView = findViewById(R.id.question);

        previousBtn = findViewById(R.id.btnPrevious);
        nextBtn = findViewById(R.id.btnNext);

        nextBtn.setOnClickListener(this);
        previousBtn.setOnClickListener(this);

        params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 10);

        loadQuestion();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        Button answerBtn;

        //Reset backgrounds on all answers
        for (int i = 0; i < answerLayout.getChildCount(); i++) {
            answerBtn = (Button) answerLayout.getChildAt(i);
            answerBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.border_button));
            answerBtn.setTextColor(ContextCompat.getColorStateList(this, R.drawable.border_button));
        }

        Button clickedBtn = (Button) v;
        //If next btn is clicked
        if (clickedBtn.getId() == R.id.btnNext && currQuestionIndex < questionSet.questions.length - 1) {
            handleNextQuestion();
        }
        //If last question is answered
        else if (clickedBtn.getId() == R.id.btnNext && currQuestionIndex == questionSet.questions.length - 1) {
            submitQuiz();
        }
        //If previous btn is clicked
        else if (clickedBtn.getId() == R.id.btnPrevious && currQuestionIndex > 0) {
            handlePreviousQuestion();
        }
        //If any answer btns are clicked
        else {
            handleAnswer(clickedBtn);
        }
    }

    @SuppressLint("ResourceType")
    private void loadQuestion() {
        Question[] currQuestions = questionSet.questions;
        categoryTextView.setText(currQuestions[currQuestionIndex].getCategory());
        questionTextView.setText(currQuestions[currQuestionIndex].getTitle());
        for (int i = 0; i < currQuestions[currQuestionIndex].getAnswers().length; i++) {
            Button answerBtn = new Button(this);
            answerBtn.setId(View.generateViewId());
            answerBtn.setText(currQuestions[currQuestionIndex].getAnswers()[i].getAnswerText());
            answerBtn.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
            answerBtn.setLayoutParams(params);
            answerBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.border_button));
            answerBtn.setTextColor(ContextCompat.getColorStateList(this, R.drawable.border_button));
            answerLayout.addView(answerBtn);
            answerBtn.setOnClickListener(this);
        }
    }

    private int findAnswerIndex(String answerText, Question question) {
        Answer[] answers = question.getAnswers();
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].getAnswerText().equals(answerText)) {
                return i;
            }
        }
        return -1;
    }

    @SuppressLint("SetTextI18n")
    private void handleNextQuestion() {
        Question[] currQuestions = questionSet.questions;
        int selectedAnswerIndex = currQuestions[currQuestionIndex].getSelectedAnswer();

        if (currQuestions[currQuestionIndex].getAnswers()[selectedAnswerIndex] instanceof SpecialAnswer) {
            int nextQuestionIndex = ((SpecialAnswer) currQuestions[currQuestionIndex].getAnswers()[selectedAnswerIndex]).getNextQuestionIndex();
            ((SpecialQuestion) currQuestions[nextQuestionIndex]).setPreviousQuestionIndex(currQuestionIndex);
            currQuestionIndex = nextQuestionIndex;
        }
        else {
            currQuestionIndex++;
        }
        if (currQuestionIndex == questionSet.questions.length - 1) {
            nextBtn.setText("Submit");
        }
        answerLayout.removeAllViews();
        loadQuestion();
        nextBtn.setEnabled(false);
    }

    private void handlePreviousQuestion() {
        Question[] currQuestions = questionSet.questions;

        if (currQuestions[currQuestionIndex] instanceof SpecialQuestion) {
            currQuestionIndex = ((SpecialQuestion) currQuestions[currQuestionIndex]).getPreviousQuestionIndex();
        }
        else {
            currQuestionIndex--;
        }
        answerLayout.removeAllViews();
        loadQuestion();
    }

    @SuppressLint("ResourceType")
    private void handleAnswer(Button clickedBtn) {
        Question[] currQuestions = questionSet.questions;
        String currAnswer = clickedBtn.getText().toString();
        int currAnswerIndex = findAnswerIndex(currAnswer, currQuestions[currQuestionIndex]);

        currQuestions[currQuestionIndex].setSelectedAnswer(currAnswerIndex);
        clickedBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.rectangular_button));
        clickedBtn.setTextColor(ContextCompat.getColorStateList(this, R.drawable.rectangular_button));
        nextBtn.setEnabled(true);
    }

    private void submitQuiz() {
        Map<String, Double> categoryTotals;
        Intent switchActivityIntent = new Intent(this, QuizResultActivity.class);

        categoryTotals = questionSet.calculateQuizResult();
        switchActivityIntent.putExtra("totalEmissions", (Serializable) categoryTotals);
        startActivity(switchActivityIntent);
    }

}