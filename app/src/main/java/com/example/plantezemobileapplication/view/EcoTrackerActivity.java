package com.example.plantezemobileapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.plantezemobileapplication.R;



public class EcoTrackerActivity extends AppCompatActivity {

    EcoTrackerHabitFragment habitFragment;
    EcoTrackerMonitorFragment monitorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.eco_tracker_layout);

        ImageView monitorImage = findViewById(R.id.imageView2);
        ImageView habitImage = findViewById(R.id.habitImage);

        Glide.with(this)
                .load(R.drawable.cycling)
                .apply(new RequestOptions().override(800, 800))
                .into(monitorImage);

        Glide.with(this)
                .load(R.drawable.habit)
                .apply(new RequestOptions().override(800, 800))
                .into(habitImage);

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

        Button activityBtn = findViewById(R.id.view_activity_page_button);
        activityBtn.setOnClickListener(v -> {
            monitorFragment = new EcoTrackerMonitorFragment();
            navigateToFragment(monitorFragment, R.id.monitor_container);
        });
    }

    private void navigateToFragment(Fragment fragment, int viewId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(viewId, fragment);
        transaction.addToBackStack(null); // this adds the transaction to the back stack so the user can navigate back

        transaction.commit();
    }

}

