package com.example.music_player_app;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Song> songs = createSongList();
        adapter = new SongAdapter(songs, getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Song> createSongList() {
        List<Song> songs = new ArrayList<>();
        try {
            songs.add(new Song(R.drawable.img_song1, "Penjaga Hati", "Nadhif Basalamah"));
            songs.add(new Song(R.drawable.img_song2, "Rayuan Perem..", "Nadin Amizah"));
            songs.add(new Song(R.drawable.img_song3, "Tak Selalu M..", "Lyodra Margareta"));
            songs.add(new Song(R.drawable.img_song4, "Sampai Jadi D..", "Banda Neira"));
            songs.add(new Song(R.drawable.img_song5, "Turnaround", "Hans Zimmer"));
            songs.add(new Song(R.drawable.img_song6, "je te laisse..", "Patrick Watson"));
            songs.add(new Song(R.drawable.img_song7, "From The Start", "Laufey"));
            songs.add(new Song(R.drawable.img_song8, "Duvet", "BOA"));
        } catch (Resources.NotFoundException e) {
            Log.e("HomeFragment", "Error creating song list: " + e.getMessage());
        }
        return songs;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("HomeFragment", "View destroyed. RecyclerView adapter set to null.");
        recyclerView.setAdapter(null);
        adapter = null;
    }
}

