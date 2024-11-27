package com.example.plantezemobileapplication.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.Emissions;
import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.MainModel;
import com.example.plantezemobileapplication.presenter.MainPresenter;
import com.example.plantezemobileapplication.view.CreditsFragment;
import com.example.plantezemobileapplication.view.EcoGaugeFragment;
import com.example.plantezemobileapplication.view.SettingsFragment;
import com.example.plantezemobileapplication.view.login.LoginActivity;
import com.example.plantezemobileapplication.view.welcome.WelcomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements MainView{

    MainPresenter presenter;
    DatabaseReference myRef;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.ecoGauge);

        myRef = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance();
        presenter = new MainPresenter(this, new MainModel(auth, myRef));
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        presenter.loadUserData();
        presenter.setCurrentDate();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = getFragmentForItem(item.getItemId());
            return loadFragment(selectedFragment);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Sets all navigation icons to inactive state
        bottomNavigationView.getMenu().setGroupCheckable(0, true, false);
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }

        if(item.getItemId() == R.id.log_out) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if(item.getItemId() == R.id.settings) {
            return loadFragment(new SettingsFragment());
        } else if(item.getItemId() == R.id.credits) {
            return loadFragment(new CreditsFragment());
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private Fragment getFragmentForItem(int itemId) {
        Fragment selectedFragment = null;

        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);

        if (itemId == R.id.ecoGauge) {
            selectedFragment = new EcoGaugeFragment();
        } else if (itemId == R.id.ecoBalance) {
            selectedFragment = new EcoGaugeFragment();
        } else if (itemId == R.id.ecoHub) {
            selectedFragment = new EcoGaugeFragment();
        } else if (itemId == R.id.ecoTracker) {
            selectedFragment = new EcoGaugeFragment();
        } else if (itemId == R.id.ecoAgent) {
            selectedFragment = new EcoGaugeFragment();
        }
        return selectedFragment;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void displayUserData(Emissions emissions) {
        EcoGaugeFragment fragment = new EcoGaugeFragment();
        Bundle args = new Bundle();
        args.putSerializable("emissions_key", emissions);
        fragment.setArguments(args);

        loadFragment(fragment);
    }

    @Override
    public void setWelcomeText(String text) {
        TextView welcomeText = findViewById(R.id.welcome_user_text);
        welcomeText.setText(text);
    }

    @Override
    public void setCurrentDate(String date) {
        TextView dateText = findViewById(R.id.date_text);
        dateText.setText(date);
    }
}