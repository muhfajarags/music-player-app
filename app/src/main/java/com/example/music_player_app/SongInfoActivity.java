package com.example.music_player_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongInfoActivity extends AppCompatActivity {

    private static final String TAG = "SongInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_info);

        // Mendapatkan ID lagu dari Intent
        int songId = getIntent().getIntExtra("songId", 1);
        Log.d(TAG, "Received songId: " + songId);

        // Inisialisasi komponen UI
        TextView writtenByTextView = findViewById(R.id.written_by);
        TextView producedByTextView = findViewById(R.id.produced_by);
        TextView songTitleTextView = findViewById(R.id.song_title);

        // Membuat thread baru untuk menjalankan pemanggilan API
        Thread apiThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Pemanggilan API dengan Retrofit
                ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                Call<SongInfo> call = apiService.getSongInfo(songId);

                // Menjalankan request dan menangani response
                call.enqueue(new Callback<SongInfo>() {
                    @Override
                    public void onResponse(Call<SongInfo> call, Response<SongInfo> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            SongInfo songInfo = response.body();
                            Log.d(TAG, "Response body: " + songInfo.getSongTitle());

                            // Pastikan pembaruan UI dilakukan di thread utama
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Mengupdate UI dengan data lagu
                                    writtenByTextView.setText(songInfo.getWrittenBy());
                                    producedByTextView.setText(songInfo.getProducedBy());
                                    songTitleTextView.setText(songInfo.getSongTitle());
                                }
                            });
                        } else {
                            Log.e(TAG, "Failed to get data");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SongInfoActivity.this, "Failed to load song info", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<SongInfo> call, Throwable t) {
                        Log.e(TAG, "Error: " + t.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SongInfoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        // Menjalankan thread
        apiThread.start();
    }
}