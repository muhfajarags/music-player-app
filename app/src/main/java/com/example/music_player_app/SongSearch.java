package com.example.music_player_app;

public class SongSearch {
    private String title;
    private String artist;
    private String url;

    // Default constructor for Firebase
    public SongSearch() {}

    // Constructor
    public SongSearch(String title, String artist, String url) {
        this.title = title;
        this.artist = artist;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getUrl() {
        return url;
    }
}
