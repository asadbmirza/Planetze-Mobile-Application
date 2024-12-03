package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.view.EcoHub.Appliances.AppliancesFragment;
import com.example.plantezemobileapplication.view.EcoHub.Articles.ArticlesFragment;
import com.example.plantezemobileapplication.view.EcoHub.Fashion.FashionFragment;
import com.example.plantezemobileapplication.view.EcoHub.GreenHome.GreenHomeFragment;
import com.example.plantezemobileapplication.view.EcoHub.Videos.VideosFragment;
import com.example.plantezemobileapplication.view.EcoHub.EcoHubView;

public class EcoHubPresenter {

    private EcoHubView view;

    public EcoHubPresenter(EcoHubView view) {
        this.view = view;
    }

    public void onSustainableFashionClicked() {
        view.loadFragment(new FashionFragment());
    }

    public void onGreenHomeSolutionsClicked() {
        view.loadFragment(new GreenHomeFragment());
    }

    public void onEnergyEfficientClicked() {
        view.loadFragment(new AppliancesFragment());
    }

    public void onArticlesClicked() {
        view.loadFragment(new ArticlesFragment());
    }

    public void onVideosClicked() {
        view.loadFragment(new VideosFragment());
    }
}

