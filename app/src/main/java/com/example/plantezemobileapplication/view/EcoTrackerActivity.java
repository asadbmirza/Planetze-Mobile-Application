package com.example.plantezemobileapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.plantezemobileapplication.R;



public class EcoTrackerActivity extends AppCompatActivity {

    EcoTrackerHabitFragment habitFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.eco_tracker_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.eco_tracker_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Button habitsBtn = findViewById(R.id.view_habit_page_button);
        habitsBtn.setOnClickListener(v -> {
            habitFragment = new EcoTrackerHabitFragment();
            navigateToFragment(habitFragment, R.id.eco_tracker_habit);
        });
    }

    private void navigateToFragment(Fragment fragment, int viewId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(viewId, fragment);
        transaction.addToBackStack(null); // this adds the transaction to the back stack so the user can navigate back

        transaction.commit();
    }

}

