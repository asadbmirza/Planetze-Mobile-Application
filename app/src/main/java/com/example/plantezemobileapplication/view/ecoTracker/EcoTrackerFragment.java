package com.example.plantezemobileapplication.view.ecoTracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.view.main.MainActivity;


public class EcoTrackerFragment extends Fragment {

    public static final String HABIT_FRAGMENT_TAG = "HabitFragment";
    public static final String MONITOR_FRAGMENT_TAG = "MonitorFragment";
    EcoTrackerHabitFragment habitFragment;
    EcoTrackerMonitorFragment monitorFragment;

    public EcoTrackerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_eco_tracker_layout, container, false);

        Button habitsBtn = view.findViewById(R.id.view_habit_page_button);
        habitsBtn.setOnClickListener(v -> {
            habitFragment = new EcoTrackerHabitFragment();
            navigateToFragment(habitFragment);
        });

        Button activityBtn = view.findViewById(R.id.view_activity_page_button);
        activityBtn.setOnClickListener(v -> {
            monitorFragment = new EcoTrackerMonitorFragment();
            navigateToFragment(monitorFragment);
        });

        ImageView monitorImage = view.findViewById(R.id.imageView2);
        ImageView habitImage = view.findViewById(R.id.habitImage);

        Glide.with(this)
                .load(R.drawable.cycling)
                .apply(new RequestOptions().override(800, 800))
                .into(monitorImage);

        Glide.with(this)
                .load(R.drawable.habit)
                .apply(new RequestOptions().override(800, 800))
                .into(habitImage);
        return view;
    }



    private void navigateToFragment(Fragment fragment) {

        if (getActivity() != null) {
            ((MainActivity) getActivity()).loadFragment(fragment);
        }
    }


}

