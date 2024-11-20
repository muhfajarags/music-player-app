package com.example.music_player_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.appcheck.BuildConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songs;
    private final Context context;
    private final OnSongClickListener songClickListener;

    public interface OnSongClickListener {
        void onSongClick(Song song);
    }

    SongAdapter(List<Song> songs, Context context, OnSongClickListener songClickListener) {
        this.songs = songs;
        this.context = context;
        this.songClickListener = songClickListener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);
        String shortTitle = song.getTitle().length() > 12 ?
                song.getTitle().substring(0, 12) + "..." : song.getTitle();
        holder.titleTextView.setText(shortTitle);

        String shortSinger = song.getArtist().length() > 15 ?
                song.getArtist().substring(0, 15) + "..." : song.getArtist();
        holder.singerTextView.setText(shortSinger);

        holder.imageView.setImageResource(song.getCoverResourceId());
        holder.itemView.setOnClickListener(v -> songClickListener.onSongClick(song));
    }

    @Override
    public void onViewRecycled(@NonNull SongViewHolder holder) {
        super.onViewRecycled(holder);
        holder.clearImage();
    }

    @Override
    public int getItemCount() {
        return songs != null ? songs.size() : 0;
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {
        private final ShapeableImageView imageView;
        private final TextView titleTextView;
        private final TextView singerTextView;

        SongViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.songImage);
            titleTextView = itemView.findViewById(R.id.songTitle);
            singerTextView = itemView.findViewById(R.id.songSinger);
        }

        void clearImage() {
            imageView.setImageDrawable(null);
        }
    }

    public void updateSongs(List<Song> newSongs) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return songs.size();
            }

            @Override
            public int getNewListSize() {
                return newSongs.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                Song oldSong = songs.get(oldItemPosition);
                Song newSong = newSongs.get(newItemPosition);
                return oldSong.getId() == newSong.getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Song oldSong = songs.get(oldItemPosition);
                Song newSong = newSongs.get(newItemPosition);
                return oldSong.getTitle().equals(newSong.getTitle()) &&
                        oldSong.getArtist().equals(newSong.getArtist()) &&
                        oldSong.getCoverResourceId() == newSong.getCoverResourceId() &&
                        oldSong.getSongResourceId() == newSong.getSongResourceId();
            }
        });
        this.songs = new ArrayList<>(newSongs);
        diffResult.dispatchUpdatesTo(this);
    }
}