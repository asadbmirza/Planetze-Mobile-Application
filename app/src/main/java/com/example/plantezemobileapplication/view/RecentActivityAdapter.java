package com.example.plantezemobileapplication.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.utils.ActivityLog;
import com.example.plantezemobileapplication.utils.Answer;
import com.example.plantezemobileapplication.utils.Question;

import java.util.ArrayList;
import java.util.List;

public class RecentActivityAdapter extends RecyclerView.Adapter<RecentActivityAdapter.RecentActivityViewHolder> {
    private List<ActivityLog> activityLogList;

    public RecentActivityAdapter(List<ActivityLog> activityLogList) {
        this.activityLogList = activityLogList;
    }

    @NonNull
    @Override
    public RecentActivityAdapter.RecentActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_activity_card, parent, false);
        return new RecentActivityAdapter.RecentActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentActivityAdapter.RecentActivityViewHolder holder, int position) {
        ActivityLog activityLog = activityLogList.get(position);

        holder.questionTitle.setText(activityLog.getQuestionTitle());
        holder.enteredValue.setText(String.valueOf(activityLog.getEnteredValue()));
        holder.selectedOption.setText(activityLog.getSelectedAnswer().toString());
    }

    @Override
    public int getItemCount() {
        return activityLogList.size();
    }

    public void setActivityLogList(List<ActivityLog> activityLogList) {
        this.activityLogList = activityLogList;
        notifyDataSetChanged();
    }


    public static class RecentActivityViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitle, enteredValue, selectedOption;

        public RecentActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.recent_activity_title);
            enteredValue = itemView.findViewById(R.id.recent_activity_entered_value);
            selectedOption = itemView.findViewById(R.id.recent_activity_selected_option);
        }
    }
}
