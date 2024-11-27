package com.example.plantezemobileapplication.view;

import com.example.plantezemobileapplication.model.VideoItem;

import java.util.List;

public interface VideosView {
    void showVideos(List<VideoItem> videos);
    void showError(String message);
}