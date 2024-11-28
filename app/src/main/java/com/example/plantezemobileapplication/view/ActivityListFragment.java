package com.example.plantezemobileapplication.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

    private List<Question> questions;
    private String activityName;

    public ActivityListFragment() {
        // Required empty public constructor
    }

    public static ActivityListFragment newInstance(String activityName, List<Question> questions) {
        ActivityListFragment fragment = new ActivityListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, activityName);
        args.putParcelableArrayList(ARG_QUESTIONS, (ArrayList<? extends Parcelable>) questions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            activityName = getArguments().getString(ARG_TITLE);
            questions = getArguments().getParcelableArrayList(ARG_QUESTIONS);
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
            presenter.calculateTodaysActivity(questions, adapter.getEnteredAmounts());

            Fragment targetFragment = new EcoTrackerMonitorFragment();
            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();

            transaction.replace(R.id.fragmentContainerView, targetFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }
}