package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.Emissions;
import com.example.plantezemobileapplication.User;
import com.example.plantezemobileapplication.view.registration.RegistrationContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationPresenter implements RegistrationContract.Presenter {
    private final RegistrationContract.View view;
    private final FirebaseAuth mAuth;
    private final DatabaseReference dbRef;

    public RegistrationPresenter(RegistrationContract.View view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    public void registerUser(String fullName, String email, String password, String confirmPassword) {
        view.hideUI();
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            view.showErrorMessage("Please fill all the fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            view.showErrorMessage("Passwords do not match");
            return;
        }

        String passwordError = isValidPassword(password);
        if (passwordError != null) {
            view.showErrorMessage(passwordError);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        saveUserDetails(user, fullName, email);
                        view.navigateToWelcomePage();
                    } else {
                        view.showErrorMessage("Registration Failed: " + task.getException().getMessage());
                    }
                    view.resetUI();
                });
    }

    private String isValidPassword(String password) {
        if (password.length() < 8) return "Password must be at least 8 characters long.";
        if (!password.matches(".*[a-z].*")) return "Password must contain at least one lowercase letter.";
        if (!password.matches(".*[A-Z].*")) return "Password must contain at least one uppercase letter.";
        if (!password.matches(".*\\d.*")) return "Password must contain at least one number.";
        if (!password.matches(".*[@$!%*?&].*")) return "Password must contain at least one special character (@$!%*?&).";
        return null;
    }

    private void saveUserDetails(FirebaseUser user, String fullName, String email) {
        String userId = user.getUid();
        User newUser = new User(fullName, email, new Emissions());
        // TODO: possibly broken, may need to reconsider how to handle newUser with newEmissions

        dbRef.child(userId).setValue(newUser)
                .addOnSuccessListener(aVoid -> sendEmailVerification(user))
                .addOnFailureListener(e -> view.showErrorMessage("Failed to save user data"));
    }

    private void sendEmailVerification(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        view.showSuccessMessage("Please check your email for verification link.");
                    } else {
                        view.showErrorMessage("Failed to send verification email");
                    }
                });
    }

}
