package com.example.plantezemobileapplication.view;

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

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.VideoItem;
import com.example.plantezemobileapplication.presenter.VideosPresenter;

import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends AppCompatActivity implements VideosView {
    private RecyclerView recyclerView;
    private VideosAdapter adapter;
    private final List<VideoItem> videoList = new ArrayList<>();
    private VideosPresenter presenter;

    private Runnable searchRunnable;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_videos);

        recyclerView = findViewById(R.id.articlesRecyclerView);
        EditText searchBar = findViewById(R.id.searchBar);

        adapter = new VideosAdapter(this, videoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter = new VideosPresenter(this);
        presenter.fetchVideos("eco-friendly");

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
                        presenter.fetchVideos("eco-friendly " + s.toString().trim());
                    } else {
                        presenter.fetchVideos("eco-friendly");
                    }
                };
                handler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void showVideos(List<VideoItem> videos) {
        runOnUiThread(() -> {
            videoList.clear();
            videoList.addAll(videos);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void showError(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }
}
