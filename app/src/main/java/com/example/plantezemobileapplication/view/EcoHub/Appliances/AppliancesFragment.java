package com.example.plantezemobileapplication.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.Appliance;
import com.example.plantezemobileapplication.presenter.AppliancesPresenter;

import java.util.List;

public class AppliancesFragment extends AppCompatActivity implements AppliancesView {

    private RecyclerView recyclerView;
    private AppliancesAdapter adapter;
    private AppliancesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_appliances);

        recyclerView = findViewById(R.id.applicancesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EditText searchBar = findViewById(R.id.searchBar);

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
    public void showAppliances(List<Appliance> appliances) {
        adapter = new AppliancesAdapter(this, appliances);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
