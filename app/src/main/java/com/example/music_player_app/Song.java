package com.example.music_player_app;
public class Song {
    private String title;
    private String artist;
    private String coverUrl;
    private String songUrl;

    public Song() {}

    public Song(String title, String artist, String coverUrl, String songUrl) {
        this.title = title;
        this.artist = artist;
        this.coverUrl = coverUrl;
        this.songUrl = songUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}

