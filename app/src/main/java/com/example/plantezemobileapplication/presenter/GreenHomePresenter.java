package com.example.plantezemobileapplication.presenter;

import android.content.Context;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.ArticleModel;
import com.example.plantezemobileapplication.view.EcoHub.GreenHome.GreenHomeView;
import com.google.gson.Gson;
import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GreenHomePresenter {

    private GreenHomeView view;
    private Context context;
    private List<ArticleModel> articles;
    private List<ArticleModel> fullList;

    public GreenHomePresenter(GreenHomeView view) {
        this.view = view;
        this.context = (Context) view;
        articles = new ArrayList<>();
        fullList = new ArrayList<>();
    }

    public void loadArticles() {
        String json = loadJSONFromRaw();
        if (json != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ArticleModel>>() {}.getType();
            articles = gson.fromJson(json, listType);
            fullList.addAll(articles);
            view.displayArticles(articles);
        } else {
            view.showError("Error loading articles");
        }
    }

    public void filterArticles(String query) {
        if (query.isEmpty()) {
            articles = new ArrayList<>(fullList);
        } else {
            List<ArticleModel> filteredList = new ArrayList<>();
            for (ArticleModel article : fullList) {
                if (article.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(article);
                }
            }
            articles = filteredList;
        }
        view.displayArticles(articles);
    }

    private String loadJSONFromRaw() {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.greenhome_solutions);
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

