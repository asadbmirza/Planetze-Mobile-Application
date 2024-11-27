package com.example.plantezemobileapplication.view.EcoHub.Articles;

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

import com.example.plantezemobileapplication.BuildConfig;
import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.ArticleModel;
import com.example.plantezemobileapplication.model.NewsApiService;
import com.example.plantezemobileapplication.model.RetrofitClient;
import com.example.plantezemobileapplication.presenter.ArticlesPresenter;

import java.util.List;

public class ArticlesFragment extends AppCompatActivity implements ArticlesView {

    private RecyclerView recyclerView;
    private ArticlesAdapter adapter;
    private ArticlesPresenter presenter;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_articles);

        recyclerView = findViewById(R.id.articlesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EditText searchBar = findViewById(R.id.searchBar);

        NewsApiService apiService = RetrofitClient.getInstance("https://newsapi.org/")
                .create(NewsApiService.class);
        presenter = new ArticlesPresenter(this, apiService);

        fetchArticles("eco-friendly");

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> {
                    if (!s.toString().trim().isEmpty()) {
                        fetchArticles("eco-friendly AND " + s.toString().trim());
                    } else {
                        fetchArticles("eco-friendly");
                    }
                };
                handler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchArticles(String query) {
        presenter.fetchArticles(query, BuildConfig.NEWS_API_KEY);
    }

    @Override
    public void showArticles(List<ArticleModel> articles) {
        adapter = new ArticlesAdapter(this, articles);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
