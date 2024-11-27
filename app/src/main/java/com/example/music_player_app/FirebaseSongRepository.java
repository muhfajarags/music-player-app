package com.example.music_player_app;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FirebaseSongRepository {
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private SongDao songDao;

    public FirebaseSongRepository(Context context) {
        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(
                "https://celloo-pam-default-rtdb.asia-southeast1.firebasedatabase.app/"
        );
        databaseReference = firebaseDatabase.getReference("today_hits");

        // Initialize Firebase Storage
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // Initialize Room Database
        AppDatabase database = AppDatabase.getInstance(context);
        songDao = database.songDao();
    }

    // Create: Add multiple songs to Firebase and Room
    public void addSongs(List<Song> songs) {
        for (Song song : songs) {
            // Push to Firebase
            String key = databaseReference.push().getKey();
            databaseReference.child(key).setValue(song);
        }

        // Insert to Room
        songDao.insertSongs(songs);
    }

    // Read: Fetch songs from Firebase and update Room
    public void fetchSongsFromFirebase(SongAdapter adapter) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Song> songs = new ArrayList<>();
                for (DataSnapshot songSnapshot : snapshot.getChildren()) {
                    Song song = songSnapshot.getValue(Song.class);
                    songs.add(song);
                }

                // Update Room Database
                songDao.deleteAllSongs();
                songDao.insertSongs(songs);

                // Update RecyclerView
                adapter.updateSongs(songs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching songs", error.toException());
            }
        });
    }

    // Update: Update multiple songs in Firebase and Room
    public void updateSongs(List<Song> songs) {
        for (Song song : songs) {
            // Find the key for each song in Firebase
            databaseReference.orderByChild("title")
                    .equalTo(song.getTitle())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                childSnapshot.getRef().setValue(song);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Firebase", "Error updating song", error.toException());
                        }
                    });
        }

        // Update Room
        songDao.updateSongs(songs);
    }

    // Delete: Remove multiple songs from Firebase and Room
    public void deleteSongs(List<Song> songs) {
        for (Song song : songs) {
            // Find the key for each song in Firebase
            databaseReference.orderByChild("title")
                    .equalTo(song.getTitle())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                childSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Firebase", "Error deleting song", error.toException());
                        }
                    });
        }

        // Delete from Room
        songDao.deleteSongs(songs);
    }
}