package com.example.fmmusic.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.HomeActivity;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;

public class SongsPlayingFragment extends Fragment {
    private ImageView imbBack;
    private ImageView imgThumbnailPlaying;
    private TextView tvTitlePlaying;
    private RecyclerView rcvSingerPlaylist;
    private TextView tvSubTitlePlaylistOfSiger;
    private Bundle bundle;
    private MusicPlayingActivity musicPlayingActivity;

    public static SongsPlayingFragment songsPlaying = new SongsPlayingFragment();
    public static SongsPlayingFragment newInstance(){
        return songsPlaying;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs_playing, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imbBack = (ImageView) view.findViewById(R.id.imbBack);
        imgThumbnailPlaying = (ImageView) view.findViewById(R.id.imgThumbnailPlaying);
        tvTitlePlaying = (TextView) view.findViewById(R.id.tvTitlePlaying);
        rcvSingerPlaylist = (RecyclerView) view.findViewById(R.id.rcvSingerPlaylist);
        tvSubTitlePlaylistOfSiger = (TextView) view.findViewById(R.id.tvSubTitlePlaylistOfSiger);

        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });
        String title = ((MusicPlayingActivity)getActivity()).nameSong+" - "+((MusicPlayingActivity)getActivity()).artist_name;
        if (title.length()>35){
            tvTitlePlaying.setText(((MusicPlayingActivity)getActivity()).nameSong.substring(0,20)+"..."+" - "+((MusicPlayingActivity)getActivity()).artist_name);
        }
        else {
            tvTitlePlaying.setText(title);
        }
        tvSubTitlePlaylistOfSiger.setText("Danh sách phát - "+((MusicPlayingActivity)getActivity()).artist_name);
        Glide.with(getContext())
                .load(((MusicPlayingActivity)getActivity()).thumbnail)
                .centerCrop()
                .into(imgThumbnailPlaying);

        Log.e("img",((MusicPlayingActivity)getActivity()).thumbnail);
        Log.e("indexofask",((MusicPlayingActivity)getActivity()).thumbnail.indexOf("?")+"");
        String url = ((MusicPlayingActivity)getActivity()).thumbnail;
    }


}