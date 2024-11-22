package com.example.plantezemobileapplication.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.plantezemobileapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EcoTrackerMonitorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EcoTrackerMonitorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        Button btn = view.findViewById(R.id.button5);

        //Redirect user to specific activity log
        btn.setOnClickListener(v -> {
            Fragment targetFragment = new ActivityListFragment();
            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();

            transaction.replace(R.id.fragmentContainerView, targetFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        // Inflate the layout for this fragment
        return view;
    }
}