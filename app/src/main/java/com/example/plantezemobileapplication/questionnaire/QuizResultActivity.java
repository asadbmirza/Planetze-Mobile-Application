package com.example.plantezemobileapplication.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.R;

import java.util.HashMap;
import java.util.Map;

public class QuizResultActivity extends AppCompatActivity {
    TextView totalEmissionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_result);

        totalEmissionView = findViewById(R.id.textView);

        Intent intent = getIntent();
        Map<String, Double> totalEmissions = (HashMap<String, Double>) intent.getSerializableExtra("totalEmissions");

        totalEmissionView.setText(String.valueOf(totalEmissions.get("Food")));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}