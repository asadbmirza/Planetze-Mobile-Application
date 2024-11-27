package com.example.plantezemobileapplication.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.EcoMonitorModel;
import com.example.plantezemobileapplication.presenter.EcoTrackerMonitorPresenter;
import com.example.plantezemobileapplication.utils.Question;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EcoTrackerMonitorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EcoTrackerMonitorFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView totalDailyEmissionView;
    TextView transportationEmissionView;
    TextView foodEmissionView;
    TextView consumptionEmissionView;
    EcoTrackerMonitorPresenter presenter;
    EcoMonitorModel model;

    public EcoTrackerMonitorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EcoTrackerMonitorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EcoTrackerMonitorFragment newInstance(String param1, String param2) {
        EcoTrackerMonitorFragment fragment = new EcoTrackerMonitorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco_tracker_monitor, container, false);;
        Button transporationActivityBtn = view.findViewById(R.id.transportation_log_btn);
        Button foodActivityBtn = view.findViewById(R.id.food_consumption_log);
        Button consumptionActivityBtn = view.findViewById(R.id.consumption_log_btn);

        //Update CO2 tracker display
        totalDailyEmissionView = view.findViewById(R.id.daily_emission_text);
        transportationEmissionView = view.findViewById(R.id.transportation_emission_text);
        foodEmissionView = view.findViewById(R.id.food_emission_text);
        consumptionEmissionView = view.findViewById(R.id.consumption_emission_text);

        presenter = new EcoTrackerMonitorPresenter(this);
        model = new EcoMonitorModel(this, presenter);
        model.getTodaysActivities();
        model.setDefaultVehicle();
        model.setUserEnergy();

        transporationActivityBtn.setOnClickListener(this);
        foodActivityBtn.setOnClickListener(this);
        consumptionActivityBtn.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    public void setTotalDailyEmissionView(String text) {
        totalDailyEmissionView.setText(text);
    }

    public void setTransportationEmissionView(String text) {
        transportationEmissionView.setText(text);
    }

    public void setFoodEmissionView(String text) {
        foodEmissionView.setText(text);
    }

    public void setConsumptionEmissionView(String text) {
        consumptionEmissionView.setText(text);
    }

    @Override
    public void onClick(View v) {
        Button clickedBtn = (Button) v;
        List<Question> questionList = null;
        String activityName = "";

        if (clickedBtn.getId() == R.id.transportation_log_btn) {
            questionList = presenter.initializeTransportationQuestions();
            activityName = "Transportation Activities";
        }
        else if (clickedBtn.getId() == R.id.food_consumption_log) {
            questionList = presenter.initializeFoodQuestions();
            activityName = "Food Consumption Activities";
        }
        else if (clickedBtn.getId() == R.id.consumption_log_btn) {
            questionList = presenter.initializeConsumptionQuestions();
            activityName = "Shopping & Consumption Activities";
        }

        Fragment targetFragment = ActivityListFragment.newInstance(activityName, questionList);
        FragmentTransaction transaction = requireActivity()
                .getSupportFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.fragmentContainerView, targetFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}