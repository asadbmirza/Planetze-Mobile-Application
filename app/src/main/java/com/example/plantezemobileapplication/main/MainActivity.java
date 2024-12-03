package com.example.plantezemobileapplication.main;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.view.ecoTracker.EcoTrackerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply system bars inset adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Check if the fragment is already added to avoid replacing it again on configuration changes (like screen rotations)
        if (savedInstanceState == null) {
            loadEcoTrackerFragment();
        }
    }

    private void loadEcoTrackerFragment() {
        // Create a new EcoTrackerFragment instance
        EcoTrackerFragment ecoTrackerFragment = new EcoTrackerFragment();

        // Replace the fragment in the container
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.eco_tracker_layout, ecoTrackerFragment); // Use the container's ID
        transaction.commit(); // Commit the transaction
    }
}
