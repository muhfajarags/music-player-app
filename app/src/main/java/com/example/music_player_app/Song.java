package com.example.music_player_app;

public class Song {
    private String title;
    private String artist;
    private String cover_url;

    public Song(String title, String artist, String cover_url) {
        this.title = title;
        this.artist = artist;
        this.cover_url = cover_url;
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
}