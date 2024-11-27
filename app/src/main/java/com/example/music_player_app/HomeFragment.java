package com.example.music_player_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;

public class HomeFragment extends Fragment implements SongAdapter.OnSongClickListener {
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private FirebaseSongRepository songRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);

        // Initialize Firebase Song Repository
        songRepository = new FirebaseSongRepository(getContext());

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        ));

        // Initialize adapter with empty list
        adapter = new SongAdapter(new ArrayList<>(), getContext(), this);
        recyclerView.setAdapter(adapter);

        // Fetch songs from Firebase
        fetchSongs();

        // Setup profile button
        ImageView profileButton = view.findViewById(R.id.ic_profile);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });

        return view;
    }

    // Method to fetch songs from Firebase
    private void fetchSongs() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(
                "https://celloo-pam-default-rtdb.asia-southeast1.firebasedatabase.app/"
        ).getReference("today_hits");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Song> songs = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Song song = childSnapshot.getValue(Song.class);
                    if (song != null) {
                        songs.add(song);
                    }
                }

                // Update adapter with fetched songs
                adapter.updateSongs(songs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching songs", error.toException());
                // Optionally show an error message to the user
            }
        });
    }

    @Override
    public void onSongClick(Song song) {
        // Navigate to Music Player Activity with song details
        Intent intent = new Intent(getContext(), MusicPlayerActivity.class);
        intent.putExtra("songTitle", song.getTitle());
        intent.putExtra("artistName", song.getArtist());
        intent.putExtra("songUrl", song.getSongUrl());
        intent.putExtra("coverUrl", song.getCoverUrl());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up resources
        recyclerView.setAdapter(null);
        adapter = null;
    }
}