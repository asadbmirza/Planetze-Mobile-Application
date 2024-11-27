package com.example.plantezemobileapplication.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface NewsApiService {
    @GET("v2/everything")
    Call<NewsResponseModel> getNews(@Query("q") String query, @Query("language") String language, @Query("apiKey") String apiKey);
}
