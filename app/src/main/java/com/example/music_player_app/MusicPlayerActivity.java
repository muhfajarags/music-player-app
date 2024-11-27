package com.example.music_player_app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ImageButton playPauseButton;
    private boolean isPlaying = false;
    private static final String TAG = "MusicPlayerApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);

        // Retrieve song details from intent
        Intent intent = getIntent();
        String songTitle = intent.getStringExtra("songTitle");
        String artistName = intent.getStringExtra("artistName");
        String songUrl = intent.getStringExtra("songUrl");
        String coverUrl = intent.getStringExtra("coverUrl");

        // Find UI elements
        TextView songTitleView = findViewById(R.id.song_title);
        TextView artistNameView = findViewById(R.id.song_artist);
        ImageView coverImageView = findViewById(R.id.song_cover);
        playPauseButton = findViewById(R.id.pause_music_player);

        // Set song details
        songTitleView.setText(songTitle);
        artistNameView.setText(artistName);

        // Load cover image
        Glide.with(this)
                .load(coverUrl)
                .into(coverImageView);

        // Initialize MediaPlayer with song URL
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(songUrl);
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Error setting up MediaPlayer", e);
        }

        // Setup play/pause button
        playPauseButton.setOnClickListener(v -> {
            if (isPlaying) {
                pauseMusic();
            } else {
                playMusic();
            }
        });
    }

    private void playMusic() {
        if (!isPlaying) {
            mediaPlayer.start();
            playPauseButton.setImageResource(R.drawable.img_pause);
            isPlaying = true;
            Log.d(TAG, "Music started playing");
        }
    }

    private void pauseMusic() {
        if (isPlaying && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playPauseButton.setImageResource(R.drawable.img_play);
            isPlaying = false;
            Log.d(TAG, "Music paused");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            Log.d(TAG, "MediaPlayer released");
        }
    }
}