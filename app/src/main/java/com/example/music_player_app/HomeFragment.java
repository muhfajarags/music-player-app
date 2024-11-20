package com.example.music_player_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;

public class HomeFragment extends Fragment implements SongAdapter.OnSongClickListener {
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private AppDatabase db;
    private SongDao songDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        db = AppDatabase.getInstance(getContext());
        songDao = db.songDao();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new SongAdapter(new ArrayList<>(), getContext(), this);
        recyclerView.setAdapter(adapter);

        initializeDataIfNeeded();
        loadSongs();

        ImageView profileButton = view.findViewById(R.id.ic_profile);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void initializeDataIfNeeded() {
        if (songDao.getAllSongs().isEmpty()) {
            Song[] initialSongs = {
                    new Song(
                            "Blinding Lights",
                            "The Weeknd",
                            R.drawable.blinding_lights,
                            R.raw.blinding_lights
                    ),
                    new Song(
                            "Levitating",
                            "Dua Lipa",
                            R.drawable.levitating,
                            R.raw.levitating
                    ),
                    new Song(
                            "Peaches",
                            "Justin Bieber ft. Daniel Caesar & Giveon",
                            R.drawable.peaches,
                            R.raw.peaches
                    ),
                    new Song(
                            "Good 4 U",
                            "Olivia Rodrigo",
                            R.drawable.good_4_u,
                            R.raw.good_4_u
                    ),
                    new Song(
                            "We Don't Talk Anymore",
                            "Charlie Puth",
                            R.drawable.we_dont_talk_anymore,
                            R.raw.we_dont_talk_anymore
                    ),
                    new Song(
                            "Soldier Poet King",
                            "The Oh Hellos",
                            R.drawable.soldier_poet_king,
                            R.raw.soldier_poet_king
                    )
            }
                    ;

            for (Song song : initialSongs) {
                songDao.insert(song);
            }
        }
    }

    private void loadSongs() {
        new Thread(() -> {
            List<Song> songs = songDao.getAllSongs();
            requireActivity().runOnUiThread(() -> adapter.updateSongs(songs));
        }).start();
    }

    @Override
    public void onSongClick(Song song) {
        Intent intent = new Intent(getContext(), MusicPlayerActivity.class);
        intent.putExtra("songTitle", song.getTitle());
        intent.putExtra("artistName", song.getArtist());
        intent.putExtra("songResourceId", song.getSongResourceId());
        intent.putExtra("coverResourceId", song.getCoverResourceId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
        adapter = null;
    }
}