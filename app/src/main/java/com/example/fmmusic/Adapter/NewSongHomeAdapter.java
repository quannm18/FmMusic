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
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;

import java.util.List;

public class NewSongHomeAdapter extends RecyclerView.Adapter<NewSongHomeAdapter.NewSongHomeHolder> {
    private List<Top> topList;

    public NewSongHomeAdapter(List<Top> topList) {
        this.topList = topList;
    }

    @NonNull
    @Override
    public NewSongHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_highlight,parent,false);
        return new NewSongHomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewSongHomeHolder holder, int position) {
        final Top top = topList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(top.getThumbnail())
                .centerCrop()
                .into(holder.imgHighlights);
        holder.tvHighlights.setText(top.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MusicPlayingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",holder.getAdapterPosition());
                bundle.putString("from","HighlightAdapter");
                bundle.putString("id",top.getId());
                bundle.putString("name",top.getName());
                bundle.putString("artist_names",top.getSinger().getName());
                bundle.putString("performer",top.getPerformer());
                bundle.putString("thumbnail",top.getThumbnail());
                bundle.putString("duration",top.getDuration()+"");

                intent.putExtra("song_suggested",bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topList.size();
    }

    public class NewSongHomeHolder extends RecyclerView.ViewHolder {
        private ImageView imgHighlights;
        private TextView tvHighlights;
        public NewSongHomeHolder(@NonNull View itemView) {
            super(itemView);
            imgHighlights = (ImageView) itemView.findViewById(R.id.imgHighlights);
            tvHighlights = (TextView) itemView.findViewById(R.id.tvHighlights);

        }
    }
}
