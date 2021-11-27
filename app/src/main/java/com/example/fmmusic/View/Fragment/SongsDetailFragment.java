package com.example.fmmusic.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;

public class SongsDetailFragment extends Fragment {
    private TextView tvSingerDetail;
    private TextView tvComposerSongDetail;
    private TextView tvSongDetail;
    private TextView tvCategorySongDetail;
    private ImageView thumbDetailSongs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvSingerDetail = (TextView) view.findViewById(R.id.tvSingerDetail);
        tvComposerSongDetail = (TextView) view.findViewById(R.id.tvComposerSongDetail);
        tvSongDetail = (TextView) view.findViewById(R.id.tvSongDetail);
        tvCategorySongDetail = (TextView) view.findViewById(R.id.tvCategorySongDetail);
        thumbDetailSongs = (ImageView) view.findViewById(R.id.thumbDetailSongs);

        tvSingerDetail.setText(((MusicPlayingActivity)getActivity()).artist_name);
        tvComposerSongDetail.setText(((MusicPlayingActivity)getActivity()).performer);
        tvSongDetail.setText(((MusicPlayingActivity)getActivity()).nameSong);
        Glide.with(getContext())
                .load(((MusicPlayingActivity)getActivity()).thumbnail)
                .centerCrop()
                .into(thumbDetailSongs);
    }
}