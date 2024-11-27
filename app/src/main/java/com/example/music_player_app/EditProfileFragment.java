package com.example.music_player_app;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EditProfileFragment extends Fragment {

    private EditText editTextUsername;
    private Button applyButton;
    private OnProfileUpdateListener profileUpdateListener;

    // Interface untuk komunikasi dengan Activity
    public interface OnProfileUpdateListener {
        void onProfileUpdated(String newUsername);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            profileUpdateListener = (OnProfileUpdateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " harus mengimplementasikan OnProfileUpdateListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        editTextUsername = view.findViewById(R.id.editTextText);
        applyButton = view.findViewById(R.id.applyButton);
        ImageView backButton = view.findViewById(R.id.backButton);

        // Mengambil username dari arguments
        Bundle args = getArguments();
        if (args != null) {
            String currentUsername = args.getString("username", "");
            editTextUsername.setText(currentUsername);

            TextView profileName = view.findViewById(R.id.profileName);
            profileName.setText(currentUsername);
        }

        backButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        applyButton.setOnClickListener(v -> {
            String newUsername = editTextUsername.getText().toString().trim();
            if (TextUtils.isEmpty(newUsername)) {
                Toast.makeText(getContext(), "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                // Kirim username baru ke Activity
                profileUpdateListener.onProfileUpdated(newUsername);
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }
}
