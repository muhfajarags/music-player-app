package com.example.music_player_app;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment implements OnSongClickListener {
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private FirebaseDatabase db;
    private DatabaseReference todayHitsDB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new SongAdapter(new ArrayList<>(), getContext(), this); //memberi tahu adapter tentang interface SongAdapter.OnSongClickListener
        recyclerView.setAdapter(adapter);

        db = FirebaseDatabase.getInstance();
        todayHitsDB = db.getReference("today_hits");

        loadSongsFromFirebase();

        //use case profile
        ImageView profileButton = view.findViewById(R.id.ic_profile);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void loadSongsFromFirebase() {
        todayHitsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Song> songs = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    // Asumsikan model data Song sesuai dengan data di Firebase
                    Song song = childSnapshot.getValue(Song.class);
                    if (song != null) {
                        Log.d("HomeFragment", "Song loaded: " + song.getTitle());
                        songs.add(song);
                    } else {
                        Log.e("HomeFragment", "Failed to parse song from snapshot");
                    }

                }
                adapter.updateSongs(songs);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onSongClick(Song song) {
        Intent intent = new Intent(getContext(), MusicPlayerActivity.class);
        intent.putExtra("songTitle", song.getTitle());
        intent.putExtra("artistName", song.getArtist());
        intent.putExtra("songUrl", song.getSongUrl());
        intent.putExtra("coverUrl", song.getCoverUrl());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() { //untuk menghindari memory leak
        super.onDestroyView();
        recyclerView.setAdapter(null);
        adapter = null;
    }
}
