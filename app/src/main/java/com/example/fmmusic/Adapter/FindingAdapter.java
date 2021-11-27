package com.example.fmmusic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;

import java.util.List;

public class FindingAdapter extends RecyclerView.Adapter<FindingAdapter.TopHolder> {
    private List<Song> songList;

    public FindingAdapter(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public TopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suggested_song,parent,false);
        return new TopHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopHolder holder, int position) {
        final Song song = songList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(song.getThumbnail())
                .centerCrop()
                .into(holder.imgThumbTopRow);
        holder.tvNameTopRow.setText(song.getName());
        holder.tvSingerTopRow.setText(song.getSinger().getName());

    }

    @Override
    public int getItemCount() {
        return songList.size();
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
