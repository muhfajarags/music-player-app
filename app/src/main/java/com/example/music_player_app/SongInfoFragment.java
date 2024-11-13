package com.example.music_player_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongInfoFragment extends Fragment {

    private static final String TAG = "SongInfoFragment";
    private TextView writtenByTextView;
    private TextView producedByTextView;
    private TextView songTitleTextView;

    // Variabel untuk menyimpan ID lagu
    private int songId;

    public SongInfoFragment() {
        // Required empty public constructor
    }

    // Method untuk membuat instance Fragment dengan songId
    public static SongInfoFragment newInstance(int songId) {
        SongInfoFragment fragment = new SongInfoFragment();
        Bundle args = new Bundle();
        args.putInt("songId", songId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mendapatkan ID lagu dari arguments
        if (getArguments() != null) {
            songId = getArguments().getInt("songId", 1);
        }
        Log.d(TAG, "Received songId: " + songId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout untuk fragment
        View view = inflater.inflate(R.layout.fragment_song_info, container, false);

        // Inisialisasi komponen UI
        writtenByTextView = view.findViewById(R.id.written_by);
        producedByTextView = view.findViewById(R.id.produced_by);
        songTitleTextView = view.findViewById(R.id.song_title);

        // Panggil API untuk mendapatkan info lagu
        fetchSongInfo();

        return view;
    }

    private void fetchSongInfo() {
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

                    // Mengupdate UI dengan data lagu
                    writtenByTextView.setText(songInfo.getWrittenBy());
                    producedByTextView.setText(songInfo.getProducedBy());
                    songTitleTextView.setText(songInfo.getSongTitle());
                } else {
                    Log.e(TAG, "Failed to get data");
                    Toast.makeText(requireContext(), "Failed to load song info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SongInfo> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
