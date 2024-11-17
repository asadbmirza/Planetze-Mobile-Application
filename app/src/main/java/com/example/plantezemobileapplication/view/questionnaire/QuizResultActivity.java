package com.example.plantezemobileapplication.view.questionnaire;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.utils.JsonParser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class QuizResultActivity extends AppCompatActivity {
    TextView totalEmissionView;
    TextView countryEmissionView;
    LinearLayout layout;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_result);

        layout = findViewById(R.id.category_layout);
        totalEmissionView = findViewById(R.id.user_emissions);
        countryEmissionView = findViewById(R.id.country_emissions);

        Intent intent = getIntent();
        Map<String, Double> totalEmissions = (HashMap<String, Double>) intent.getSerializableExtra("totalEmissions");
        String selectedCountry = intent.getStringExtra("userCountry");
        JsonParser jsonParser = new JsonParser(this);
        double averageEmissions = jsonParser.getEmissionByCountry("countryEmissions.json", selectedCountry);

        totalEmissionView.setText(String.format("%.5g%n", totalEmissions.get("Total") * 0.001));
        countryEmissionView.setText(String.valueOf(averageEmissions));

        for (Map.Entry<String, Double> entry : totalEmissions.entrySet()) {
            if (!entry.getKey().equals("Total")) {
                TextView text = new TextView(this);
                text.setId(TextView.generateViewId());
                text.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
                text.setTextSize(25);
                text.setText(entry.getKey() + ": " + entry.getValue());
                text.setTextColor(R.color.black);
                layout.addView(text);
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}