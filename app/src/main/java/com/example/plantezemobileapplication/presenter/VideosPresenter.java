package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.YouTubeApiHelper;
import com.example.plantezemobileapplication.model.VideoItemModel;
import com.example.plantezemobileapplication.view.EcoHub.Videos.VideosView;

import java.util.List;

public class VideosPresenter {
    private final VideosView view;
    private final YouTubeApiHelper apiHelper;

    public VideosPresenter(VideosView view) {
        this.view = view;
        this.apiHelper = new YouTubeApiHelper();
    }

    public void fetchVideos(String query) {
        new Thread(() -> {
            try {
                List<VideoItemModel> videos = apiHelper.searchVideos(query);
                view.showVideos(videos);
            } catch (Exception e) {
                view.showError("Failed to load videos: " + e.getMessage());
            }
        }).start();
    }
}

