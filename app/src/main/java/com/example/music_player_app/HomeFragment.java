package com.example.music_player_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        ImageView profileButton = view.findViewById(R.id.ic_profile);
        if (profileButton != null) {
            profileButton.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    showErrorToast("Error opening Profile: " + e.getMessage());
                }
            });
        }

        LinearLayout song1Layout = view.findViewById(R.id.song1_layout);
        int coverResourceId = R.drawable.img_song1;
        if (song1Layout != null) {
            song1Layout.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
                    intent.putExtra("songTitle", "Penjaga Hati");
                    intent.putExtra("artistName", "NadifBasamalah");
                    intent.putExtra("songUrl", "https://firebasestorage.googleapis.com/v0/b/celloo-pam.appspot.com/o/penjaga_hati.mp3?alt=media&token=133c1b6b-93d4-4b33-9da7-2308b5a4b78a");
                    intent.putExtra("coverImageResource", coverResourceId);
                    startActivity(intent);
                } catch (Exception e) {
                    showErrorToast("Error opening Music Player: " + e.getMessage());
                }
            });
        }

        return view;
    }

    private void showErrorToast(String message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }
}