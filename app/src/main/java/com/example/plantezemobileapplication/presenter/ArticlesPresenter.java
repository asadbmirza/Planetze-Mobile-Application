package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.ArticleModel;
import com.example.plantezemobileapplication.model.NewsApiService;
import com.example.plantezemobileapplication.model.NewsResponseModel;
import com.example.plantezemobileapplication.view.EcoHub.Articles.ArticlesView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesPresenter {

    private final ArticlesView view;
    private final NewsApiService apiService;

    public ArticlesPresenter(ArticlesView view, NewsApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void fetchArticles(String query, String apiKey) {

        apiService.getNews(query, "en", apiKey).enqueue(new Callback<NewsResponseModel>() {
            @Override
            public void onResponse(Call<NewsResponseModel> call, Response<NewsResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ArticleModel> articles = response.body().getArticles();
                    view.showArticles(articles);
                } else {
                    view.showError("Failed to load articles");
                }
            }

            @Override
            public void onFailure(Call<NewsResponseModel> call, Throwable t) {
                view.showError("Error: " + t.getMessage());
            }
        });
    }
}

