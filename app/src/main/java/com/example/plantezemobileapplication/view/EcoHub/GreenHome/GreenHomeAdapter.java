package com.example.plantezemobileapplication.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.ArticleModel;

import java.util.ArrayList;
import java.util.List;

public class GreenHomeAdapter extends RecyclerView.Adapter<GreenHomeAdapter.GreenHomeViewHolder> {
    private Context context;
    private List<ArticleModel> articles;
    private List<ArticleModel> fullList;

    public GreenHomeAdapter(List<ArticleModel> articles, Context context) {
        this.articles = articles;
        this.fullList = new ArrayList<>(articles);
        this.context = context;
    }

    @NonNull
    @Override
    public GreenHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.articles_card, parent, false);
        return new GreenHomeAdapter.GreenHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GreenHomeViewHolder holder, int position) {
        ArticleModel article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());

        Glide.with(context)
                .load(article.getUrlToImage())
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.image);

        holder.readMoreButton.setOnClickListener(v -> {
            String url = article.getUrl();
            if (url != null && !url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "No URL available for this article", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void filterList(String query) {
        if (query.isEmpty()) {
            articles = new ArrayList<>(fullList);
        } else {
            List<ArticleModel> filteredList = new ArrayList<>();
            for (ArticleModel article : fullList) {
                if (article.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(article);
                }
            }
            articles = filteredList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class GreenHomeViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;
        Button readMoreButton;

        public GreenHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.articleTitle);
            description = itemView.findViewById(R.id.articleDescription);
            image = itemView.findViewById(R.id.articleImage);
            readMoreButton = itemView.findViewById(R.id.readMoreButton);
        }
    }
}
