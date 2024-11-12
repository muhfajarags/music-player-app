package com.example.music_player_app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
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

        playPauseButton = findViewById(R.id.pause_music_player);
        mediaPlayer = new MediaPlayer();

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseMusic();
                } else {
                    playMusic();
                }
                Log.d(TAG, "Play/Pause button clicked. isPlaying: " + isPlaying);
            }
        });
        ImageButton backButton = findViewById(R.id.arrow_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void playMusic() {
        try {
            if (!isPlaying) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    String audioUrl = "https://firebasestorage.googleapis.com/v0/b/celloo-pam.appspot.com/o/penjaga_hati.mp3?alt=media&token=133c1b6b-93d4-4b33-9da7-2308b5a4b78a";
                    mediaPlayer.setDataSource(audioUrl);
                    mediaPlayer.prepare();
                }
                mediaPlayer.start();
                playPauseButton.setImageResource(R.drawable.img_pause);  // Ganti gambar tombol menjadi ikon pause
                isPlaying = true;
                Log.d(TAG, "Music started playing");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error playing music", e);
            e.printStackTrace();
        }
    }

    private void pauseMusic() {
        if (isPlaying && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playPauseButton.setImageResource(R.drawable.img_play);  // Ganti gambar tombol menjadi ikon play
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



