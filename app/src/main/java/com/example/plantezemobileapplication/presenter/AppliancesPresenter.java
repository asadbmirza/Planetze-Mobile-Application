package com.example.plantezemobileapplication.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.plantezemobileapplication.BuildConfig;
import com.example.plantezemobileapplication.model.ApplianceModel;
import com.example.plantezemobileapplication.model.ApplianceApiService;
import com.example.plantezemobileapplication.model.ApplianceResponseModel;
import com.example.plantezemobileapplication.model.RetrofitClient;
import com.example.plantezemobileapplication.view.EcoHub.Appliances.AppliancesView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliancesPresenter {

    private AppliancesView view;
    private Handler handler;
    private Runnable searchRunnable;

    public AppliancesPresenter(AppliancesView view) {
        this.view = view;
        handler = new Handler(Looper.getMainLooper());
    }

    public void fetchAppliances(String query) {

        ApplianceApiService apiService = RetrofitClient.getInstance("https://real-time-amazon-data.p.rapidapi.com/").create(ApplianceApiService.class);
        String apiKey = BuildConfig.RAPID_API_KEY;

        apiService.getAppliances(query, 4, apiKey, "real-time-amazon-data.p.rapidapi.com").enqueue(new Callback<ApplianceResponseModel>() {
            @Override
            public void onResponse(Call<ApplianceResponseModel> call, Response<ApplianceResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ApplianceModel> appliances = response.body().getData().getAppliances();
                    view.showAppliances(appliances);
                } else {
                    view.showError("Failed to load articles");
                }
            }

            @Override
            public void onFailure(Call<ApplianceResponseModel> call, Throwable t) {
                view.showError("Error: " + t.getMessage());
            }
        });
    }

    public void onSearchTextChanged(String searchQuery) {
        if (searchRunnable != null) {
            handler.removeCallbacks(searchRunnable);
        }

        searchRunnable = () -> {
            String query = searchQuery.trim().isEmpty() ? "Energy-efficient appliances" : "Energy-efficient appliances " + searchQuery.trim();
            fetchAppliances(query);
        };

        handler.postDelayed(searchRunnable, 500);
    }
}

