package com.example.plantezemobileapplication.view.EcoHub.Fashion;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.FashionCategoryModel;
import com.example.plantezemobileapplication.presenter.FashionPresenter;

import java.util.List;

public class FashionFragment extends AppCompatActivity implements FashionView {

    private RecyclerView recyclerView;
    private FashionAdapter adapter;
    private FashionPresenter presenter;
    private Runnable searchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sustainable_fashion);

        recyclerView = findViewById(R.id.fashionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EditText searchBar = findViewById(R.id.searchBar);

        presenter = new FashionPresenter(this);
        presenter.loadFashionCategories(this);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    new Handler(Looper.getMainLooper()).removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> {
                    if (!s.toString().trim().isEmpty()) {
                        presenter.filterFashionList(s.toString().trim(), FashionFragment.this);
                    } else {
                        presenter.filterFashionList("", FashionFragment.this);
                    }
                };
                new Handler(Looper.getMainLooper()).postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void setFashionCategories(List<FashionCategoryModel> fashionCategories) {
        adapter = new FashionAdapter(fashionCategories, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


