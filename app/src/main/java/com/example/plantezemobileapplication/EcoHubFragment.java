package com.example.plantezemobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EcoHubFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ecohub);

        findViewById(R.id.articlesButton).setOnClickListener(v -> {
            startActivity(new Intent(EcoHubFragment.this, ArticlesFragment.class));
        });
        findViewById(R.id.videosButton).setOnClickListener(v -> {
            startActivity(new Intent(EcoHubFragment.this, VideosFragment.class));
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

