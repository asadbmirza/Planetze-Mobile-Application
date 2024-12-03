package com.example.plantezemobileapplication.view.EcoHub.Appliances;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.ApplianceModel;
import com.example.plantezemobileapplication.presenter.AppliancesPresenter;

import java.util.List;

public class AppliancesFragment extends Fragment implements AppliancesView {

    private RecyclerView recyclerView;
    private AppliancesAdapter adapter;
    private AppliancesPresenter presenter;

    public AppliancesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appliances, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.appliancesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EditText searchBar = view.findViewById(R.id.searchBar);

        presenter = new AppliancesPresenter(this);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onSearchTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        presenter.fetchAppliances("Energy-efficient appliances");
    }

    @Override
    public void showAppliances(List<ApplianceModel> appliances) {
        adapter = new AppliancesAdapter(getContext(), appliances);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
