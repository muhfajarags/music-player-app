package com.example.music_player_app;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText etRegUsername, etRegPassword;
    private Button btnRegister;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegUsername = findViewById(R.id.etRegUsername);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);

        db = AppDatabase.getInstance(this);

        btnRegister.setOnClickListener(view -> {
            String username = etRegUsername.getText().toString();
            String password = etRegPassword.getText().toString();

            new Thread(() -> {
                if (db.userDao().checkUsername(username) == 0) {
                    db.userDao().insertUser(new User(username, password));
                    runOnUiThread(() -> Toast.makeText(this, "User Registered!", Toast.LENGTH_SHORT).show());
                    finish();
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });
    }
}
