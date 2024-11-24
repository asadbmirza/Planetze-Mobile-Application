package com.example.plantezemobileapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.plantezemobileapplication.BuildConfig;

public class ArticlesFragment extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticlesAdapter adapter;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_articles);

        recyclerView = findViewById(R.id.articlesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EditText searchBar = findViewById(R.id.searchBar);

        fetchArticles("eco-friendly");

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable); // Cancel the previous runnable
                }

                searchRunnable = () -> {
                    if (!s.toString().trim().isEmpty()) {
                        fetchArticles("eco-friendly AND "+s.toString().trim());
                    }
                    else {
                        fetchArticles("eco-friendly");
                    }
                };
                handler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fetchArticles(String query) {
        NewsApiService apiService = RetrofitClient.getInstance().create(NewsApiService.class);
        String apiKey = BuildConfig.NEWS_API_KEY;
        String language = "en";

        apiService.getNews(query, language, apiKey).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    adapter = new ArticlesAdapter(ArticlesFragment.this, articles);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ArticlesFragment.this, "Failed to load articles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(ArticlesFragment.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

