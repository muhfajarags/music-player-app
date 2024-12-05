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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements EditProfileFragment.OnProfileUpdateListener {

    private TextView profileName;
    private List<MyPlaylistProfile> collection;
    private MyPlaylistProfileCollection adapter;
    private RecyclerView rvCollection;

    private DatabaseReference databaseReference;
    private String userId = "UserID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Inisialisasi Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        // Inisialisasi RecyclerView dan adapter
        initializeRecyclerView();

        profileName = findViewById(R.id.profileName);

        // Ambil data username dari Firebase
        loadUsernameFromDatabase();

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

        // Simpan username baru ke Firebase
        databaseReference.child("username").setValue(newUsername);
    }

    private void loadUsernameFromDatabase() {
        databaseReference.child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String username = snapshot.getValue(String.class);
                if (username != null) {
                    profileName.setText(username);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
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
