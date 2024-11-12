package com.example.plantezemobileapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(v -> {
            registerUser();
        });
    }

    private void registerUser() {
        String fullName = ((EditText) findViewById(R.id.editTextFullName)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
        String confirm = ((EditText) findViewById(R.id.editTextConfirmPassword)).getText().toString();

        if(fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        saveUserDetails(user, fullName, email);
                    } else {
                        try {
                            throw Objects.requireNonNull(task.getException());
                        } catch (FirebaseAuthUserCollisionException e) {
                            Toast.makeText(RegistrationActivity.this, "Email already in use. Please log in or use a different email.", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(RegistrationActivity.this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
