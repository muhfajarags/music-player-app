package com.example.music_player_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {

    private EditText editTextUsername;
    private Button applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextUsername = findViewById(R.id.editTextText);
        applyButton = findViewById(R.id.applyButton);

        Intent intent = getIntent();
        String currentUsername = intent.getStringExtra("username");
        if (currentUsername != null) {
            editTextUsername.setText(currentUsername);
        }

        String username = getIntent().getStringExtra("username");

        TextView profileName = findViewById(R.id.profileName);
        if (username != null) {
            profileName.setText(username);
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = editTextUsername.getText().toString().trim();
                if (TextUtils.isEmpty(newUsername)) {
                    Toast.makeText(EditProfile.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    // Mengirim username baru kembali ke ProfileActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newUsername", newUsername);
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Kembali ke halaman profile
                }
            }
        });
    }
}
