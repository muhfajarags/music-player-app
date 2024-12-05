package com.example.music_player_app;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songs;
    private final Context context;
    private final OnSongClickListener songClickListener;
    private static final int MAX_TITLE_LENGTH = 12;
    private static final int MAX_ARTIST_LENGTH = 15;

    public SongAdapter(List<Song> songs, Context context, OnSongClickListener songClickListener) {
        this.songs = songs;
        this.context = context;
        this.songClickListener = songClickListener;
    }

    //method untuk membuat tampilan data lagu
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    //method untuk binding/ menghubungkan data ke tampilan
    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);

        String shortTitle = song.getTitle().length() > 12 ?
                song.getTitle().substring(0, MAX_TITLE_LENGTH) + "..." : song.getTitle();
        holder.titleTextView.setText(shortTitle);

        String shortSinger = song.getArtist().length() > 15 ?
                song.getArtist().substring(0, MAX_ARTIST_LENGTH) + "..." : song.getArtist();
        holder.singerTextView.setText(shortSinger);

        Glide.with(context)
                .load(song.getCoverUrl())
                .error(R.drawable.error_image) // Gambar error
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> songClickListener.onSongClick(song));
    }

    //method untuk menghitung jumlah data yang akan ditampilkan
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
    }

    //kelas untuk komparasi data lama dan data baru
    private static class SongDiffCallback extends DiffUtil.Callback {
        private final List<Song> oldList;
        private final List<Song> newList;

        public SongDiffCallback(List<Song> oldList, List<Song> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList != null ? oldList.size() : 0;
        }

        @Override
        public int getNewListSize() {
            return newList != null ? newList.size() : 0;
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            Song oldSong = oldList.get(oldItemPosition);
            Song newSong = newList.get(newItemPosition);
            return oldSong.getTitle().equals(newSong.getTitle()) &&
                    oldSong.getArtist().equals(newSong.getArtist());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Song oldSong = oldList.get(oldItemPosition);
            Song newSong = newList.get(newItemPosition);
            return oldSong.equals(newSong);
        }
    }

    //memperbarui data baru saja ke adapter, agar tidak perlu memuat ulang semua data dari awal
    public void updateSongs(List<Song> newSongs) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SongDiffCallback(songs, newSongs));
        this.songs = new ArrayList<>(newSongs);
        diffResult.dispatchUpdatesTo(this);
    }

}

