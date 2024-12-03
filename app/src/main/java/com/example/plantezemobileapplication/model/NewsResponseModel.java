package com.example.plantezemobileapplication.model;

import java.util.List;

public class NewsResponseModel {
    private String status;
    private int totalResults;
    private List<ArticleModel> articles;

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<ArticleModel> getArticles() {
        return articles;
    }
}
