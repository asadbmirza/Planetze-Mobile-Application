package com.example.plantezemobileapplication.view.login;

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

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.LoginModel;
import com.example.plantezemobileapplication.presenter.LoginPresenter;
import com.example.plantezemobileapplication.view.MainActivity;
import com.example.plantezemobileapplication.view.registration.RegistrationActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private TextInputEditText emailText, passwordText;
    private Button logInBtn;
    private ProgressBar progressBar;
    private Intent intent;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginPresenter = new LoginPresenter(this, new LoginModel(FirebaseAuth.getInstance()));

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        logInBtn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress_bar);
        TextView forgotPass = findViewById(R.id.forgot_psw);
        TextView registerLink = findViewById(R.id.registerLink);

        logInBtn.setOnClickListener(v -> {
            String email = Objects.requireNonNull(emailText.getText()).toString();
            String password = Objects.requireNonNull(passwordText.getText()).toString();

            loginPresenter.loginUser(email, password);
        });

        forgotPass.setOnClickListener(v -> {
            intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        registerLink.setOnClickListener(v -> {
            intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loginPresenter.alreadyLoggedIn();
    }

    public void goToHomepage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProcessSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProcessFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        // Show a loading spinner or progress bar
        logInBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        // Hide the loading spinner or progress bar
        progressBar.setVisibility(View.GONE);
        logInBtn.setVisibility(View.VISIBLE);
    }
}