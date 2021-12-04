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
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;
import com.example.fmmusic.View.Fragment.SongsPlayingFragment;

import java.util.List;

public class PLLSongAdapter extends RecyclerView.Adapter<PLLSongAdapter.PLLSongHolder> {
    public static List<PLLSong> pllSongList;

    public PLLSongAdapter(List<PLLSong> pllSongList) {
        this.pllSongList = pllSongList;
    }

    @NonNull
    @Override
    public PLLSongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suggested_song, parent, false);
        return new PLLSongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PLLSongHolder holder, int position) {
        final PLLSong pllSong = pllSongList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(pllSong.getSong().getThumbnail())
                .centerCrop()
                .into(holder.imgThumbTopRow);

        holder.tvNameTopRow.setText(pllSong.getSong().getName());
        holder.tvSingerTopRow.setText(pllSong.getSong().getSinger().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MusicPlayingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", holder.getAdapterPosition());
                bundle.putString("from", "PLLSongAdapter");
                bundle.putString("id", pllSong.getSong().getId());
                bundle.putString("name", pllSong.getSong().getName());
                bundle.putString("artist_names", pllSong.getSong().getSinger().getName());
                bundle.putString("performer", pllSong.getSong().getSinger().getName());
                bundle.putString("thumbnail", pllSong.getSong().getThumbnail());
                bundle.putString("duration", String.valueOf(pllSong.getSong().getDuration()));

                intent.putExtra("song_suggested", bundle);
                SongsPlayingFragment songsPlayingFragment = SongsPlayingFragment.newInstance();
                songsPlayingFragment.setArguments(bundle);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pllSongList.size();
    }

    public class PLLSongHolder extends RecyclerView.ViewHolder {
        private ImageView imgThumbTopRow;
        private TextView tvNameTopRow;
        private TextView tvSingerTopRow;

        public PLLSongHolder(@NonNull View itemView) {
            super(itemView);

            imgThumbTopRow = (ImageView) itemView.findViewById(R.id.imgThumbTopRow);
            tvNameTopRow = (TextView) itemView.findViewById(R.id.tvNameTopRow);
            tvSingerTopRow = (TextView) itemView.findViewById(R.id.tvSingerTopRow);

        }
    }
}
