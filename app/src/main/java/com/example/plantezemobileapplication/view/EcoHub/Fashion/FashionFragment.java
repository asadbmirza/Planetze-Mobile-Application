package com.example.plantezemobileapplication.view.EcoHub.Fashion;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.plantezemobileapplication.model.FashionCategoryModel;
import com.example.plantezemobileapplication.presenter.FashionPresenter;

import java.util.List;

public class FashionFragment extends Fragment implements FashionView {

    private RecyclerView recyclerView;
    private FashionAdapter adapter;
    private FashionPresenter presenter;
    private Runnable searchRunnable;

    public FashionFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sustainable_fashion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fashionRecyclerView); // Use `view.findViewById`
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Use `getContext`

        EditText searchBar = view.findViewById(R.id.searchBar); // Use `view.findViewById`

        presenter = new FashionPresenter(this);
        presenter.loadFashionCategories(getContext()); // Use `getContext`

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
                        presenter.filterFashionList(s.toString().trim(), getContext());
                    } else {
                        presenter.filterFashionList("", getContext());
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
        adapter = new FashionAdapter(fashionCategories, getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
