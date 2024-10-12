package com.example.music_player_app;

public class Song {
    int imageResource;
    String title;
    String singer;

    Song(int imageResource, String title, String singer) {
        this.imageResource = imageResource;
        this.title = title;
        this.singer = singer;
    }
}