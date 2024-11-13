package com.example.plantezemobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.presenter.ForgotPasswordPresenter;
import com.example.plantezemobileapplication.presenter.LoginPresenter;
import com.example.plantezemobileapplication.view.ProcessView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity implements ProcessView {
    ForgotPasswordPresenter presenter;
    TextInputEditText target_email;
    TextView back_to_sign_in;
    ProgressBar progressBar;
    Button reset_pass_btn;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        presenter = new ForgotPasswordPresenter(this);


        target_email = findViewById(R.id.pass_reset_email);
        back_to_sign_in = findViewById(R.id.back_to_sign_in);
        progressBar = findViewById(R.id.progress_bar);
        reset_pass_btn = findViewById(R.id.reset_pass_btn);

        reset_pass_btn.setOnClickListener(v -> {
            String email = Objects.requireNonNull(target_email.getText()).toString();
            presenter.sendPasswordReset(email);
        });

        back_to_sign_in.setOnClickListener(v -> {
            intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void showProcessSuccess() {
        Toast.makeText(this, "Password reset sent to email.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProcessFailure() {
        Toast.makeText(this, "Password reset could not be sent.", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showLoading() {
        // Show a loading spinner or progress bar
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        // Hide the loading spinner or progress bar
        progressBar.setVisibility(View.GONE);
    }
}