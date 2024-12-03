package com.example.music_player_app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private SongAdapterSearch songAdapterSearch;
    private List<SongSearch> songList = new ArrayList<>();

    private FirebaseDatabase database;
    private DatabaseReference songsRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.searching, container, false);

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance();
        songsRef = database.getReference("songs");

        // Initialize views
        searchEditText = view.findViewById(R.id.search_edit_text);
        recyclerView = view.findViewById(R.id.recyclerView);  // RecyclerView in searching.xml

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        songAdapterSearch = new SongAdapterSearch(songList);
        recyclerView.setAdapter(songAdapterSearch);

        // Add TextWatcher to search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Perform search when text changes
                String searchQuery = charSequence.toString();
                if (!searchQuery.isEmpty()) {
                    searchSongs(searchQuery);
                } else {
                    // Clear results when search query is empty
                    songList.clear();
                    songAdapterSearch.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }

    private void searchSongs(String searchQuery) {
        // Query Firebase Realtime Database for songs matching the search query (by title)
        Query query = songsRef.orderByChild("title").startAt(searchQuery).endAt(searchQuery + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                songList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SongSearch song = snapshot.getValue(SongSearch.class);
                    songList.add(song);
                }
                songAdapterSearch.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load songs.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
