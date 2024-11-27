package com.example.plantezemobileapplication.view;

import com.example.plantezemobileapplication.model.Article;

import java.util.List;

public interface ArticlesView {
    void showArticles(List<Article> articles);
    void showError(String message);
}

