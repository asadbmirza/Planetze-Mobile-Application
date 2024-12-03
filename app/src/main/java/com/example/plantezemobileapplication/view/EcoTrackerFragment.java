package com.example.plantezemobileapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.plantezemobileapplication.R;


public class EcoTrackerFragment extends Fragment {

    public static final String HABIT_FRAGMENT_TAG = "HabitFragment";
    public static final String MONITOR_FRAGMENT_TAG = "MonitorFragment";
    EcoTrackerHabitFragment habitFragment;
    EcoTrackerMonitorFragment monitorFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_eco_tracker_layout, container, false);



        Button habitsBtn = view.findViewById(R.id.view_habit_page_button);
        habitsBtn.setOnClickListener(v -> {
            habitFragment = new EcoTrackerHabitFragment();
            navigateToFragment(habitFragment, R.id.eco_tracker_habit, HABIT_FRAGMENT_TAG);
        });

        Button activityBtn = view.findViewById(R.id.view_activity_page_button);
        activityBtn.setOnClickListener(v -> {
            monitorFragment = new EcoTrackerMonitorFragment();
            navigateToFragment(monitorFragment, R.id.monitor_container, MONITOR_FRAGMENT_TAG);
        });
        return view;
    }

    private void navigateToFragment(Fragment fragment, int viewId, String tag) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(viewId, fragment, tag);
        transaction.addToBackStack(null); // Add to backstack to enable back navigation
        transaction.commit();
    }


}

