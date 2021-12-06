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
import com.example.fmmusic.Model.Songs.Playlist;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.Personal.PlayListScreenActivity;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<Playlist> playlistList;

    public PlaylistAdapter(List<Playlist> playlistList) {
        this.playlistList = playlistList;
    }


    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_playlist,parent,false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        final Playlist playlist = playlistList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(playlist.getImg())
                .centerCrop()
                .into(holder.imgRecyclerPlaylist);
        holder.tvtRecyclerPlaylist.setText(playlist.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PlayListScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("from",playlist.getText());
                intent.putExtra("song_suggested",bundle);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgRecyclerPlaylist;
        private TextView tvtRecyclerPlaylist;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);

            imgRecyclerPlaylist = (ImageView) itemView.findViewById(R.id.imgRecyclerPlaylist);
            tvtRecyclerPlaylist = (TextView) itemView.findViewById(R.id.tvtRecyclerPlaylist);

        }
    }
}
