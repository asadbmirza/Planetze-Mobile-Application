package com.example.plantezemobileapplication.view;

import com.example.plantezemobileapplication.model.ArticleModel;

import java.util.List;
public interface GreenHomeView {
    void displayArticles(List<ArticleModel> articles);
    void showError(String message);
}

