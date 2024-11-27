package com.example.plantezemobileapplication.view.EcoHub.Articles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.ArticleModel;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private List<ArticleModel> articles;
    private Context context;

    public ArticlesAdapter(Context context, List<ArticleModel> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.articles_card, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;
        Button readMoreButton;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.articleTitle);
            description = itemView.findViewById(R.id.articleDescription);
            image = itemView.findViewById(R.id.articleImage);
            readMoreButton = itemView.findViewById(R.id.readMoreButton);
        }
    }
}

