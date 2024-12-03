package com.example.plantezemobileapplication.view.registration;

public interface RegistrationContract {
    interface View {
        void resetUI();
        void hideUI();
        void showSuccessMessage(String message);
        void showErrorMessage(String message);
        void navigateToWelcomePage();
    }

    interface Presenter {
        void registerUser(String fullName, String email, String password, String confirmPassword);
    }
}