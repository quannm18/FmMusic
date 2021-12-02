package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fmmusic.Adapter.ViewPagerAdapterMusicPlaying;
import com.example.fmmusic.DAO.FavoriteDAO;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.View.Fragment.*;
import com.example.fmmusic.Model.Songs.AudioModel;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Fragment.SongsDetailFragment;
import com.example.fmmusic.View.Fragment.SongsPlayingFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public int position;
    public String from;
    public String id;
    public String nameSong;
    public String artist_name;
    public String thumbnail;
    public String performer;
    public String duration;

    static List<Song> songs = new ArrayList<>();
    static List<AudioModel> audios = new ArrayList<>();
    static List<Top> tops = new ArrayList<>();
    private Animation animation;

    private List<Favorite> favoriteList;
    private FavoriteDAO favoriteDAO;
    private Favorite favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing);
        playingBar = findViewById(R.id.playingBar);
        imbPlay = findViewById(R.id.imbPlay);
        imbPrevious = findViewById(R.id.imbPrevious);
        imbNext = findViewById(R.id.imbNext);
        tvMinutePerSong = findViewById(R.id.tvMinutePerSong);
        imbFavorite = findViewById(R.id.imbFavorite);
        imbLoop = findViewById(R.id.imbLoop);
        imbPause = findViewById(R.id.imbPause);
        viewPager = findViewById(R.id.vpMusicPlaying);

        getIntentData();
        animation = AnimationUtils.loadAnimation(MusicPlayingActivity.this,R.anim.run_music);

        favoriteDAO = new FavoriteDAO(MusicPlayingActivity.this);
        favoriteList = new ArrayList<>();
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
        imbFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favorite = new Favorite();
                Song song =  new Song();

                song.setId(id);
                song.setName(nameSong);
                song.setThumbnail(thumbnail);
                song.setDuration(Integer.parseInt(duration));
                Singer singer = new Singer("abcxyz",artist_name);
                song.setSinger(singer);
                favorite.setSong(song);
                favorite.setUseName("tho2002");
                long kq = favoriteDAO.insertFV(favorite);
                if (kq>0)
                {
                    favoriteList.clear();
                    favoriteList.addAll(favoriteDAO.getAllFVR());

                    Toast.makeText(MusicPlayingActivity.this,"Thêm thành công ",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(MusicPlayingActivity.this,"Thêm không thành công ",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    void getIntentData(){
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("song_suggested");
        Log.e("bundle",bundle.getString("id"));

        from = bundle.getString("from");
        position = (bundle.getInt("position",-1));
        id = bundle.getString("id");
        nameSong = bundle.getString("name");
        artist_name = bundle.getString("artist_names");
        thumbnail = bundle.getString("thumbnail");
        performer = bundle.getString("performer");
        duration = bundle.getString("duration");

        if (from.equals("TopAdapter")){
            tops = RankingsFragment.topList;
            Log.e("size",tops.size()+"TopAdapter");
        }
        if (from.equals("SuggestAdapter")){
            tops = PersonalFragment.suggestedList;
            Log.e("size",tops.size()+"SuggestAdapter");
        }
        if (from.equals("FindingAdapter")){
            songs = FindingMusicActivity.findingList;
            if (songs.isEmpty()){
                Toast.makeText(MusicPlayingActivity.this, "K phải Finding", Toast.LENGTH_SHORT).show();
            }else {
                Log.e("size",songs.size()+"FindingAdapter");
            }
        }
        if (from.equals("HighlightAdapter")){
            tops = HomeFragment.topList;
            Log.e("size",tops.size()+"HighlightAdapter");
        }
    }


    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MICROSECONDS.toSeconds(duration)
                        -TimeUnit.MINUTES.toSeconds(TimeUnit.MICROSECONDS.toMinutes(duration)));
    }
}