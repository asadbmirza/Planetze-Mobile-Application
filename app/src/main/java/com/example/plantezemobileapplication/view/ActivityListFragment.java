package com.example.plantezemobileapplication.view;

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
import com.example.plantezemobileapplication.model.EcoMonitorModel;
import com.example.plantezemobileapplication.presenter.ActivityListPresenter;
import com.example.plantezemobileapplication.utils.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<Question> questions;
    private String activityName;

    private String currentDay;
    private String currentMonth;
    private String currentWeek;

    public ActivityListFragment() {
        // Required empty public constructor
    }

    public static ActivityListFragment newInstance(String activityName, List<Question> questions, String day, String month, String week) {
        ActivityListFragment fragment = new ActivityListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, activityName);
        args.putParcelableArrayList(ARG_QUESTIONS, (ArrayList<? extends Parcelable>) questions);
        args.putString(ARG_DAY, day);
        args.putString(ARG_MONTH, month);
        args.putString(ARG_WEEK, week);
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
            presenter.calculateTodaysActivity(questions, adapter.getEnteredAmounts(), currentDay, currentMonth, currentWeek);

            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }


}