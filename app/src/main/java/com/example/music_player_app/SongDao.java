package com.example.music_player_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM songs")
    List<Song> getAllSongs();

    @Insert
    void insert(Song song);

    @Update
    void update(Song song);

    @Delete
    void deleteSong(Song song);

    @Query("DELETE FROM songs")
    void deleteAllSongs();
}