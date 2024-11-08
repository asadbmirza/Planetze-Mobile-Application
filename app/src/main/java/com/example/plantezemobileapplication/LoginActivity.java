package com.example.plantezemobileapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.presenter.LoginPresenter;
import com.example.plantezemobileapplication.view.LoginView;
import com.google.android.material.textfield.TextInputEditText;


import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginView {

    TextInputEditText emailText, passwordText;
    Button logInBtn;
    private LoginPresenter presenter;

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

        presenter = new LoginPresenter(this);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        logInBtn = findViewById(R.id.login_btn);

        logInBtn.setOnClickListener(v -> {
            String email = Objects.requireNonNull(emailText.getText()).toString();
            String password = Objects.requireNonNull(passwordText.getText()).toString();

            presenter.loginUser(email, password);
        });
    }


    @Override
    public void showLoginSuccess() {
        Toast.makeText(this, "Logged in.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginFailure() {
        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        // Show a loading spinner or progress bar
    }

    @Override
    public void hideLoading() {
        // Hide the loading spinner or progress bar
    }
}