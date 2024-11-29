package com.example.plantezemobileapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.model.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class AppliancesFragment extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppliancesAdapter adapter;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_appliances);

        recyclerView = findViewById(R.id.applicancesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EditText searchBar = findViewById(R.id.searchBar);

        fetchAppliances("Energy-efficient appliances");

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> {
                    if (!s.toString().trim().isEmpty()) {
                        fetchAppliances("Energy-efficient appliances " + s.toString().trim());
                    } else {
                        fetchAppliances("Energy-efficient appliances");
                    }
                };
                handler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    private void fetchAppliances(String query) {
        ApplianceApiService apiService = RetrofitClient.getInstance("https://real-time-amazon-data.p.rapidapi.com/").create(ApplianceApiService.class);
        String apiKey = BuildConfig.RAPID_API_KEY;

        apiService.getAppliances(query, 4, apiKey, "real-time-amazon-data.p.rapidapi.com").enqueue(new Callback<ApplianceResponse>() {
            @Override
            public void onResponse(Call<ApplianceResponse> call, Response<ApplianceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Appliance> appliances = response.body().getData().getAppliances();
                    Log.d("API_RAW_RESPONSE", response.body().toString());
                    adapter = new AppliancesAdapter(AppliancesFragment.this, appliances);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AppliancesFragment.this, "Failed to load articles", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Response error: " + response.message());
                    Log.e("API_ERROR", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApplianceResponse> call, Throwable t) {
                Toast.makeText(AppliancesFragment.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

