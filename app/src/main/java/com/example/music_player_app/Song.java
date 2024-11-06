package com.example.music_player_app;

public class Song {
    private String title;
    private String artist;
    private String cover_url;
    private String song_url; // Add this field for song URL

    public Song(String title, String artist, String cover_url, String song_url) {
        this.title = title;
        this.artist = artist;
        this.cover_url = cover_url;
        this.song_url = song_url; // Initialize the song URL
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getCoverUrl() {
        return cover_url;
    }

    public String getSongUrl() {
        return song_url; // Getter for song URL
    }
}