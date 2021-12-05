package com.example.fmmusic.Adapter;

import android.content.ContentUris;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fmmusic.Model.Songs.AudioModel;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;
import com.example.fmmusic.View.Fragment.SongsPlayingFragment;

import java.io.File;
import java.util.List;

public class SongsLibAdapter extends RecyclerView.Adapter<SongsLibAdapter.SongsLibViewHolder> {
    public List<AudioModel> audioModels;

    public SongsLibAdapter(List<AudioModel> audioModels) {
        this.audioModels = audioModels;
    }

    @NonNull
    @Override
    public SongsLibAdapter.SongsLibViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_song_lib, parent, false);
        return new SongsLibViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SongsLibAdapter.SongsLibViewHolder holder, int position) {
        final AudioModel audioModel = audioModels.get(position);
        holder.tvNameSongLibRow.setText(audioModel.getName()+"");
        holder.tvSingerSongLibRow.setText(audioModel.getArtist()+"");
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(audioModel
                        .getImgPath()))
                .centerCrop()
                .into(holder.imgThumbSongLibRow);

        holder.imgMoreSongLibsRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(),holder.imgMoreSongLibsRow);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menuxoa:

                                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                        Long.parseLong(audioModel.getId()));
                                File file = new File(audioModel.getPath());
                                boolean deleted = file.delete();
                                if (deleted){
                                    v.getContext().getContentResolver().delete(uri,
                                            null,
                                            null);
                                    audioModels.remove(audioModel);
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyItemRangeChanged(holder.getAdapterPosition(), audioModels.size());
                                    Toast.makeText(v.getContext(), "Delete ok!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(v.getContext(), "Can't delete!", Toast.LENGTH_SHORT).show();

                                }

                                break;

                            case R.id.menuphat:
                                Intent intent = new Intent(v.getContext(), MusicPlayingActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("position",holder.getAdapterPosition());
                                bundle.putString("from","SongLibsAdapter");
                                bundle.putString("id",audioModel.getId());
                                bundle.putString("name",audioModel.getName());
                                bundle.putString("artist_names",audioModel.getArtist());
                                bundle.putString("performer",audioModel.getArtist());
                                bundle.putString("thumbnail",audioModel.getImgPath());
                                bundle.putString("duration",audioModel.getDuration()+"");

                                intent.putExtra("song_suggested",bundle);
                                SongsPlayingFragment songsPlayingFragment = SongsPlayingFragment.newInstance();
                                songsPlayingFragment.setArguments(bundle);

                                v.getContext().startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MusicPlayingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",holder.getAdapterPosition());
                bundle.putString("from","SongLibsAdapter");
                bundle.putString("id",audioModel.getId());
                bundle.putString("name",audioModel.getName());
                bundle.putString("artist_names",audioModel.getArtist());
                bundle.putString("performer",audioModel.getArtist());
                bundle.putString("thumbnail",audioModel.getImgPath());
                bundle.putString("duration",audioModel.getDuration()+"");

                intent.putExtra("song_suggested",bundle);
                SongsPlayingFragment songsPlayingFragment = SongsPlayingFragment.newInstance();
                songsPlayingFragment.setArguments(bundle);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return audioModels.size();
    }

    public class SongsLibViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgThumbSongLibRow;
        private TextView tvNameSongLibRow;
        private TextView tvSingerSongLibRow;
        private ImageView imgMoreSongLibsRow;
        public SongsLibViewHolder(@NonNull View itemView) {
            super(itemView);

            imgThumbSongLibRow = (ImageView) itemView.findViewById(R.id.imgThumbSongLibRow);
            tvNameSongLibRow = (TextView) itemView.findViewById(R.id.tvNameSongLibRow);
            tvSingerSongLibRow = (TextView) itemView.findViewById(R.id.tvSingerSongLibRow);
            imgMoreSongLibsRow = (ImageView) itemView.findViewById(R.id.imgMoreSongLibsRow);
        }
    }
}
