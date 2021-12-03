package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
    private SeekBar seekBar;
    private ImageView imbPrevious;
    private ImageView imbNext;
    private TextView tvMinutePerSong;
    private ImageView imbFavorite;
    private ImageView imbLoop;
    private ImageView btnPlay;
    private TextView tvMinuteRunning;

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

    static Uri uri;
    static MediaPlayer mediaPlayer;
    public static String DOMAIN_PLAY = "http://api.mp3.zing.vn/api/streaming/audio/";
    private Animation animation;
    public Handler handler = new Handler();
    private Favorite favorite;
    private FavoriteDAO favoriteDAO;
    private Thread playThread, prevThread, nextThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing);
        seekBar = findViewById(R.id.playingBar);
        imbPrevious = findViewById(R.id.imbPrevious);
        imbNext = findViewById(R.id.imbNext);
        tvMinutePerSong = findViewById(R.id.tvMinutePerSong);
        imbFavorite = findViewById(R.id.imbFavorite);
        imbLoop = findViewById(R.id.imbLoop);
        btnPlay = findViewById(R.id.imbPause);
        viewPager = findViewById(R.id.vpMusicPlaying);

        seekBar = (SeekBar) findViewById(R.id.playingBar);
        tvMinutePerSong = (TextView) findViewById(R.id.tvMinutePerSong);
        tvMinuteRunning = (TextView) findViewById(R.id.tvMinuteRunning);

        getIntentData();
        animation = AnimationUtils.loadAnimation(MusicPlayingActivity.this,R.anim.run_music);


        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SongsPlayingFragment());
        fragmentList.add(new SongsDetailFragment());

        SongsPlayingFragment fragmentPlaying = new SongsPlayingFragment();
        fragmentPlaying.setArguments(bundle);
        SongsDetailFragment fragmentDetail = new SongsDetailFragment();
        fragmentDetail.setArguments(bundle);

        viewPagerAdapterMusicPlaying = new ViewPagerAdapterMusicPlaying(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapterMusicPlaying);
        //TODO : thêm animation quay 360 cho imageview
        imbFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favorite = new Favorite();
                Song song =  new Song();
                favoriteDAO = new FavoriteDAO(MusicPlayingActivity.this);
                song.setId(id);
                song.setName(nameSong);
                song.setThumbnail(thumbnail);
                song.setDuration(Integer.parseInt(duration));
                Singer singer = new Singer("abcxyz",artist_name);
                song.setSinger(singer);
                favorite.setSong(song);
                favorite.setUseName("tho2002");
                long kq = favoriteDAO.insertFV(favorite);
                if (kq>0){
                    Toast.makeText(MusicPlayingActivity.this,"Thêm thành công ",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MusicPlayingActivity.this,"Thêm không thành công ",Toast.LENGTH_LONG).show();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        MusicPlayingActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer!=null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(mCurrentPosition);
                    tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                }
                handler.postDelayed(this::run,1000);
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

            if (!tops.isEmpty()){
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY+tops.get(position).getId()+"/320");
            }
            createMediaPlayer();
        }
        if (from.equals("SuggestAdapter")){
            tops = PersonalFragment.suggestedList;
            Log.e("size",tops.size()+"SuggestAdapter");

            if (!tops.isEmpty()){
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY+tops.get(position).getId()+"/320");
            }
            createMediaPlayer();
        }
        if (from.equals("FindingAdapter")){
            songs = FindingMusicActivity.findingList;
            if (songs.isEmpty()){
                Toast.makeText(MusicPlayingActivity.this, "K phải Finding", Toast.LENGTH_SHORT).show();
            }else {
                Log.e("size",songs.size()+"FindingAdapter");
            }
            if (!songs.isEmpty()){
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY+songs.get(position).getId()+"/320");
            }
            createMediaPlayer();
        }
        if (from.equals("HighlightAdapter")){
            tops = HomeFragment.topList;
            Log.e("size",tops.size()+"HighlightAdapter");

            if (!tops.isEmpty()){
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY+tops.get(position).getId()+"/320");
            }
            createMediaPlayer();
        }
    }

    public void createMediaPlayer(){
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this,uri);
            mediaPlayer.start();
        }else {
            mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this,uri);
            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration()/1000);
        tvMinutePerSong.setText(convertFormat(mediaPlayer.getDuration()/1000));
    }

    private String convertFormat(int duration) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(duration % 60);
        String minutes = String.valueOf(duration / 60);

        totalOut = minutes +":"+seconds;
        totalNew = minutes +":"+"0"+seconds;
        if (seconds.length()==1){
            return totalNew;
        }
        return totalOut;
    }

    @Override
    protected void onResume() {
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        super.onResume();
    }

    private void prevThreadBtn() {
        prevThread = new Thread(){
            @Override
            public void run() {
                super.run();
                imbNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void preBtnClicked() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();

            if (from.equals("TopAdapter")){
                tops = RankingsFragment.topList;

                position = (position+1)%tops.size();
                Uri uri = Uri.parse(tops.get(position).getThumbnail());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                //metaData(uri);
                //setSongName
                //setArtist
                seekBar.setMax(mediaPlayer.getDuration()/1000);
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer!=null){
                            int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                            seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                        }
                        handler.postDelayed(this::run,1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
            if (from.equals("SuggestAdapter")){
                tops = PersonalFragment.suggestedList;
                position = (position+1)%tops.size();
                Uri uri = Uri.parse(tops.get(position).getThumbnail());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                //metaData(uri);
                //setSongName
                //setArtist
                seekBar.setMax(mediaPlayer.getDuration()/1000);
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer!=null){
                            int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                            seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                        }
                        handler.postDelayed(this::run,1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
            if (from.equals("FindingAdapter")){
                songs = FindingMusicActivity.findingList;
                position = (position+1)%songs.size();
                Uri uri = Uri.parse(songs.get(position).getThumbnail());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                //metaData(uri);
                //setSongName
                //setArtist
                seekBar.setMax(mediaPlayer.getDuration()/1000);
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer!=null){
                            int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                            seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                        }
                        handler.postDelayed(this::run,1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
            if (from.equals("HighlightAdapter")){
                tops = HomeFragment.topList;
                position = (position+1)%tops.size();
                Uri uri = Uri.parse(tops.get(position).getThumbnail());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                //metaData(uri);
                //setSongName
                //setArtist
                seekBar.setMax(mediaPlayer.getDuration()/1000);
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer!=null){
                            int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                            seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                        }
                        handler.postDelayed(this::run,1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }

        }else {
            if (from.equals("TopAdapter")){
                tops = RankingsFragment.topList;
                position = (position+1)%tops.size();
                Uri uri = Uri.parse(tops.get(position).getThumbnail());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                //metaData(uri);
                //setSongName
                //setArtist
                seekBar.setMax(mediaPlayer.getDuration()/1000);
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer!=null){
                            int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                            seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                        }
                        handler.postDelayed(this::run,1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("SuggestAdapter")){
                tops = PersonalFragment.suggestedList;
                position = (position+1)%tops.size();
                Uri uri = Uri.parse(tops.get(position).getThumbnail());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                //metaData(uri);
                //setSongName
                //setArtist
                seekBar.setMax(mediaPlayer.getDuration()/1000);
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer!=null){
                            int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                            seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                        }
                        handler.postDelayed(this::run,1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);

            }
            if (from.equals("FindingAdapter")){
                songs = FindingMusicActivity.findingList;
                position = (position+1)%songs.size();
                Uri uri = Uri.parse(songs.get(position).getThumbnail());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                //metaData(uri);
                //setSongName
                //setArtist
                seekBar.setMax(mediaPlayer.getDuration()/1000);
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer!=null){
                            int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                            seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                        }
                        handler.postDelayed(this::run,1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);

            }
            if (from.equals("HighlightAdapter")){
                tops = HomeFragment.topList;
                position = (position+1)%tops.size();
                Uri uri = Uri.parse(tops.get(position).getThumbnail());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                //metaData(uri);
                //setSongName
                //setArtist
                seekBar.setMax(mediaPlayer.getDuration()/1000);
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer!=null){
                            int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                            seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                        }
                        handler.postDelayed(this::run,1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);

            }
        }
    }

    private void nextThreadBtn() {
        nextThread = new Thread(){
            @Override
            public void run() {
                super.run();
                imbNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void playThreadBtn() {
        playThread = new Thread(){
            @Override
            public void run() {
                super.run();
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void pauseBtnClicked() {
        if (mediaPlayer.isPlaying()){

            btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                    }
                    handler.postDelayed(this::run,1000);
                }
            });
        }else {
            btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                    }
                    handler.postDelayed(this::run,1000);
                }
            });
        }
    }
}