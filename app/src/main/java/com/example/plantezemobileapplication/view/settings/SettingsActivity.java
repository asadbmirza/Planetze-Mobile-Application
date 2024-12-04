package com.example.plantezemobileapplication.view.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.presenter.SettingsPresenter;
import com.example.plantezemobileapplication.view.main.MainActivity;

public class SettingsActivity extends AppCompatActivity {

    Button cancelBtn, confirmBtn;
    ImageView profilePicture;
    SettingsPresenter presenter;
    Intent intent;
    ActivityResultLauncher<Intent> imagePickLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cancelBtn = findViewById(R.id.cancelProfileChanges);
        confirmBtn = findViewById(R.id.confirmProfileChanges);
        profilePicture = findViewById(R.id.profile_picture);

        cancelBtn.setOnClickListener(v -> {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        profilePicture.setOnClickListener(v -> {
        });

        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        intent = result.getData();
                    }
                });

    }
}