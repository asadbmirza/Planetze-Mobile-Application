package com.example.plantezemobileapplication.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.EcoMonitorModel;
import com.example.plantezemobileapplication.utils.ActivityLog;
import com.example.plantezemobileapplication.utils.Answer;
import com.example.plantezemobileapplication.utils.Question;

import java.util.ArrayList;
import java.util.List;

public class RecentActivityAdapter extends RecyclerView.Adapter<RecentActivityAdapter.RecentActivityViewHolder> {
    private List<ActivityLog> activityLogList;
    private Context context;
    private String day;
    private String week;
    private String month;

    public RecentActivityAdapter(List<ActivityLog> activityLogList, String day, String week, String month) {
        this.activityLogList = activityLogList;
        this.day = day;
        this.week = week;
        this.month = month;
    }

    @NonNull
    @Override
    public RecentActivityAdapter.RecentActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recent_activity_card, parent, false);
        return new RecentActivityAdapter.RecentActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentActivityAdapter.RecentActivityViewHolder holder, int position) {
        ActivityLog activityLog = activityLogList.get(position);

        holder.questionTitle.setText(activityLog.getQuestionTitle());
        holder.enteredValue.setText(String.valueOf(activityLog.getEnteredValue()));
        if (activityLog.getSelectedAnswer() != null && !activityLog.getSelectedAnswer().getAnswerText().contains("!")) {
            holder.selectedOption.setText(activityLog.getSelectedAnswer().toString());
        }
        holder.editActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditActivityDialog(activityLog);
            }
        });

        holder.deleteActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteActivityDialog(activityLog);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityLogList.size();
    }

    public void setActivityLogList(List<ActivityLog> activityLogList) {
        this.activityLogList = activityLogList;
        notifyDataSetChanged();
    }

    private void showEditActivityDialog(ActivityLog activityLog) {
        int previousInput = activityLog.getEnteredValue();
        String questionName = activityLog.getQuestionTitle();

        final EditText input = new EditText(context);
        input.setHint(String.valueOf(previousInput));
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit '" + questionName + "'");
        builder.setView(input);
        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EcoMonitorModel model = new EcoMonitorModel();
                int userInput = Integer.parseInt(input.getText().toString());
                Toast.makeText(context, "You entered: " + userInput, Toast.LENGTH_SHORT).show();
                model.updateActivityLog(activityLog, userInput, day, week, month);
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show the dialog
        builder.show();
    }

    private void showDeleteActivityDialog(ActivityLog activityLog) {
        String questionName = activityLog.getQuestionTitle();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete '" + questionName + "'?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EcoMonitorModel model = new EcoMonitorModel();
                System.out.println(activityLog.getId());
                model.deleteActivityLog(activityLog, day, week, month);
                Toast.makeText(context, "DELETED", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show the dialog
        builder.show();
    }


    public static class RecentActivityViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitle, enteredValue, selectedOption;
        Button editActivityBtn, deleteActivityBtn;

        public RecentActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.recent_activity_title);
            enteredValue = itemView.findViewById(R.id.recent_activity_entered_value);
            selectedOption = itemView.findViewById(R.id.recent_activity_selected_option);
            editActivityBtn = itemView.findViewById(R.id.edit_activity_btn);
            deleteActivityBtn = itemView.findViewById(R.id.delete_activity_btn);
        }
    }
}
