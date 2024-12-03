package com.example.plantezemobileapplication.view.questionnaire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;

import java.util.List;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Map.Entry<String, Double>> categoryList;

    public CategoryAdapter(List<Map.Entry<String, Double>> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_breakdown_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Map.Entry<String, Double> category = categoryList.get(position);
        holder.categoryViewTitle.setText(category.getKey().toUpperCase());
        holder.categoryEmissionView.setText(String.format("%.2f", category.getValue() * 0.001) + " tonnes/yr");
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryViewTitle, categoryEmissionView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryViewTitle = itemView.findViewById(R.id.category_title);
            categoryEmissionView = itemView.findViewById(R.id.category_emissions);
        }
    }
}

