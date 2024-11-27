package com.example.plantezemobileapplication.view.EcoHub.Articles;

import com.example.plantezemobileapplication.model.ArticleModel;

import java.util.List;

public interface ArticlesView {
    void showArticles(List<ArticleModel> articles);
    void showError(String message);
}

