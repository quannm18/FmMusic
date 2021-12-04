package com.example.fmmusic.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fmmusic.Model.PLLSong;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;
import com.example.fmmusic.View.Fragment.SongsPlayingFragment;

import java.util.List;

public class PLLSongAdapter extends RecyclerView.Adapter<PLLSongAdapter.TopHolder> {
    private List<PLLSong> pllSongList;

    public PLLSongAdapter(List<PLLSong> pllSongList) {
        this.pllSongList = pllSongList;
    }

    @NonNull
    @Override
    public TopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suggested_song,parent,false);
        return new TopHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopHolder holder, int position) {
        final PLLSong pllSong = pllSongList.get(position);
        holder.tvNameTopRow.setText(pllSong.getSong().getName());
        holder.tvSingerTopRow.setText(pllSong.getSong().getSinger().getName());
        Glide.with(holder.itemView.getContext())
                .load(pllSong.getSong().getThumbnail())
                .centerCrop().into(holder.imgThumbTopRow);
        
    }

    @Override
    public int getItemCount() {
        return pllSongList.size();
    }

    public class TopHolder extends RecyclerView.ViewHolder {
        private ImageView imgThumbTopRow;
        private TextView tvNameTopRow;
        private TextView tvSingerTopRow;

        public TopHolder(@NonNull View itemView) {
            super(itemView);

            imgThumbTopRow = (ImageView) itemView.findViewById(R.id.imgThumbTopRow);
            tvNameTopRow = (TextView) itemView.findViewById(R.id.tvNameTopRow);
            tvSingerTopRow = (TextView) itemView.findViewById(R.id.tvSingerTopRow);

        }
    }
}
