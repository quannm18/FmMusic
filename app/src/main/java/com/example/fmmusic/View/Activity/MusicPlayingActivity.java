package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fmmusic.Adapter.ViewPagerAdapterMusicPlaying;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Fragment.SongsDetailFragment;
import com.example.fmmusic.View.Fragment.SongsPlayingFragment;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayingActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapterMusicPlaying viewPagerAdapterMusicPlaying;
    private SeekBar playingBar;
    private ImageView imbPlay;
    private ImageView imbPrevious;
    private ImageView imbNext;
    private TextView tvMinutePerSong;
    private ImageView imbFavorite;
    private ImageView imbLoop;
    private ImageView imbPause;
    private Bundle bundle;
    public String id;
    public String nameSong;
    public String artist_name;
    public String thumbnail;
    public String performer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing);
        playingBar = (SeekBar) findViewById(R.id.playingBar);
        imbPlay = (ImageView) findViewById(R.id.imbPlay);
        imbPrevious = (ImageView) findViewById(R.id.imbPrevious);
        imbNext = (ImageView) findViewById(R.id.imbNext);
        tvMinutePerSong = (TextView) findViewById(R.id.tvMinutePerSong);
        imbFavorite = (ImageView) findViewById(R.id.imbFavorite);
        imbLoop = (ImageView) findViewById(R.id.imbLoop);
        imbPause = (ImageView) findViewById(R.id.imbPause);
        viewPager = findViewById(R.id.vpMusicPlaying);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("song_suggested");
        Log.e("bundle",bundle.getString("id"));
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SongsPlayingFragment());
        fragmentList.add(new SongsDetailFragment());

        SongsPlayingFragment fragmentPlaying = new SongsPlayingFragment();
        fragmentPlaying.setArguments(bundle);
        SongsDetailFragment fragmentDetail = new SongsDetailFragment();
        fragmentDetail.setArguments(bundle);

        viewPagerAdapterMusicPlaying = new ViewPagerAdapterMusicPlaying(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapterMusicPlaying);
        imbPause.setVisibility(View.GONE);
        //TODO : thêm animation quay 360 cho imageview
        imbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imbPause.setVisibility(View.VISIBLE);
                imbPlay.setVisibility(View.GONE);
            }
        });
        imbPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imbPause.setVisibility(View.GONE);
                imbPlay.setVisibility(View.VISIBLE);
            }
        });
        id = bundle.getString("id");
        nameSong = bundle.getString("name");
        artist_name = bundle.getString("artist_names");
        thumbnail = bundle.getString("thumbnail");
        performer = bundle.getString("performer");
    }
}