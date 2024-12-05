package com.example.music_player_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class MyPlaylistProfileCollection
        extends RecyclerView.Adapter {

    private final Context ctx;
    private final List<MyPlaylistProfile> collection;
    public MyPlaylistProfile playlistCalonDelete;

    public MyPlaylistProfileCollection(Context ctx, List<MyPlaylistProfile> collection) {
        this.ctx = ctx;
        this.collection = collection;
    }

    private class VH extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView tvMyPlaylistName;
        private final TextView tvMyPlaylistDate;
        private final ImageView ivMyPlaylist;
        private MyPlaylistProfile myPlaylistProfile;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.tvMyPlaylistName = itemView.findViewById(R.id.tvMyPlaylistName);
            this.tvMyPlaylistDate = itemView.findViewById(R.id.tvMyPlaylistDate);
            this.ivMyPlaylist = itemView.findViewById(R.id.ivMyPlaylist);

            itemView.setOnClickListener(this);
        }

        private void setMyPlaylistProfile(MyPlaylistProfile mp) {
            this.myPlaylistProfile = mp;
            if (this.myPlaylistProfile.terpilih)
                this.itemView.setBackgroundResource(R.drawable.bg_selected);
            else
                this.itemView.setBackgroundResource(R.drawable.bg_unselected);
        }

        @Override
        public void onClick(View v) {
            for (MyPlaylistProfile mp : collection)
                mp.terpilih = false;

            this.myPlaylistProfile.terpilih = !this.myPlaylistProfile.terpilih;
            playlistCalonDelete = this.myPlaylistProfile.terpilih ? this.myPlaylistProfile : null;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.ctx)
                .inflate(R.layout.item_myplaylist, parent, false);
        VH vh = new VH(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyPlaylistProfile mp = this.collection.get(position);
        VH vh = (VH) holder;
        vh.tvMyPlaylistName.setText(mp.MyPlaylistName);
        vh.tvMyPlaylistDate.setText(mp.MyPlaylistDate);
        vh.ivMyPlaylist.setImageResource(ctx.getResources().getIdentifier(mp.MyPlaylistImg, "drawable", ctx.getPackageName()));
    }


    @Override
    public int getItemCount() {
        return this.collection.size();
    }
}