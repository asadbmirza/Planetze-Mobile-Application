package com.example.plantezemobileapplication.view.EcoHub.Fashion;

import com.example.plantezemobileapplication.model.FashionCategoryModel;

import java.util.List;

public interface FashionView {
    void setFashionCategories(List<FashionCategoryModel> fashionCategories);
    void showError(String message);
}
