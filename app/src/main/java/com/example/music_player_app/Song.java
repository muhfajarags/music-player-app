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

    @ColumnInfo(name = "cover_resource_id")
    private int coverResourceId;

    @ColumnInfo(name = "song_resource_id")
    private int songResourceId;

    public Song(String title, String artist, int coverResourceId, int songResourceId) {
        this.title = title;
        this.artist = artist;
        this.coverResourceId = coverResourceId;
        this.songResourceId = songResourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public int getSongResourceId() {
        return songResourceId;
    }
}