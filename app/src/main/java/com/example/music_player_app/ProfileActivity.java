package com.example.music_player_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST = 1;
    private TextView profileName;

    private List<MyPlaylistProfile> collection;
    private MyPlaylistProfileCollection adapter;
    private RecyclerView rvCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        this.collection = new ArrayList<MyPlaylistProfile>();
        this.collection.add(new MyPlaylistProfile("OVT 24/7", "12 September 2024", "img_playlist1"));
        this.collection.add(new MyPlaylistProfile("Pingin Nilai A", "12 September 2024", "img_playlist2"));

        this.adapter =
                new MyPlaylistProfileCollection(this, this.collection);

        this.rvCollection = this.findViewById(R.id.myRecyclerView);
        this.rvCollection.setLayoutManager(new LinearLayoutManager(this));
        this.rvCollection.setAdapter(this.adapter);

        profileName = findViewById(R.id.profileName);

        String savedUsername = getSharedPreferences("ProfileData", MODE_PRIVATE)
                .getString("username", "Dianboo");
        profileName.setText(savedUsername);

        Button editProfileBtn = findViewById(R.id.editProfileBtn);
        ImageButton backButton = findViewById(R.id.arrow_back);

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kirim nama terbaru saat membuka halaman EditProfile
                Intent intent = new Intent(ProfileActivity.this, EditProfile.class);
                intent.putExtra("username", profileName.getText().toString());
                startActivityForResult(intent, EDIT_PROFILE_REQUEST);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK && data != null) {
            String newUsername = data.getStringExtra("newUsername");
            if (newUsername != null) {
                profileName.setText(newUsername);

                // Simpan nama ke SharedPreferences
                getSharedPreferences("ProfileData", MODE_PRIVATE)
                        .edit()
                        .putString("username", newUsername)
                        .apply();
            }
        }
    }
}