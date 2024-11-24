package com.example.plantezemobileapplication.view;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.view.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.fragment.app.Fragment;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if (savedInstanceState == null) {
            loadFragment(new EcoGaugeFragment());
        }

        TextView dateText = findViewById(R.id.date_text);
        dateText.setText(getCurrentDate());
        setWelcomeText();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment;
                if (item.getItemId() == R.id.ecoGauge) {
                    selectedFragment = new EcoGaugeFragment();
                } else if (item.getItemId() == R.id.ecoBalance) {
                    selectedFragment = new EcoGaugeFragment();
                } else if (item.getItemId() == R.id.ecoHub) {
                    selectedFragment = new EcoGaugeFragment();
                } else if (item.getItemId() == R.id.ecoTracker) {
                    selectedFragment = new EcoGaugeFragment();
                } else {
                    selectedFragment = null;
                }
                return loadFragment(selectedFragment);
            }
        });
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

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd");
        return dateFormat.format(date);
    }

    private void setWelcomeText() {
        FirebaseUser user = auth.getCurrentUser();
        TextView welcomeText = findViewById(R.id.welcome_user_text);

        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("fullName").getValue(String.class);
                if(name != null) {
                    welcomeText.setText(String.format("Welcome back, %s", name.split(" ")[0]));
                } else {
                    welcomeText.setText("Welcome back");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Log.w("MainActivity", "Failed to read value.", databaseError.toException());
            }
        });
    }
}