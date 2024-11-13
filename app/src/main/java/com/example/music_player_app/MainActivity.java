package com.example.music_player_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            item -> {
                Intent intent = null;

                if (item.getItemId() == R.id.navigation_library) {
                    intent = new Intent(MainActivity.this, LibraryActivity.class);
                } else if (item.getItemId() == R.id.navigation_search) {
                    intent = new Intent(MainActivity.this, SearchActivity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                }

                return true;
            };
}