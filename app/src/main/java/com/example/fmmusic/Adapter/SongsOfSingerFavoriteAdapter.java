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
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;
import com.example.fmmusic.View.Fragment.SongsPlayingFragment;

import java.util.List;

public class SongsOfSingerFavoriteAdapter extends RecyclerView.Adapter<SongsOfSingerFavoriteAdapter.SongsOfSingerFavoriteHolder> {
    public static List<Song> songOfSinger;

    public SongsOfSingerFavoriteAdapter(List<Song> songOfSinger) {
        this.songOfSinger = songOfSinger;
    }

    @NonNull
    @Override
    public SongsOfSingerFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suggested_song,parent,false);
        return new SongsOfSingerFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsOfSingerFavoriteHolder holder, int position) {
        final Song song = songOfSinger.get(position);
        Glide.with(holder.itemView.getContext())
                .load(song.getThumbnail())
                .centerCrop()
                .into(holder.imgThumbTopRow);
        holder.tvNameTopRow.setText(song.getName());
        holder.tvSingerTopRow.setText(song.getSinger().getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MusicPlayingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",holder.getAdapterPosition());
                bundle.putString("from","SongsOfSingerFavoriteAdapter");
                bundle.putString("id",song.getId());
                bundle.putString("name",song.getName());
                bundle.putString("artist_names",song.getSinger().getName());
                bundle.putString("performer",song.getSinger().getName());
                bundle.putString("thumbnail",song.getThumbnail());
                bundle.putString("duration", String.valueOf(song.getDuration()));

                intent.putExtra("song_suggested",bundle);
                SongsPlayingFragment songsPlayingFragment = SongsPlayingFragment.newInstance();
                songsPlayingFragment.setArguments(bundle);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songOfSinger.size();
    }

    public class SongsOfSingerFavoriteHolder extends RecyclerView.ViewHolder {
        private ImageView imgThumbTopRow;
        private TextView tvNameTopRow;
        private TextView tvSingerTopRow;

        public SongsOfSingerFavoriteHolder(@NonNull View itemView) {
            super(itemView);

            imgThumbTopRow = (ImageView) itemView.findViewById(R.id.imgThumbTopRow);
            tvNameTopRow = (TextView) itemView.findViewById(R.id.tvNameTopRow);
            tvSingerTopRow = (TextView) itemView.findViewById(R.id.tvSingerTopRow);

        }
    }
}
