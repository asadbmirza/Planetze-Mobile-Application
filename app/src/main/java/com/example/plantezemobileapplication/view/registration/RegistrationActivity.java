package com.example.plantezemobileapplication.view.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.view.login.LoginActivity;
import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.view.welcome.WelcomeActivity;
import com.example.plantezemobileapplication.presenter.RegistrationPresenter;

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.View {
    private RegistrationContract.Presenter presenter;
    private ProgressBar progressBar;
    private EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerBtn;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registration_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registrationPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        presenter = new RegistrationPresenter(this);
        progressBar = findViewById(R.id.progressBar);
        fullNameEditText = findViewById(R.id.editTextFullName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        registerBtn = findViewById(R.id.registerBtn);
        loginLink = findViewById(R.id.loginLink);

        registerBtn.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            presenter.registerUser(fullName, email, password, confirmPassword);
        });

        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });
    }

    @Override
    public void resetUI() {
        registerBtn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        fullNameEditText.setEnabled(true);
        emailEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
        confirmPasswordEditText.setEnabled(true);
    }

    public void hideUI() {
        registerBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        fullNameEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
        confirmPasswordEditText.setEnabled(false);
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        resetUI();
    }

    @Override
    public void navigateToWelcomePage() {
        startActivity(new Intent(RegistrationActivity.this, WelcomeActivity.class));
        finish();
    }


}
