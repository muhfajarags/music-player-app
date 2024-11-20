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

        Intent intent = getIntent();
        String songTitle = intent.getStringExtra("songTitle");
        String artistName = intent.getStringExtra("artistName");
        int songResourceId = intent.getIntExtra("songResourceId", 0);
        int coverResourceId = intent.getIntExtra("coverResourceId", 0);

        TextView songTitleView = findViewById(R.id.song_title);
        TextView artistNameView = findViewById(R.id.song_artist);
        ImageView coverImageView = findViewById(R.id.song_cover);
        playPauseButton = findViewById(R.id.pause_music_player);

        songTitleView.setText(songTitle);
        artistNameView.setText(artistName);
        coverImageView.setImageResource(coverResourceId);

        mediaPlayer = MediaPlayer.create(this, songResourceId);

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
        if (mediaPlayer != null) {
            mediaPlayer.release();
            Log.d(TAG, "MediaPlayer released");
        }
    }
}