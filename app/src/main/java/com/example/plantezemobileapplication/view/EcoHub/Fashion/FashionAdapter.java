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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.FashionCategory;

import java.util.ArrayList;
import java.util.List;

public class FashionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int item = 0;
    private static final int footer = 1;
    private Context context;
    private List<FashionCategory> fashionCategories;
    private List<FashionCategory> fullList;

    public FashionAdapter(List<FashionCategory> fashionCategories, Context context) {
        this.fashionCategories = fashionCategories;
        this.fullList = new ArrayList<>(fashionCategories);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == fashionCategories.size()) {
            return footer;
        }
        return item;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == footer) {
            View view = LayoutInflater.from(context).inflate(R.layout.fashion_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.fashion_card, parent, false);
            return new FashionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FashionViewHolder) {
            FashionCategory category = fashionCategories.get(position);
            FashionViewHolder fashionHolder = (FashionViewHolder) holder;

            fashionHolder.title.setText(category.getFashionCategory());
            String desc = "Check out eco-friendly brands for " + category.getFashionCategory();
            fashionHolder.description.setText(desc);

            Glide.with(context)
                    .load(category.getFashionCategoryImg())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(fashionHolder.image);

            fashionHolder.learnMoreButton.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(category.getFashionCategoryLink()));
                context.startActivity(intent);
            });
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.creditText.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://directory.goodonyou.eco/"));
                context.startActivity(intent);
            });
        }
    }

    public void filterList(String query) {
        if (query.isEmpty()) {
            fashionCategories = new ArrayList<>(fullList);
        } else {
            List<FashionCategory> filteredList = new ArrayList<>();
            for (FashionCategory category : fullList) {
                if (category.getFashionCategory().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(category);
                }
            }
            fashionCategories = filteredList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fashionCategories.size() + 1;
    }

    public static class FashionViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;
        Button learnMoreButton;

        public FashionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.fashionCategoryTitle);
            description = itemView.findViewById(R.id.fashionCategoryDescription);
            image = itemView.findViewById(R.id.fashionCategoryImage);
            learnMoreButton = itemView.findViewById(R.id.learnMoreButton);
        }
    }
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView creditText;
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            creditText = itemView.findViewById(R.id.creditTextView);
        }
    }
}

