package com.example.plantezemobileapplication;

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

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
    Button registerBtn;
    TextView loginLink;
    EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
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

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        registerBtn = findViewById(R.id.registerBtn);
        loginLink = findViewById(R.id.loginLink);

        registerBtn.setOnClickListener(v -> {
            registerUser();
        });

        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        registerBtn = findViewById(R.id.registerBtn);
        progressBar = findViewById(R.id.progressBar);
        fullNameEditText = findViewById(R.id.editTextFullName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);

        registerBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        fullNameEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
        confirmPasswordEditText.setEnabled(false);

        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirm = confirmPasswordEditText.getText().toString().trim();


        if(fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            resetUI(registerBtn, progressBar, fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText);
            return;
        }

        String passwordError = isValidPassword(password);
        if (passwordError != null) {
            Toast.makeText(this, passwordError, Toast.LENGTH_LONG).show();
            resetUI(registerBtn, progressBar, fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText);
            return;
        }

        if(!password.equals(confirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            resetUI(registerBtn, progressBar, fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        saveUserDetails(user, fullName, email);

                        Intent intent = new Intent(RegistrationActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw Objects.requireNonNull(task.getException());
                        } catch (FirebaseAuthUserCollisionException e) {
                            Toast.makeText(RegistrationActivity.this, "Email already in use. Please log in or use a different email.", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(RegistrationActivity.this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        resetUI(registerBtn, progressBar, fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText);
                    }
                });

    }

    private void resetUI(Button registerBtn, ProgressBar progressBar, EditText... editTexts) {
        registerBtn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        for (EditText editText : editTexts) {
            editText.setEnabled(true);
        }

    }

    private String isValidPassword(String password) {
        if (password.length() < 8) {
            return "Password must be at least 8 characters long.";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Password must contain at least one lowercase letter.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter.";
        }
        if (!password.matches(".*\\d.*")) {
            return "Password must contain at least one number.";
        }
        if (!password.matches(".*[@$!%*?&].*")) {
            return "Password must contain at least one special character (@$!%*?&).";
        }
        return null; // Null means the password is valid
    }

    private void saveUserDetails(FirebaseUser user, String fullName, String email) {
        String userId = user.getUid();
        User newUser = new User(fullName, email);
        db.collection("users")
                .document(userId)
                .set(newUser)
                .addOnSuccessListener(aVoid -> {
                    sendEmailVerification(user);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegistrationActivity.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                });
    }

    private void sendEmailVerification(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "Registration successful. Please check your email for verification link.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
