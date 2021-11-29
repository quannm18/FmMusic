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
import com.example.fmmusic.Model.Songs.PlaylistSongs;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;
import com.example.fmmusic.View.Fragment.SongsPlayingFragment;

import java.util.List;

public class PlayListSongAdapter extends RecyclerView.Adapter<PlayListSongAdapter.PlaylistHolder> {
    private List<Song> songList;

    public PlayListSongAdapter(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public PlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.row_playlist_songs,parent,false);
        return new PlaylistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistHolder holder, int position) {
        Song song = songList.get(position);
        Glide.with(holder.itemView.getContext()).load(song.getThumbnail()).centerCrop().into(holder.imgThumbSongPLRow);
        holder.tvNameSongPLRow.setText(song.getName());
        holder.tvSingerSongPLRow.setText(song.getSinger().getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MusicPlayingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",holder.getAdapterPosition());
                bundle.putString("from","FindingAdapter");
                bundle.putString("id",song.getId());
                bundle.putString("name",song.getName());
                bundle.putString("artist_names",song.getSinger().getName());
                bundle.putString("performer",song.getSinger().getName());
                bundle.putString("thumbnail",song.getThumbnail());
                bundle.putString("duration",song.getDuration()+"");

                intent.putExtra("song_suggested",bundle);
                SongsPlayingFragment songsPlayingFragment = SongsPlayingFragment.newInstance();
                songsPlayingFragment.setArguments(bundle);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class PlaylistHolder extends RecyclerView.ViewHolder {
        private ImageView imgThumbSongPLRow;
        private TextView tvNameSongPLRow;
        private TextView tvSingerSongPLRow;

        public PlaylistHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbSongPLRow = (ImageView) itemView.findViewById(R.id.imgThumbSongPLRow);
            tvNameSongPLRow = (TextView) itemView.findViewById(R.id.tvNameSongPLRow);
            tvSingerSongPLRow = (TextView) itemView.findViewById(R.id.tvSingerSongPLRow);

        }
    }
}
