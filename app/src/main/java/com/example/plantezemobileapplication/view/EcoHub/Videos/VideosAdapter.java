package com.example.plantezemobileapplication.view.EcoHub.Videos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.VideoItemModel;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
    private Context context;
    private List<VideoItemModel> videoList;

    public VideosAdapter(Context context, List<VideoItemModel> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.videos_card, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItemModel video = videoList.get(position);

        holder.title.setText(video.getTitle());
        holder.description.setText(video.getDescription());
        Glide.with(context)
                .load(video.getThumbnailUrl())
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.thumbnail);

        holder.watchButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + video.getVideoId()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView description;
        Button watchButton;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.articleImage);
            title = itemView.findViewById(R.id.videoTitle);
            description = itemView.findViewById(R.id.videoDescription);
            watchButton = itemView.findViewById(R.id.watchVideoButton);
        }
    }
}
