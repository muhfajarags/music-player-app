package com.example.music_player_app;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URL;

public class MusicPlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ImageButton playPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);

        // Ambil data dari intent dengan pemeriksaan null safety
        Intent intent = getIntent();
        String songTitle = intent.getStringExtra("songTitle");
        String artistName = intent.getStringExtra("artistName");
        String songUrl = intent.getStringExtra("songUrl");
        String coverUrl = intent.getStringExtra("coverUrl");

        if (songTitle == null || songUrl == null || coverUrl == null) {
            Toast.makeText(this, "Invalid data passed to activity", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inisialisasi UI
        TextView songTitleView = findViewById(R.id.song_title);
        TextView artistNameView = findViewById(R.id.song_artist);
        ImageView coverImageView = findViewById(R.id.song_cover);
        playPauseButton = findViewById(R.id.pause_music_player);

        songTitleView.setText(songTitle);
        artistNameView.setText(artistName);

        Glide.with(this)
                .load(coverUrl)
                .error(R.drawable.error_image)
                .into(coverImageView);

        if (songUrl.isEmpty()) {
            Toast.makeText(this, "Invalid song URL", Toast.LENGTH_SHORT).show();
            return;
        }

        initializeMediaPlayer(songUrl);
    }

    private void initializeMediaPlayer(String songUrl) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        setupMediaListeners();

        try {
            prepareMediaPlayer(songUrl);
        } catch (IOException e) {
            playPauseButton.setEnabled(false);
            Toast.makeText(this, "Error initializing MediaPlayer.", Toast.LENGTH_SHORT).show();
        }
    }

    private void prepareMediaPlayer(String songUrl) throws IOException {
        mediaPlayer.setDataSource(songUrl);
        mediaPlayer.prepareAsync();
    }

    private void setupMediaListeners() {
        mediaPlayer.setOnPreparedListener(mp -> {
            playPauseButton.setEnabled(true);
            setupPlayPauseButton();
        });

        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.e("MediaPlayer", "Error occurred: " + what + ", extra: " + extra);
            Toast.makeText(this, "An error occurred during playback.", Toast.LENGTH_SHORT).show();
            return true;
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            playPauseButton.setImageResource(R.drawable.img_play);
        });
    }

    private void setupPlayPauseButton() {
        playPauseButton.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    pauseMusic();
                } else {
                    playMusic();
                }
            }
        });
    }

    private void playMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            playPauseButton.setImageResource(R.drawable.img_pause);
        }
    }

    private void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playPauseButton.setImageResource(R.drawable.img_play);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            pauseMusic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
