package com.example.plantezemobileapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class AppliancesAdapter extends RecyclerView.Adapter<AppliancesAdapter.ApplianceViewHolder> {

    private Context context;
    private List<Appliance> appliances;

    public AppliancesAdapter(Context context, List<Appliance> appliances) {
        this.context = context;
        this.appliances = appliances;
    }

    @Override
    public ApplianceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.appliances_card, parent, false);
        return new ApplianceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplianceViewHolder holder, int position) {
        Appliance appliance = appliances.get(position);
        holder.titleTextView.setText(appliance.getTitle());
        holder.priceTextView.setText(appliance.getPrice());

        Glide.with(context)
                .load(appliance.getImageUrl())
                .override(200, 200)
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.imageView);

        holder.buyNowButton.setOnClickListener(v -> {
            String url = appliance.getUrl();
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
        return appliances.size();
    }

    public static class ApplianceViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, priceTextView;
        ImageView imageView;
        Button buyNowButton;

        public ApplianceViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.applianceTitle);
            priceTextView = itemView.findViewById(R.id.appliancePrice);
            imageView = itemView.findViewById(R.id.applianceImage);
            buyNowButton = itemView.findViewById(R.id.buyNowButton);
        }
    }
}

