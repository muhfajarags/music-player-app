package com.example.music_player_app;

public class MyPlaylistProfile {
    public String MyPlaylistName;
    public String MyPlaylistDate;
    public String MyPlaylistImg;

    public Boolean terpilih = false;

    public MyPlaylistProfile(String pn, String pd, String pi) {
        this.MyPlaylistName = pn;
        this.MyPlaylistDate = pd;
        this.MyPlaylistImg = pi;
    }
}