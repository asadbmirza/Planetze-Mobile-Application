package com.example.plantezemobileapplication.model;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final HashMap<String, Retrofit> retrofitInstances = new HashMap<>();

    // Factory method to get Retrofit instance for a specific base URL
    public static Retrofit getInstance(String baseUrl) {
        if (!retrofitInstances.containsKey(baseUrl)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitInstances.put(baseUrl, retrofit);
        }
        return retrofitInstances.get(baseUrl);
    }
}
