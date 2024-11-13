// ProfileActivity.java
package com.example.music_player_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements EditProfileFragment.OnProfileUpdateListener {

    private TextView profileName;
    private List<MyPlaylistProfile> collection;
    private MyPlaylistProfileCollection adapter;
    private RecyclerView rvCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Inisialisasi RecyclerView dan adapter (kode sama seperti sebelumnya)
        initializeRecyclerView();

        profileName = findViewById(R.id.profileName);
        String savedUsername = getSharedPreferences("ProfileData", MODE_PRIVATE)
                .getString("username", "Dianboo");
        profileName.setText(savedUsername);

        Button editProfileBtn = findViewById(R.id.editProfileBtn);
        ImageButton backButton = findViewById(R.id.arrow_back);

        editProfileBtn.setOnClickListener(v -> openEditProfileFragment());

        backButton.setOnClickListener(v -> finish());
    }

    private void openEditProfileFragment() {
        // Tampilkan container fragment
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);

        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString("username", profileName.getText().toString());
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onProfileUpdated(String newUsername) {
        profileName.setText(newUsername);
        getSharedPreferences("ProfileData", MODE_PRIVATE)
                .edit()
                .putString("username", newUsername)
                .apply();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            findViewById(R.id.fragment_container).setVisibility(View.GONE);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    private void initializeRecyclerView() {
        this.collection = new ArrayList<>();
        this.collection.add(new MyPlaylistProfile("OVT 24/7", "12 September 2024", "img_playlist1"));
        this.collection.add(new MyPlaylistProfile("Pingin Nilai A", "12 September 2024", "img_playlist2"));

        this.adapter = new MyPlaylistProfileCollection(this, this.collection);
        this.rvCollection = this.findViewById(R.id.myRecyclerView);
        this.rvCollection.setLayoutManager(new LinearLayoutManager(this));
        this.rvCollection.setAdapter(this.adapter);
    }
}