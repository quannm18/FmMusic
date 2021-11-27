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
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;

import java.util.List;

public class SingerHomeAdapter extends RecyclerView.Adapter<SingerHomeAdapter.SingerHomeHolder> {
    private List<Singer> singerList;

    public SingerHomeAdapter(List<Singer> singerList) {
        this.singerList = singerList;
    }

    @NonNull
    @Override
    public SingerHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_singer,parent,false);
        return new SingerHomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerHomeHolder holder, int position) {
        final Singer singer = singerList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(singer.getId())
                .centerCrop()
                .into(holder.imgSingerHomeRow);
        holder.tvSingerHomeRow.setText(singer.getName());
    }

    @Override
    public int getItemCount() {
        return singerList.size();
    }

    public class SingerHomeHolder extends RecyclerView.ViewHolder {
        private ImageView imgSingerHomeRow;
        private TextView tvSingerHomeRow;

        public SingerHomeHolder(@NonNull View itemView) {
            super(itemView);
            imgSingerHomeRow = (ImageView) itemView.findViewById(R.id.imgSingerHomeRow);
            tvSingerHomeRow = (TextView) itemView.findViewById(R.id.tvSingerHomeRow);

        }
    }
}
