package com.example.plantezemobileapplication.view.questionnaire;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.QuestionnaireModel;
import com.example.plantezemobileapplication.presenter.QuestionnaireResultPresenter;
import com.example.plantezemobileapplication.utils.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class QuestionnaireResultActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalEmissionView;
    TextView countryEmissionView;
    Button completeQuizButton;
    LinearLayout layout;
    Map<String, Double> totalEmissions;
    String selectedCountry;
    QuestionnaireResultPresenter presenter;
    QuestionnaireModel model;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_result);

        layout = findViewById(R.id.category_layout);
        totalEmissionView = findViewById(R.id.user_emissions);
        countryEmissionView = findViewById(R.id.country_emissions);
        completeQuizButton = findViewById(R.id.btnCompleteQuiz);

        completeQuizButton.setOnClickListener(this);

        Intent intent = getIntent();

        model = new QuestionnaireModel();
        presenter = new QuestionnaireResultPresenter(model, this);

        totalEmissions = (HashMap<String, Double>) intent.getSerializableExtra("totalEmissions");
        selectedCountry = intent.getStringExtra("userCountry");

        presenter.setTotalEmissionDisplay(totalEmissions, selectedCountry);

        displayCategories();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {
        Button clickedBtn = (Button) v;
        if (clickedBtn.getId() == R.id.btnCompleteQuiz) {
            presenter.uploadUserResults(totalEmissions, selectedCountry);
        }
    }

    public void setUserTotalText(String total) {
        totalEmissionView.setText(total);
    }

    public void setCountryEmissions(String total) {
        countryEmissionView.setText(total);
    }

    public void displayCategories() {
        for (Map.Entry<String, Double> entry : totalEmissions.entrySet()) {
            if (!entry.getKey().equals("Total")) {
                TextView text = new TextView(this);
                text.setId(TextView.generateViewId());
                text.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
                text.setTextSize(25);
                text.setText(entry.getKey().toUpperCase() + ": " + String.format("%.5g%n", entry.getValue() * 0.001) + "tonne(s)/yr");
                layout.addView(text);
            }
        }
    }
}