package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
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

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SongsPlayingFragment());
        fragmentList.add(new SongsDetailFragment());

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
    }
}