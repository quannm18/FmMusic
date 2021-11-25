package com.example.fmmusic.Adapter;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fmmusic.Model.Songs.AudioModel;
import com.example.fmmusic.R;

import java.util.List;

public class SongsLibAdapter extends RecyclerView.Adapter<SongsLibAdapter.SongsLibViewHolder> {
    private List<AudioModel> audioModels;

    public SongsLibAdapter(List<AudioModel> audioModels) {
        this.audioModels = audioModels;
    }

    @NonNull
    @Override
    public SongsLibAdapter.SongsLibViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_song_lib, parent, false);
        return new SongsLibViewHolder(view);
    }

    private MediaPlayer mediaPlayer;
    private Handler handler;

    @Override
    public void onBindViewHolder(@NonNull SongsLibAdapter.SongsLibViewHolder holder, int position) {
        final AudioModel audioModel = audioModels.get(position);
        holder.tvRowSongLib.setText(audioModel.getName());
        holder.tvRowArtistSongLib.setText(audioModel.getArtist());
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(audioModel
                        .getImgPath()))
                .centerCrop()
                .into(holder.imgRowSongLib);
    }

    @Override
    public int getItemCount() {
        return audioModels.size();
    }

    public class SongsLibViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgRowSongLib;
        private TextView tvRowSongLib;
        private TextView tvRowArtistSongLib;

        public SongsLibViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRowSongLib = (ImageView) itemView.findViewById(R.id.imgThumbSongLibRow);
            tvRowSongLib = (TextView) itemView.findViewById(R.id.tvNameSongLibRow);
            tvRowArtistSongLib = (TextView) itemView.findViewById(R.id.tvSingerSongLibRow);


        }
    }
}
