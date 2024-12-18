package com.example.plantezemobileapplication.view.questionnaire;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.presenter.QuestionnairePresenter;
import com.example.plantezemobileapplication.utils.Answer;

import java.io.Serializable;
import java.util.Map;

public class QuestionnaireActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView categoryTextView;
    private TextView questionTextView;
    private Button previousBtn;
    private Button nextBtn;
    private LinearLayout answerLayout;
    private RelativeLayout.LayoutParams params;
    private QuestionnairePresenter presenter;
    private Spinner spinner;
    private boolean visitedCountrySelector;
    private final String[] countries = {"Afghanistan", "Africa", "Albania", "Algeria", "Andorra", "Angola", "Anguilla", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Asia", "Asia (excl. China and India)", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bonaire Sint Eustatius and Saba", "Bosnia and Herzegovina", "Botswana", "Brazil", "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Curacao", "Cyprus", "Czechia", "Democratic Republic of Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Europe", "Europe (excl. EU-27)", "Europe (excl. EU-28)", "European Union (27)", "European Union (28)", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Greenland", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "High-income countries", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Low-income countries", "Lower-middle-income countries", "Luxembourg", "Macao", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia (country)", "Moldova", "Mongolia", "Montenegro", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "North America", "North America (excl. USA)", "North Korea", "North Macedonia", "Norway", "Oceania", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Sint Maarten (Dutch part)", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South America", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Upper-middle-income countries", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Wallis and Futuna", "World", "Yemen", "Zambia", "Zimbabwe"};
    private String selectedCountry;
    private ProgressBar progressBar;
    private TextView progressBarText;

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

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countries);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nextBtn.setEnabled(true);
                selectedCountry = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Selected item: ", "Nothing selected!");
            }
        });
        spinner.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progress_bar);
        progressBarText = findViewById(R.id.progress_bar_text);

        params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 10);

        presenter = new QuestionnairePresenter(this);

        previousBtn.setEnabled(false);
        presenter.loadQuestion();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        //Reset backgrounds on all answers
        resetAnswers();

        Button clickedBtn = (Button) v;
        //If next btn is clicked
        if (clickedBtn.getId() == R.id.btnNext && presenter.currQuestionIndex < presenter.questions.length - 1) {
            presenter.handleNextQuestion();
            answerLayout.removeAllViews();
            presenter.loadQuestion();
            nextBtn.setEnabled(false);
            previousBtn.setEnabled(true);
            adjustProgressBar();
        }
        // Redirect to country selector if last question
        else if (clickedBtn.getId() == R.id.btnNext
                && presenter.currQuestionIndex >= presenter.questions.length - 1
                && !visitedCountrySelector) {
            presenter.currQuestionIndex++;
            answerLayout.setVisibility(View.GONE);
            nextBtn.setText("Submit");
            loadCountrySelector();
            adjustProgressBar();
        }
        // If user selects a country
        else if (clickedBtn.getId() == R.id.btnNext && visitedCountrySelector) {
            submitQuiz();
        }
        //If previous btn is clicked
        else if (clickedBtn.getId() == R.id.btnPrevious && presenter.currQuestionIndex > 0) {
            if (visitedCountrySelector) {
                visitedCountrySelector = false;
                spinner.setVisibility(View.GONE);
                answerLayout.setVisibility(View.VISIBLE);
                nextBtn.setEnabled(false);
                nextBtn.setText("Next");
            }
            presenter.handlePreviousQuestion();
            answerLayout.removeAllViews();
            presenter.loadQuestion();
            adjustProgressBar();
        }
        //If any answer btns are clicked
        else {
            if (clickedBtn.getId() != R.id.btnPrevious) {
                updateButtonStyle(clickedBtn, R.drawable.rectangular_button);
            }
            String currAnswer = clickedBtn.getText().toString();
            presenter.handleAnswer(currAnswer);
            nextBtn.setEnabled(true);
        }
    }

    @SuppressLint("ResourceType")
    private void resetAnswers() {
        Button answerBtn;
        for (int i = 0; i < answerLayout.getChildCount(); i++) {
            answerBtn = (Button) answerLayout.getChildAt(i);
            answerBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.border_button));
            answerBtn.setTextColor(ContextCompat.getColorStateList(this, R.drawable.border_button));
        }
    }

    public void setCategory(String category) {
        categoryTextView.setText(category);
    }

    public void setQuestion(String question) {
        questionTextView.setText(question);
    }

    public void addAnswersToLayout(Answer[] answers) {
        for (Answer answer : answers) {
            Button answerBtn = new Button(this);
            answerBtn.setId(View.generateViewId());
            answerBtn.setText(answer.getAnswerText());
            answerBtn.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
            answerBtn.setLayoutParams(params);
            updateButtonStyle(answerBtn, R.drawable.border_button);
            answerLayout.addView(answerBtn);
            answerBtn.setOnClickListener(this);
        }
    }

    private void loadCountrySelector() {
        answerLayout.removeAllViews();
        visitedCountrySelector = true;
        spinner.setVisibility(View.VISIBLE);
        categoryTextView.setText("Before you start your sustainable journey,");
        questionTextView.setText("Select your country");
    }

    @SuppressLint("ResourceType")
    private void updateButtonStyle(Button button, int drawableId) {
        button.setBackground(ContextCompat.getDrawable(this, drawableId));
        button.setTextColor(ContextCompat.getColorStateList(this, drawableId));
    }

    private void submitQuiz() {
        Map<String, Double> categoryTotals;
        Map<String, Object> additionalUserInfo;
        Intent switchActivityIntent = new Intent(this, QuestionnaireResultActivity.class);
        categoryTotals = presenter.calculateQuizResult();
        additionalUserInfo = presenter.setAdditionalUserInfo();
        switchActivityIntent.putExtra("totalEmissions", (Serializable) categoryTotals);
        switchActivityIntent.putExtra("userCountry", selectedCountry);
        switchActivityIntent.putExtra("selectedAnswerIndexes", (Serializable) additionalUserInfo);
        startActivity(switchActivityIntent);
    }

    private void adjustProgressBar() {
        int progress = presenter.currQuestionIndex;
        if (progress > progressBar.getMax()) {
            progress = 0;
        }
        float progressPercentage = (float) progress / progressBar.getMax() * 100;
        progressBarText.setText(String.format("%.1f", progressPercentage) + "% Completed");
        progressBar.setProgress(progress);
    }

}