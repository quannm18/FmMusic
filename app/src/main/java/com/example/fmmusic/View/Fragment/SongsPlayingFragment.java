package com.example.fmmusic.View.Fragment;

import static com.example.fmmusic.View.Activity.MusicPlayingActivity.pause;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.example.fmmusic.Adapter.CustomSpinerAdapter;
import com.example.fmmusic.DAO.FavoriteDAO;
import com.example.fmmusic.DAO.PLLDAO;
import com.example.fmmusic.DAO.PLLSongDAO;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.PLL;
import com.example.fmmusic.Model.PLLSong;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.FindingMusicActivity;
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
    private ImageView imgAddtoPlaylist;
    private Dialog dialog;

    private PLLDAO plldao;
    private PLLSongDAO pllSongDAO;
    private CustomSpinerAdapter customSpinerAdapter;
    private  List<PLL> pllList;


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
        imgAddtoPlaylist = (ImageView) view.findViewById(R.id.imgAddtoPlaylist);

        if (((MusicPlayingActivity)getActivity()).from.equals("SongLibsAdapter")){
            imgAddtoPlaylist.setVisibility(View.GONE);
        }
        imgAddtoPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddSongtoPlaylistDialog();
            }
        });
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
                getActivity().finish();
            }
        });
        String title = ((MusicPlayingActivity) getActivity()).nameSong + " - " + ((MusicPlayingActivity) getActivity()).artist_name;
        if (title.length() > 35) {
            if (((MusicPlayingActivity) getActivity()).nameSong.length()>10&&((MusicPlayingActivity) getActivity()).artist_name.length()<15){
                tvTitlePlaying.setText(((MusicPlayingActivity) getActivity()).nameSong.substring(0,10)+"... - "+((MusicPlayingActivity) getActivity()).artist_name);
            }
            if (((MusicPlayingActivity) getActivity()).nameSong.length()<15&&((MusicPlayingActivity) getActivity()).artist_name.length()>10){
                tvTitlePlaying.setText(((MusicPlayingActivity) getActivity()).nameSong.substring(0,10)+" - "+((MusicPlayingActivity) getActivity()).artist_name+"...");
            }
            if (((MusicPlayingActivity) getActivity()).nameSong.length()>10&&((MusicPlayingActivity) getActivity()).artist_name.length()>10){
                tvTitlePlaying.setText(((MusicPlayingActivity) getActivity()).nameSong.substring(0,10)+"... - "+((MusicPlayingActivity) getActivity()).artist_name+"...");
            }
        } else {
            tvTitlePlaying.setText(title);
        }
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
        SharedPreferences sdf = getContext().getSharedPreferences("USER_CURRENT", Context.MODE_PRIVATE);
        String userName =sdf.getString("USERNAME","");
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.addtoplaylist_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        spnAddtpPL = (Spinner) dialog.findViewById(R.id.spnAddtpPL);
        btnAddSongToPlaylist = (MaterialButton) dialog.findViewById(R.id.btnAddSongToPlaylist);
        pllSongDAO = new PLLSongDAO(getContext());
        plldao = new PLLDAO(getContext());
        pllList = new ArrayList<>();
        pllList = plldao.getDataUser(userName);

        customSpinerAdapter = new CustomSpinerAdapter(getContext(),pllList);
        spnAddtpPL.setAdapter(customSpinerAdapter);


        btnAddSongToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PLLSong pllSong = new PLLSong();
                Song song =  new Song();

                String id = ((MusicPlayingActivity) getActivity()).id;
                String nameSong = ((MusicPlayingActivity) getActivity()).nameSong;
                String duration = ((MusicPlayingActivity) getActivity()).duration;
                String thumbnail = ((MusicPlayingActivity) getActivity()).thumbnail;
                String artist_name = ((MusicPlayingActivity) getActivity()).artist_name;
                int idPLL = pllList.get(spnAddtpPL.getSelectedItemPosition()).getIdPLL();

                song.setId(id);
                song.setName(nameSong);
                song.setThumbnail(thumbnail);
                song.setDuration(Integer.parseInt(duration));
                Singer singer = new Singer("abcxyz",artist_name);
                song.setSinger(singer);

                pllSong.setIdPll(idPLL);
                pllSong.setSong(song);

                long kq = pllSongDAO.insertPllSong(pllSong);
                if (kq>0){
                    Toast.makeText(getContext(),"Thêm thành công ",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"Thêm không thành công ",Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }


}
