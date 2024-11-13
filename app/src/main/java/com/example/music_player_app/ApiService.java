package com.example.music_player_app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // Endpoint untuk mendapatkan informasi lagu berdasarkan ID
    @GET("index.php")
    Call<SongInfo> getSongInfo(@Query("id") int songId);
}
