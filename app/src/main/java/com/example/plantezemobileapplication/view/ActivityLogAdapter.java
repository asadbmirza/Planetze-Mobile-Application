package com.example.plantezemobileapplication.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.plantezemobileapplication.utils.Answer;
import com.example.plantezemobileapplication.utils.Question;

import java.util.ArrayList;
import java.util.List;

public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.ActivityViewHolder> {
    private List<Question> questionList;
    private List<Integer> enteredAmounts;

    public ActivityLogAdapter(List<Question> questions) {
        this.questionList = questions;
        enteredAmounts = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            enteredAmounts.add(0);
        }
    }

    @NonNull
    @Override
    public ActivityLogAdapter.ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_log_layout, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityLogAdapter.ActivityViewHolder holder, int position) {
        Question question = questionList.get(position);

        String questionText = question.getTitle().substring(0, question.getTitle().indexOf('$'));
        String questionHint = question.getTitle().substring(question.getTitle().indexOf('$') + 1);
        holder.questionTitle.setText(questionText);
        holder.editText.setHint(questionHint);

        // Set up edit text
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString().strip();
                if (input.isEmpty()) {
                    enteredAmounts.set(holder.getAdapterPosition(), 0);
                }
                else {
                    enteredAmounts.set(holder.getAdapterPosition(), Integer.parseInt(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Set up spinner
        ArrayAdapter<Answer> adapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, question.getAnswers());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.optionSpinner.setAdapter(adapter);
        if (question.getSelectedAnswer() >= 0) {
            holder.optionSpinner.setSelection(question.getSelectedAnswer());
        }
        holder.optionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question.setSelectedAnswer(parent.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // If there are no answers or there is an attributed factor to the answer but we do not want the user to see said option
        if (question.getAnswers().length == 0 || question.getAnswers()[0].getAnswerText().contains("!")) {
            holder.optionSpinner.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public List<Integer> getEnteredAmounts() {
        return enteredAmounts;
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitle;
        EditText editText;
        Spinner optionSpinner;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.question_title);
            editText = itemView.findViewById(R.id.edit_amount);
            optionSpinner = itemView.findViewById(R.id.option_spinner);
        }
    }
}
