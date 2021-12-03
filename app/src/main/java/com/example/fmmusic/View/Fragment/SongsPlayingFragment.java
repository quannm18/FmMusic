package com.example.fmmusic.View.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fmmusic.DAO.PLLDAO;
import com.example.fmmusic.Model.PLL;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.HomeActivity;
import com.example.fmmusic.View.Activity.LoginActivity;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class SongsPlayingFragment extends Fragment {
    private ImageView imbBack;
    private ImageView imgThumbnailPlaying;
    private TextView tvTitlePlaying;
    private RecyclerView rcvSingerPlaylist;
    private TextView tvSubTitlePlaylistOfSiger;
    private ImageView imgAddtoPlaylist;
    private Dialog dialog;
    private ArrayAdapter<String> StringArrayAdapter;
    private PLLDAO plldao;


    public static SongsPlayingFragment songsPlaying = new SongsPlayingFragment();

    public static SongsPlayingFragment newInstance() {
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
        imgAddtoPlaylist = (ImageView) view.findViewById(R.id.imgAddtoPlaylist);

        imgAddtoPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddSongtoPlaylistDialog();
            }
        });
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        String title = ((MusicPlayingActivity) getActivity()).nameSong + " - " + ((MusicPlayingActivity) getActivity()).artist_name;
        if (title.length() > 35) {
            tvTitlePlaying.setText(((MusicPlayingActivity) getActivity()).nameSong.substring(0, 20) + "..." + " - " + ((MusicPlayingActivity) getActivity()).artist_name);
        } else {
            tvTitlePlaying.setText(title);
        }
        tvSubTitlePlaylistOfSiger.setText("Danh sách phát - " + ((MusicPlayingActivity) getActivity()).artist_name);
        Glide.with(getContext())
                .load(((MusicPlayingActivity) getActivity()).thumbnail)
                .centerCrop()
                .into(imgThumbnailPlaying);

        Log.e("img", ((MusicPlayingActivity) getActivity()).thumbnail);
        Log.e("indexofask", ((MusicPlayingActivity) getActivity()).thumbnail.indexOf("?") + "");
        String url = ((MusicPlayingActivity) getActivity()).thumbnail;
    }

    private Spinner spnAddtpPL;
    private MaterialButton btnAddSongToPlaylist;
    public void openAddSongtoPlaylistDialog() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.addtoplaylist_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        spnAddtpPL = (Spinner) dialog.findViewById(R.id.spnAddtpPL);
        btnAddSongToPlaylist = (MaterialButton) dialog.findViewById(R.id.btnAddSongToPlaylist);

        plldao = new PLLDAO(getContext());
        StringArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,plldao.getAllNamePLL());
        spnAddtpPL.setAdapter(StringArrayAdapter);
        btnAddSongToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }
}