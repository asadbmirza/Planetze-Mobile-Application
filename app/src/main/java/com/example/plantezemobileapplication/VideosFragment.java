package com.example.plantezemobileapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VideosAdapter adapter;
    private List<VideoItem> videoList;
    private Runnable searchRunnable;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_videos);

        recyclerView = findViewById(R.id.articlesRecyclerView);
        EditText searchBar = findViewById(R.id.searchBar);

        videoList = new ArrayList<>();
        adapter = new VideosAdapter(this, videoList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fetchVideos("eco-friendly");

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
                        fetchVideos("eco-friendly "+s.toString().trim());
                    }
                    else {
                        fetchVideos("eco-friendly");
                    }
                };
                handler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fetchVideos(String query) {
        new Thread(() -> {
            try {
                YouTubeApiHelper helper = new YouTubeApiHelper();
                List<VideoItem> results = helper.searchVideos(query);

                runOnUiThread(() -> {
                    videoList.clear();
                    videoList.addAll(results);
                    adapter.notifyDataSetChanged();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}