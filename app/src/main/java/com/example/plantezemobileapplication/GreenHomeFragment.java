package com.example.plantezemobileapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.model.ArticleModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class GreenHomeFragment extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GreenHomeAdapter adapter;
    private Runnable searchRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_greenhome);

        recyclerView = findViewById(R.id.articlesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EditText searchBar = findViewById(R.id.searchBar);

        loadArticles();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    new Handler(Looper.getMainLooper()).removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> {
                    if (!s.toString().trim().isEmpty()) {
                        adapter.filterList(s.toString().trim());
                    } else {
                        adapter.filterList("");
                    }
                };
                new Handler(Looper.getMainLooper()).postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private String loadJSONFromRaw() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.greenhome_solutions);
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

    private void loadArticles() {
        String json = loadJSONFromRaw();

        if (json != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ArticleModel>>() {}.getType();
            List<ArticleModel> articles = gson.fromJson(json, listType);

            adapter = new GreenHomeAdapter(articles, this);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Error loading JSON", Toast.LENGTH_SHORT).show();
        }
    }
}
