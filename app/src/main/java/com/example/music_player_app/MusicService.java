package com.example.music_player_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
    private static final String TAG = "MusicService";
    private static final String CHANNEL_ID = "MusicPlayerChannel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        String audioUrl = "https://firebasestorage.googleapis.com/v0/b/celloo-pam.appspot.com/o/penjaga_hati.mp3?alt=media&token=133c1b6b-93d4-4b33-9da7-2308b5a4b78a";
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true); // Musik berulang terus
        } catch (IOException e) {
            Log.e(TAG, "Error setting up MediaPlayer", e);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForegroundServiceWithNotification(); // Mulai foreground service dengan notifikasi
        mediaPlayer.start();
        Log.d(TAG, "Music started in background with notification");
        return START_STICKY; // Agar service tetap berjalan jika dihentikan oleh sistem
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            Log.d(TAG, "MusicService destroyed and MediaPlayer released");
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Tidak digunakan karena service ini tidak perlu berinteraksi dengan activity
    }

    private void startForegroundServiceWithNotification() {
        // Buat Notification Channel jika versi Android Oreo atau lebih tinggi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music Player Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        // Intent untuk kembali ke MusicPlayerActivity saat notifikasi diklik
        Intent notificationIntent = new Intent(this, MusicPlayerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Buat notifikasi
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Celloo Music Player")
                .setContentText("Celloo is playing")
                .setSmallIcon(R.drawable.ic_music)
                .setContentIntent(pendingIntent)
                .build();

        // Mulai service sebagai foreground dengan notifikasi
        startForeground(NOTIFICATION_ID, notification);
    }
}
