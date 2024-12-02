package com.example.plantezemobileapplication.view;

import android.content.Context;

import com.example.plantezemobileapplication.model.FashionCategory;

import java.util.List;

public interface FashionView {
    void setFashionCategories(List<FashionCategory> fashionCategories);
    void showError(String message);
}
