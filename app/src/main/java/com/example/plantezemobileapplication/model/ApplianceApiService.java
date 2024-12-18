package com.example.plantezemobileapplication.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
public interface ApplianceApiService {
    @GET("search")
    Call<ApplianceResponseModel> getAppliances(@Query("query") String query, @Query("page") int page,
                                               @Header("x-rapidapi-key") String apiKey,
                                               @Header("x-rapidapi-host") String apiHost);
}
