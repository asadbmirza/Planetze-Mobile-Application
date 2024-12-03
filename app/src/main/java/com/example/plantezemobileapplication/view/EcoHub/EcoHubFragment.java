package com.example.plantezemobileapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.presenter.EcoHubPresenter;
import com.example.plantezemobileapplication.view.main.MainActivity;

public class EcoHubFragment extends Fragment implements EcoHubView {

    private Button sustainableFashionButton;
    private Button greenHomeSolutionsButton;
    private Button energyEfficientButton;
    private Button articlesButton;
    private Button videosButton;

    private EcoHubPresenter presenter;

    public EcoHubFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ecohub, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sustainableFashionButton = view.findViewById(R.id.sustainableFashionButton);
        greenHomeSolutionsButton = view.findViewById(R.id.greenHomeSolutionsButton);
        energyEfficientButton = view.findViewById(R.id.energyEfficientButton);
        articlesButton = view.findViewById(R.id.articlesButton);
        videosButton = view.findViewById(R.id.videosButton);

        presenter = new EcoHubPresenter(this);

        sustainableFashionButton.setOnClickListener(v -> presenter.onSustainableFashionClicked());
        greenHomeSolutionsButton.setOnClickListener(v -> presenter.onGreenHomeSolutionsClicked());
        energyEfficientButton.setOnClickListener(v -> presenter.onEnergyEfficientClicked());
        articlesButton.setOnClickListener(v -> presenter.onArticlesClicked());
        videosButton.setOnClickListener(v -> presenter.onVideosClicked());
    }

    @Override
    public void loadFragment(Fragment fragment) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).loadFragment(fragment);
        }
    }
}
