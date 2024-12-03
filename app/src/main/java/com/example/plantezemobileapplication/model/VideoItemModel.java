package com.example.plantezemobileapplication.model;

public class VideoItemModel {
    private String videoId;
    private String title;
    private String description;
    private String thumbnailUrl;

    public VideoItemModel(String videoId, String title, String description, String thumbnailUrl) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
