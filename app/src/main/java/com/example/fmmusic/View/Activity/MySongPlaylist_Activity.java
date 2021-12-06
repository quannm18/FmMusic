package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.fmmusic.Adapter.PLLSongAdapter;
import com.example.fmmusic.DAO.PLLSongDAO;
import com.example.fmmusic.Model.PLLSong;
import com.example.fmmusic.R;

import java.util.ArrayList;
import java.util.List;

public class MySongPlaylist_Activity extends AppCompatActivity {
    private TextView textView;
    private TextView tvUserPLLName;
    private Button button;
    private RecyclerView rcvUserPLL;
    private Bundle bundle;
    public static List<PLLSong> pllSongList;
    private PLLSongDAO pllSongDAO;
    private PLLSongAdapter pllSongAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_song_playlist);

        textView = (TextView) findViewById(R.id.textView);
        tvUserPLLName = (TextView) findViewById(R.id.tvUserPLLName);
        rcvUserPLL = (RecyclerView) findViewById(R.id.rcvUserPLL);

        pllSongList = new ArrayList<>();
        pllSongDAO = new PLLSongDAO(MySongPlaylist_Activity.this);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("idPLLFromMyPlaylistAdapter");
        int getIdPLL = bundle.getInt("MyPlayListAdapter");
        String namePLL = bundle.getString("namePLL");

        tvUserPLLName.setText("Các bài hát trong "+namePLL);
        pllSongList = pllSongDAO.getSongFromPLL(getIdPLL);
        pllSongAdapter = new PLLSongAdapter(pllSongList);
        pllSongList = new ArrayList<>();
        rcvUserPLL.setAdapter(pllSongAdapter);
        rcvUserPLL.setLayoutManager(new LinearLayoutManager(MySongPlaylist_Activity.this));


    }
}