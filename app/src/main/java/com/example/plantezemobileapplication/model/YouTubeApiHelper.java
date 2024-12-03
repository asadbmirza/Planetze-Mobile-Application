package com.example.plantezemobileapplication.model;

import android.text.Html;

import com.example.plantezemobileapplication.BuildConfig;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;


public class YouTubeApiHelper {
    private static final String API_KEY = BuildConfig.YOUTUBE_API_KEY;
    private static final String APPLICATION_NAME = "PlanetZe";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public List<VideoItemModel> searchVideos(String query) throws GeneralSecurityException, IOException {
        YouTube youtube = new YouTube.Builder(
                new NetHttpTransport(),
                JSON_FACTORY,
                null
        ).setApplicationName(APPLICATION_NAME).build();

        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(API_KEY);
        search.setQ(query);
        search.setType("video");
        search.setMaxResults(100L);
        search.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/high/url)");

        List<SearchResult> results = search.execute().getItems();
        List<VideoItemModel> videoItems = new ArrayList<>();

        for (SearchResult result : results) {
            videoItems.add(new VideoItemModel(
                    result.getId().getVideoId(),
                    Html.fromHtml(result.getSnippet().getTitle(), Html.FROM_HTML_MODE_LEGACY).toString(),
                    result.getSnippet().getDescription(),
                    result.getSnippet().getThumbnails().getHigh().getUrl()
            ));
        }

        return videoItems;
    }
}
