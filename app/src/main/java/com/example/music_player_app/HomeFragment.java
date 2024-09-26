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
        if (song1Layout != null) {
            song1Layout.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
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