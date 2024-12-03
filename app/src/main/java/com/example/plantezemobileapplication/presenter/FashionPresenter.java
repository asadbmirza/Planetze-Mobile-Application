package com.example.plantezemobileapplication.presenter;

import android.content.Context;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.FashionCategoryModel;
import com.example.plantezemobileapplication.view.EcoHub.Fashion.FashionView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FashionPresenter {

    private FashionView view;

    public FashionPresenter(FashionView view) {
        this.view = view;
    }

    public void loadFashionCategories(Context context) {
        String json = loadJSONFromRaw(context);

        if (json != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FashionCategoryModel>>() {}.getType();
            List<FashionCategoryModel> fashionCategories = gson.fromJson(json, listType);

            view.setFashionCategories(fashionCategories);
        } else {
            view.showError("Error loading JSON");
        }
    }

    public void filterFashionList(String query, Context context) {
        String json = loadJSONFromRaw(context);
        if (json != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FashionCategoryModel>>() {}.getType();
            List<FashionCategoryModel> fullList = gson.fromJson(json, listType);

            List<FashionCategoryModel> filteredList = new ArrayList<>();
            for (FashionCategoryModel category : fullList) {
                if (category.getFashionCategory().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(category);
                }
            }
            view.setFashionCategories(filteredList);
        } else {
            view.showError("Error loading JSON");
        }
    }

    private String loadJSONFromRaw(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.sustainable_fashion);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
