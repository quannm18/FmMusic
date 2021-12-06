package com.example.fmmusic.View.Fragment;

import android.content.Intent;
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
import com.example.fmmusic.Controller.Updateable;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.DownLoadActivity;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;

public class SongsDetailFragment extends Fragment implements Updateable {
    private TextView tvSingerDetail;
    private TextView tvComposerSongDetail;
    private TextView tvSongDetail;
    private TextView tvCategorySongDetail;
    private ImageView thumbDetailSongs;

    private TextView tvDownloadDetail;
    private TextView tvAsk;

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


        tvDownloadDetail = (TextView) view.findViewById(R.id.tvDownloadDetail);
        tvAsk = (TextView) view.findViewById(R.id.tvAsk);


        String from = ((MusicPlayingActivity)getActivity()).from;
        String id = ((MusicPlayingActivity)getActivity()).id;
        tvSingerDetail.setText(((MusicPlayingActivity)getActivity()).artist_name);
        tvComposerSongDetail.setText(((MusicPlayingActivity)getActivity()).performer);
        tvSongDetail.setText(((MusicPlayingActivity)getActivity()).nameSong);
        Glide.with(getContext())
                .load(((MusicPlayingActivity)getActivity()).thumbnail)
                .centerCrop()
                .into(thumbDetailSongs);

        if (from.equals("TopAdapter")||from.equals("SuggestAdapter")||from.equals("FindingAdapter")
                ||from.equals("HighlightAdapter")||from.equals("PLLSongAdapter")
                ||from.equals("SongsOfSingerFavoriteAdapter")||from.equals("FVRSongAdapter")
                ||from.equals("PlayListSongAdapter")||from.equals("NewSongHomeAdapter")){
            tvDownloadDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DownLoadActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
        } else {
            tvAsk.setVisibility(View.GONE);
            tvDownloadDetail.setVisibility(View.GONE);
        }

    }

    @Override
    public void update() {
        String from = ((MusicPlayingActivity)getActivity()).from;
        String id = ((MusicPlayingActivity)getActivity()).id;
        tvSingerDetail.setText(((MusicPlayingActivity)getActivity()).artist_name);
        tvComposerSongDetail.setText(((MusicPlayingActivity)getActivity()).performer);
        tvSongDetail.setText(((MusicPlayingActivity)getActivity()).nameSong);
        Glide.with(getContext())
                .load(((MusicPlayingActivity)getActivity()).thumbnail)
                .centerCrop()
                .into(thumbDetailSongs);

        if (from.equals("TopAdapter")||from.equals("SuggestAdapter")||from.equals("FindingAdapter")||from.equals("HighlightAdapter")){
            tvDownloadDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DownLoadActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
        } else {
            tvAsk.setVisibility(View.GONE);
            tvDownloadDetail.setVisibility(View.GONE);
        }
    }
}