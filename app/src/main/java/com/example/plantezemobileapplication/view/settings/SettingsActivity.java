package com.example.plantezemobileapplication.view.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.User;
import com.example.plantezemobileapplication.model.SettingsModel;
import com.example.plantezemobileapplication.presenter.SettingsPresenter;
import com.example.plantezemobileapplication.view.login.ProcessView;
import com.example.plantezemobileapplication.view.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SettingsActivity extends AppCompatActivity implements SettingsView {

    Button cancelBtn, confirmBtn;
    ImageView profilePicture;
    SettingsPresenter presenter;
    Intent intent;
    AutoCompleteTextView dropdown;
    TextInputEditText fullName;
    TextView usersName;
    String [] countries;
    User user;
    String newCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cancelBtn = findViewById(R.id.cancelProfileChanges);
        confirmBtn = findViewById(R.id.confirmProfileChanges);
        profilePicture = findViewById(R.id.profile_picture);
        dropdown = findViewById(R.id.autoCompleteTextView);
        fullName = findViewById(R.id.fullNameText);
        usersName = findViewById(R.id.users_name);

        user = (User) getIntent().getSerializableExtra("user_key");
        presenter = new SettingsPresenter(this, new SettingsModel());
        countries = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, countries);
        dropdown.setAdapter(adapter);
        dropdown.setText(user.getCountry());
        fullName.setText(user.getFullName());
        usersName.setText(user.getFullName());

        dropdown.setOnItemClickListener((parent, view1, position, id) -> {
            newCountry = parent.getItemAtPosition(position).toString();
        });

        cancelBtn.setOnClickListener(v -> {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        confirmBtn.setOnClickListener(v -> {
            presenter.updateUserData(String.valueOf(fullName.getText()), newCountry);
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        profilePicture.setOnClickListener(v -> {
        });
    }

    @Override
    public void showProcessSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProcessFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}