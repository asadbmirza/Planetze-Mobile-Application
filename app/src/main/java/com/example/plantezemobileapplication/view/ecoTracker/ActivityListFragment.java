package com.example.plantezemobileapplication.view.ecoTracker;

import static com.example.plantezemobileapplication.view.ecoTracker.EcoTrackerFragment.HABIT_FRAGMENT_TAG;
import static com.example.plantezemobileapplication.view.ecoTracker.EcoTrackerFragment.MONITOR_FRAGMENT_TAG;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.presenter.ActivityListPresenter;
import com.example.plantezemobileapplication.utils.Habit;
import com.example.plantezemobileapplication.utils.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "activity_title";
    private static final String ARG_QUESTIONS = "questions";

    private static final String ARG_DAY = "currentDay";
    private static final String ARG_MONTH = "currentMonth";
    private static final String ARG_WEEK = "currentWeek";
    private static final String ARG_ACTIVE_HABITS = "activeHabits";

    private List<Question> questions;
    private String activityName;

    private String currentDay;
    private String currentMonth;
    private String currentWeek;
    private ArrayList<Habit> activeHabits;




    public ActivityListFragment() {
        // Required empty public constructor
    }

    public static ActivityListFragment newInstance(String activityName, List<Question> questions, String day, String month, String week, ArrayList<Habit> activeHabits) {
        ActivityListFragment fragment = new ActivityListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, activityName);
        args.putParcelableArrayList(ARG_QUESTIONS, (ArrayList<? extends Parcelable>) questions);
        args.putString(ARG_DAY, day);
        args.putString(ARG_MONTH, month);
        args.putString(ARG_WEEK, week);
        args.putParcelableArrayList(ARG_ACTIVE_HABITS, activeHabits);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            activityName = getArguments().getString(ARG_TITLE);
            questions = getArguments().getParcelableArrayList(ARG_QUESTIONS);
            currentDay = getArguments().getString(ARG_DAY);
            currentMonth = getArguments().getString(ARG_MONTH);
            currentWeek = getArguments().getString(ARG_WEEK);
            activeHabits = getArguments().getParcelableArrayList(ARG_ACTIVE_HABITS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity_list, container, false);
        TextView activityTitle = view.findViewById(R.id.activity_title);
        activityTitle.setText(activityName);

        // Set the recyclerView with questions
        RecyclerView recyclerView = view.findViewById(R.id.activity_list_questions);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ActivityLogAdapter adapter = new ActivityLogAdapter(questions);
        recyclerView.setAdapter(adapter);

        // Submit answer
        Button submitActivityBtn = view.findViewById(R.id.submit_activity_btn);

        ActivityListPresenter presenter = new ActivityListPresenter();


        submitActivityBtn.setOnClickListener(v -> {
            Log.d("activitylist", "f");
            presenter.calculateTodaysActivity(questions, adapter.getEnteredAmounts(), currentDay, currentMonth, currentWeek);

            checkActiveHabits(adapter.getEnteredAmounts());
            navigateToFragment(new EcoTrackerMonitorFragment(), R.id.eco_tracker_monitor, MONITOR_FRAGMENT_TAG);
        });

        return view;
    }

    public void checkActiveHabits(List<Integer> enteredAmounts) {
        System.out.println(questions);
        System.out.println(enteredAmounts);
        for (int i = 0; i < questions.size(); i++) {
            String questionName = questions.get(i).getTitle();
            double questionValue = enteredAmounts.get(i);
            System.out.println(questionName);
            System.out.println(questionValue);
            for (Habit habit: activeHabits) {
                System.out.println(habit.getActivity());
                if (questionName.toLowerCase().contains(habit.getActivity().toLowerCase()) && questionValue > 0) {
                    displayHabitDialog();
                    return;
                }
            }
        }
    }

    private void displayHabitDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.habit_log_dialog);

        Button navigate = dialog.findViewById(R.id.view_habit_page_button);
        Button cancel = dialog.findViewById(R.id.exit_habit_page_button);

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        navigate.setOnClickListener(v -> {
            dialog.dismiss();
            EcoTrackerHabitFragment habitFragment = new EcoTrackerHabitFragment();
            navigateToFragment(habitFragment, R.id.eco_tracker_habit, HABIT_FRAGMENT_TAG);

        });

        dialog.show();
    }

    private void navigateToFragment(Fragment fragment, int viewId, String tag) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(viewId, fragment, tag);
        transaction.addToBackStack(null); // Add to backstack to enable back navigation
        transaction.commit();
    }



}