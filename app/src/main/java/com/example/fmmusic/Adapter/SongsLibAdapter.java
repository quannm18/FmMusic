package com.example.fmmusic.Adapter;

import android.content.ContentUris;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fmmusic.Model.Songs.AudioModel;
import com.example.fmmusic.R;

import java.io.File;
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
        Context context = holder.itemView.getContext();
        holder.tvRowSongLib.setText(audioModel.getName());
        holder.tvRowArtistSongLib.setText(audioModel.getArtist());
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(audioModel
                        .getImgPath()))
                .centerCrop()
                .into(holder.imgRowSongLib);
        holder.imgPopupmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.imgPopupmenu);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menuxoa:
                                Uri uri= ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                        Long.parseLong(audioModels.get(holder.getAdapterPosition()).getId()));
                                File file = new File(audioModels.get(holder.getAdapterPosition()).getPath());
                                boolean deleted = file.delete();
                                if (deleted){
                                    v.getContext().getContentResolver().delete(uri,
                                            null,
                                            null);
                                    audioModels.remove(audioModel);
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyItemRangeChanged(holder.getAdapterPosition(),
                                            audioModels.size());
                                    notifyDataSetChanged();
                                    Toast.makeText(v.getContext(), "Delete ok!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(v.getContext(), "Can't delete!", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.menuphat:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return audioModels.size();
    }

    public class SongsLibViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgRowSongLib;
        private TextView tvRowSongLib;
        private TextView tvRowArtistSongLib;
        private ImageView imgPopupmenu;

        public SongsLibViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPopupmenu = itemView.findViewById(R.id.tvPopupmenu);
            imgRowSongLib = (ImageView) itemView.findViewById(R.id.imgThumbSongLibRow);
            tvRowSongLib = (TextView) itemView.findViewById(R.id.tvNameSongLibRow);
            tvRowArtistSongLib = (TextView) itemView.findViewById(R.id.tvSingerSongLibRow);


        }
    }
}
