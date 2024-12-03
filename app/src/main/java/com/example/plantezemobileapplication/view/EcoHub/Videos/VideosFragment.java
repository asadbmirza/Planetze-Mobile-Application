package com.example.plantezemobileapplication.view.EcoHub.Videos;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.VideoItemModel;
import com.example.plantezemobileapplication.presenter.VideosPresenter;

import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends Fragment implements VideosView {
    private RecyclerView recyclerView;
    private VideosAdapter adapter;
    private final List<VideoItemModel> videoList = new ArrayList<>();
    private VideosPresenter presenter;

    private Runnable searchRunnable;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public VideosFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.articlesRecyclerView);
        EditText searchBar = view.findViewById(R.id.searchBar);

        adapter = new VideosAdapter(getContext(), videoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void showVideos(List<VideoItemModel> videos) {
        requireActivity().runOnUiThread(() -> {
            videoList.clear();
            videoList.addAll(videos);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void showError(String message) {
        requireActivity().runOnUiThread(() -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }
}
