package com.example.music_player_app;

//interface ditaruh di class SongAdapter dikarenakan hanya digunakan pada mekasnisme recycle view yang dikelola adapter ini
interface OnSongClickListener {
    void onSongClick(Song song);
}
