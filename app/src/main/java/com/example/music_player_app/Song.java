package com.example.music_player_app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "songs")
public class Song {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "artist")
    private String artist;

    @ColumnInfo(name = "cover_url")
    private String coverUrl;

    @ColumnInfo(name = "song_url")
    private String songUrl;

    // Constructors
    public Song(String title, String artist, String coverUrl, String songUrl) {
        this.title = title;
        this.artist = artist;
        this.coverUrl = coverUrl;
        this.songUrl = songUrl;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getCoverUrl() { return coverUrl; }
    public String getSongUrl() { return songUrl; }
}