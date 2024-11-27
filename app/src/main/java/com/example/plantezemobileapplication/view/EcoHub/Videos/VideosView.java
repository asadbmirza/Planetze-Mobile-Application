package com.example.plantezemobileapplication.view.EcoHub.Videos;

import com.example.plantezemobileapplication.model.VideoItemModel;

import java.util.List;

public interface VideosView {
    void showVideos(List<VideoItemModel> videos);
    void showError(String message);
}