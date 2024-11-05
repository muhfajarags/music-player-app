package com.example.music_player_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new SongAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(adapter);

        fetchTrendingSongs();

        return view;
    }

    private void fetchTrendingSongs() {
        String url = "https://music-player-app-7fe7e-default-rtdb.asia-southeast1.firebasedatabase.app/today_hits.json"; // Ganti dengan URL Firebase Anda

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Parsing JSON Array dengan GSON
                        Gson gson = new Gson();
                        Song[] songs = gson.fromJson(response.toString(), Song[].class);
                        adapter.updateSongs(Arrays.asList(songs));
                    } catch (Exception e) {
                        Log.e("HomeFragment", "Error parsing JSON: " + e.getMessage());
                        Toast.makeText(getContext(), "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Tangani kesalahan
                    Log.e("HomeFragment", "Error fetching data", error);
                    Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                }
        );

        // Tambahkan request ke queue Volley
        Volley.newRequestQueue(getContext()).add(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("HomeFragment", "View destroyed. RecyclerView adapter set to null.");
        recyclerView.setAdapter(null);
        adapter = null;
    }
}