package com.example.fmmusic.View.Activity;

import static com.example.fmmusic.Adapter.FavoriteSongsAdapter.favoriteListAdapter;
import static com.example.fmmusic.Adapter.PLLSongAdapter.pllSongListAdapter;
import static com.example.fmmusic.Adapter.SongsOfSingerFavoriteAdapter.songOfSinger;
import static com.example.fmmusic.Controller.ApplicationClass.ACTION_NEXT;
import static com.example.fmmusic.Controller.ApplicationClass.ACTION_PLAY;
import static com.example.fmmusic.Controller.ApplicationClass.ACTION_PREVIOUS;
import static com.example.fmmusic.Controller.ApplicationClass.CHANNEL_ID_2;
import static com.example.fmmusic.View.Activity.MySongPlaylist_Activity.pllSongList;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.fmmusic.Adapter.ViewPagerAdapterMusicPlaying;
import com.example.fmmusic.Controller.ActionPlaying;
import com.example.fmmusic.Controller.MusicService;
import com.example.fmmusic.Controller.VNCharacterUtils;
import com.example.fmmusic.DAO.FavoriteDAO;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.PLLSong;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.AudioModel;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.Persional.PlayListScreenActivity;
import com.example.fmmusic.View.Activity.Persional.SongsLibActivity;
import com.example.fmmusic.View.Fragment.HomeFragment;
import com.example.fmmusic.View.Fragment.PersonalFragment;
import com.example.fmmusic.View.Fragment.RankingsFragment;
import com.example.fmmusic.View.Fragment.SongsDetailFragment;
import com.example.fmmusic.View.Fragment.SongsPlayingFragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayingActivity extends AppCompatActivity
        implements ActionPlaying, ServiceConnection {
    public static String DOMAIN_PLAY = "http://api.mp3.zing.vn/api/streaming/audio/";
    public static String DOMAIN_SHARE = "https://m.zingmp3.vn/bai-hat/";
    public static List<Song> songs = new ArrayList<>();
    public static List<AudioModel> audios = new ArrayList<>();
    public static List<Top> tops = new ArrayList<>();
    public static List<Top> songNewsList = new ArrayList<>();
    public static List<PLLSong> pllSongs = new ArrayList<>();
    public static List<Favorite> favoriteList = new ArrayList<>();
    public static Uri uri;

    public static Context context;
    public int position;
    public String from;
    public String id;
    public String nameSong;
    public String artist_name;
    public String thumbnail;
    public String performer;
    public String duration;
    public Handler handler = new Handler();
    public String[] art;
    public static MusicService musicService;
    MediaSessionCompat mediaSessionCompat;
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
    private Animation animation;
    private Favorite favorite;
    private FavoriteDAO favoriteDAO;
    private Thread playThread, prevThread, nextThread;

    public static List<AudioModel> getAudios() {
        return audios;
    }

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
        animation = AnimationUtils.loadAnimation(MusicPlayingActivity.this, R.anim.run_music);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SongsPlayingFragment());
        fragmentList.add(new SongsDetailFragment());
        context = this;
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
                Song song = new Song();
                favoriteDAO = new FavoriteDAO(MusicPlayingActivity.this);
                song.setId(id);
                song.setName(nameSong);
                song.setThumbnail(thumbnail);
                song.setDuration(Integer.parseInt(duration));
                Singer singer = new Singer("abcxyz", artist_name);
                song.setSinger(singer);
                favorite.setSong(song);

                SharedPreferences sdf = getSharedPreferences("USER_CURRENT", MODE_PRIVATE);
                favorite.setUseName(sdf.getString("USERNAME", ""));
                long kq = favoriteDAO.insertFV(favorite);
                if (kq > 0) {
                    Toast.makeText(MusicPlayingActivity.this, "Thêm thành công ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MusicPlayingActivity.this, "Thêm không thành công ", Toast.LENGTH_LONG).show();
                }
            }
        });

        imbLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("SongLibsAdapter")){
                    String link = nameSong+" "+artist_name+"/"+id+".html";
                    String convertLink = VNCharacterUtils.removeAccent(link.replace(" ","-"));
                    Log.e("link",DOMAIN_SHARE+convertLink);
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                    i.putExtra(Intent.EXTRA_TEXT, DOMAIN_SHARE+convertLink+"");
                    startActivity(Intent.createChooser(i, "This is so nice!"));
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (musicService != null && fromUser) {
                    musicService.seekTo(progress * 1000);
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
                if (musicService != null) {
                    int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                }
                handler.postDelayed(this::run, 1000);
            }
        });


    }

    void getIntentData() {
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("song_suggested");
        Log.e("bundle", bundle.getString("id"));

        from = bundle.getString("from");
        position = (bundle.getInt("position", -1));
        id = bundle.getString("id");
        nameSong = bundle.getString("name");
        artist_name = bundle.getString("artist_names");
        thumbnail = bundle.getString("thumbnail");
        performer = bundle.getString("performer");
        duration = bundle.getString("duration");

        Intent intentService = new Intent(this, MusicService.class);
        intentService.putExtra("servicePosition", position);
        intentService.putExtra("from", from);

        if (from.equals("TopAdapter")) {
            tops = RankingsFragment.topList;

            if (!tops.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("NewSongHomeAdapter")) {
            songNewsList = HomeFragment.songNewList;

            if (!tops.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("PLLSongAdapter")) {
            pllSongs = pllSongListAdapter;

            if (!pllSongs.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + pllSongs.get(position).getSong().getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("SongsOfSingerFavoriteAdapter")) {
            songs = songOfSinger;

            if (!pllSongs.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("FVRSongAdapter")) {
            favoriteList = favoriteListAdapter;

            if (!favoriteList.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + favoriteList.get(position).getSong().getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("PlayListSongAdapter")) {
            songs = PlayListScreenActivity.songPList;

            if (!songs.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("SuggestAdapter")) {
            tops = PersonalFragment.suggestedList;

            if (!tops.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("FindingAdapter")) {
            songs = FindingMusicActivity.findingList;
            if (!songs.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("HighlightAdapter")) {
            tops = HomeFragment.topList;

            if (!tops.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("SongLibsAdapter")) {
            audios = SongsLibActivity.audioModelList;

            if (!audios.isEmpty()) {
                
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(audios.get(position).getPath());
            }
            startService(intentService);
        }
    }

    private String convertFormat(int duration) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(duration % 60);
        String minutes = String.valueOf(duration / 60);

        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1) {
            return totalNew;
        }
        return totalOut;
    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    public void prevThreadBtn() {
        prevThread = new Thread() {
            @Override
            public void run() {
                super.run();
                imbPrevious.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    public void preBtnClicked() {
        if (musicService.isPlaying()) {
            musicService.stop();
            musicService.release();
            if (from.equals("TopAdapter")) {
                tops = RankingsFragment.topList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);

                from = "TopAdapter";
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("NewSongHomeAdapter")) {
                songNewsList = HomeFragment.songNewList;
                if (position > 0) {
                    position--;
                } else {
                    position = songNewsList.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + songNewsList.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);

                from = "NewSongHomeAdapter";
                id = songNewsList.get(position).getId();
                nameSong = songNewsList.get(position).getName();
                artist_name = songNewsList.get(position).getSinger().getName();
                thumbnail = songNewsList.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("SongsOfSingerFavoriteAdapter")) {
                songs = songOfSinger;
                if (position > 0) {
                    position--;
                } else {
                    position = songs.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);

                from = "SongsOfSingerFavoriteAdapter";
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("FVRSongAdapter")) {
                favoriteList = favoriteListAdapter;
                if (position > 0) {
                    position--;
                } else {
                    position = favoriteList.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + favoriteList.get(position).getSong().getId() + "/320");
                musicService.createMediaPlayer(position);

                from = "FVRSongAdapter";
                id = favoriteList.get(position).getSong().getId();
                nameSong = favoriteList.get(position).getSong().getName();
                artist_name = favoriteList.get(position).getSong().getSinger().getName();
                thumbnail = favoriteList.get(position).getSong().getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("PlayListSongAdapter")) {
                songs = PlayListScreenActivity.songPList;
                if (position > 0) {
                    position--;
                } else {
                    position = songs.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);

                from = "PlayListSongAdapter";
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("PLLSongAdapter")) {
                pllSongs = pllSongList;
                if (position > 0) {
                    position--;
                } else {
                    position = pllSongs.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + pllSongs.get(position).getSong().getId() + "/320");
                musicService.createMediaPlayer(position);

                from = "PlayListSongAdapter";
                id = pllSongs.get(position).getSong().getId();
                nameSong = pllSongs.get(position).getSong().getName();
                artist_name = pllSongs.get(position).getSong().getSinger().getName();
                thumbnail = pllSongs.get(position).getSong().getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("SuggestAdapter")) {
                tops = PersonalFragment.suggestedList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);
                from = "SuggestAdapter";
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("FindingAdapter")) {
                songs = FindingMusicActivity.findingList;
                if (position > 0) {
                    position--;
                } else {
                    position = songs.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);
                from = "FindingAdapter";
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("HighlightAdapter")) {
                tops = HomeFragment.topList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);
                from = "HighlightAdapter";
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("SongLibsAdapter")) {

//                musicService.showNotification(R.drawable.ic_baseline_pause_circle_outline_24);
                audios = SongsLibActivity.audioModelList;
                if (position > 0) {
                    position--;
                } else {
                    position = audios.size() - 1;
                }
                uri = Uri.parse(audios.get(position).getPath());
                musicService.createMediaPlayer(position);

                from = "SongLibsAdapter";
                position = position;
                id = audios.get(position).getId();
                nameSong = audios.get(position).getName();
                artist_name = audios.get(position).getArtist();
                thumbnail = audios.get(position).getImgPath();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
        } else {
            musicService.stop();
            musicService.release();

            if (from.equals("TopAdapter")) {
                tops = RankingsFragment.topList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId());
                musicService.createMediaPlayer(position);

                //noti
                from = "TopAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("NewSongHomeAdapter")) {
                songNewsList = HomeFragment.songNewList;
                if (position > 0) {
                    position--;
                } else {
                    position = songNewsList.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + songNewsList.get(position).getId());
                musicService.createMediaPlayer(position);

                //noti
                from = "NewSongHomeAdapter";
                position = position;
                id = songNewsList.get(position).getId();
                nameSong = songNewsList.get(position).getName();
                artist_name = songNewsList.get(position).getSinger().getName();
                thumbnail = songNewsList.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("SongsOfSingerFavoriteAdapter")) {
                songs = songOfSinger;
                if (position > 0) {
                    position--;
                } else {
                    position = songs.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId());
                musicService.createMediaPlayer(position);

                //noti
                from = "SongsOfSingerFavoriteAdapter";
                position = position;
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("FVRSongAdapter")) {
                favoriteList = favoriteListAdapter;
                if (position > 0) {
                    position--;
                } else {
                    position = favoriteList.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + favoriteList.get(position).getSong().getId());
                musicService.createMediaPlayer(position);

                //noti
                from = "FVRSongAdapter";
                position = position;
                id = favoriteList.get(position).getSong().getId();
                nameSong = favoriteList.get(position).getSong().getName();
                artist_name = favoriteList.get(position).getSong().getSinger().getName();
                thumbnail = favoriteList.get(position).getSong().getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("PLLSongAdapter")) {
                pllSongs = pllSongList;
                if (position > 0) {
                    position--;
                } else {
                    position = pllSongs.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + pllSongs.get(position).getSong().getId());
                musicService.createMediaPlayer(position);

                //noti
                from = "TopAdapter";
                position = position;
                id = pllSongs.get(position).getSong().getId();
                nameSong = pllSongs.get(position).getSong().getName();
                artist_name = pllSongs.get(position).getSong().getSinger().getName();
                thumbnail = pllSongs.get(position).getSong().getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("PlayListSongAdapter")) {
                songs = PlayListScreenActivity.songPList;
                if (position > 0) {
                    position--;
                } else {
                    position = songs.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId());
                musicService.createMediaPlayer(position);

                //noti
                from = "TopAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("SuggestAdapter")) {
                tops = PersonalFragment.suggestedList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId());
                musicService.createMediaPlayer(position);

                //noti
                from = "SuggestAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("FindingAdapter")) {
                songs = FindingMusicActivity.findingList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId());
                musicService.createMediaPlayer(position);
                //noti
                from = "FindingAdapter";
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("HighlightAdapter")) {
                tops = HomeFragment.topList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId());
                musicService.createMediaPlayer(position);

                //noti
                from = "HighlightAdapter";
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("SongLibsAdapter")) {
//                musicService.showNotification(R.drawable.ic_baseline_play_arrow_24);
                audios = SongsLibActivity.audioModelList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(audios.get(position).getPath());
                musicService.createMediaPlayer(position);

                from = "SongLibsAdapter";
                id = audios.get(position).getId();
                nameSong = audios.get(position).getName();
                artist_name = audios.get(position).getArtist();
                thumbnail = audios.get(position).getImgPath();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
        }
    }

    public void nextThreadBtn() {
        nextThread = new Thread() {
            @Override
            public void run() {
                super.run();
                imbNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    public void nextBtnClicked() {
        if (musicService.isPlaying()) {
            musicService.stop();
            musicService.release();
            if (from.equals("TopAdapter")) {
                tops = RankingsFragment.topList;
                if (position < tops.size()-1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);
                //reset adapter
                from = "TopAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("NewSongHomeAdapter")) {
                songNewsList = HomeFragment.songNewList;
                if (position < songNewsList.size()-1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + songNewsList.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);
                //reset adapter
                from = "NewSongHomeAdapter";
                position = position;
                id = songNewsList.get(position).getId();
                nameSong = songNewsList.get(position).getName();
                artist_name = songNewsList.get(position).getSinger().getName();
                thumbnail = songNewsList.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("SongsOfSingerFavoriteAdapter")) {
                songs = songOfSinger;
                if (position < songs.size()-1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);
                //reset adapter
                from = "SongsOfSingerFavoriteAdapter";
                position = position;
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("FVRSongAdapter")) {
                favoriteList = favoriteListAdapter;
                if (position < favoriteList.size()-1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + favoriteList.get(position).getSong().getId() + "/320");
                musicService.createMediaPlayer(position);
                //reset adapter
                from = "FVRSongAdapter";
                position = position;
                id = favoriteList.get(position).getSong().getId();
                nameSong = favoriteList.get(position).getSong().getName();
                artist_name = favoriteList.get(position).getSong().getSinger().getName();
                thumbnail = favoriteList.get(position).getSong().getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("PLLSongAdapter")) {
                pllSongs = pllSongList;
                if (position < pllSongs.size()-1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + pllSongs.get(position).getSong().getId() + "/320");
                musicService.createMediaPlayer(position);
                //reset adapter
                from = "PLLSongAdapter";
                position = position;
                id = pllSongs.get(position).getSong().getId();
                nameSong = pllSongs.get(position).getSong().getName();
                artist_name = pllSongs.get(position).getSong().getSinger().getName();
                thumbnail = pllSongs.get(position).getSong().getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("PlayListSongAdapter")) {
                songs = PlayListScreenActivity.songPList;
                if (position < songs.size()-1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);
                //reset adapter
                from = "PlayListSongAdapter";
                position = position;
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("SuggestAdapter")) {
                tops = PersonalFragment.suggestedList;
                if (position < tops.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);
                from = "SuggestAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });

                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("FindingAdapter")) {
                songs = FindingMusicActivity.findingList;
                if (position < tops.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);
                from = "FindingAdapter";
                position = position;
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("HighlightAdapter")) {
                tops = HomeFragment.topList;
                if (position < tops.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                musicService.createMediaPlayer(position);
                from = "HighlightAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
            if (from.equals("SongLibsAdapter")) {
//                musicService.showNotification(R.drawable.ic_baseline_pause_circle_outline_24);
                audios = SongsLibActivity.audioModelList;
                if (position < audios.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(audios.get(position).getPath());
                Log.e("uri", "nextBtnClicked: "+uri );
                musicService.createMediaPlayer(position);
                from = "SongLibsAdapter";
                position = position;
                id = audios.get(position).getId();
                nameSong = audios.get(position).getName();
                artist_name = audios.get(position).getArtist();
                thumbnail = audios.get(position).getImgPath();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                musicService.start();
            }
        } else {
            musicService.stop();
            musicService.release();
            if (from.equals("TopAdapter")) {
                tops = RankingsFragment.topList;
                if (position < tops.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId());
                musicService.createMediaPlayer(position);
                //metaData(uri);
                from = "TopAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

//                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("NewSongHomeAdapter")) {
                songNewsList = HomeFragment.songNewList;
                if (position < songNewsList.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + songNewsList.get(position).getId());
                musicService.createMediaPlayer(position);
                //metaData(uri);
                from = "NewSongHomeAdapter";
                position = position;
                id = songNewsList.get(position).getId();
                nameSong = songNewsList.get(position).getName();
                artist_name = songNewsList.get(position).getSinger().getName();
                thumbnail = songNewsList.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

//                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("SongsOfSingerFavoriteAdapter")) {
                songs = songOfSinger;
                if (position < songs.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId());
                musicService.createMediaPlayer(position);
                //metaData(uri);
                from = "SongsOfSingerFavoriteAdapter";
                position = position;
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

//                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("PLLSongAdapter")) {
                pllSongs = pllSongList;
                if (position < pllSongs.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + pllSongs.get(position).getSong().getId());
                musicService.createMediaPlayer(position);
                //metaData(uri);
                from = "PLLSongAdapter";
                position = position;
                id = pllSongs.get(position).getSong().getId();
                nameSong = pllSongs.get(position).getSong().getName();
                artist_name = pllSongs.get(position).getSong().getSinger().getName();
                thumbnail = pllSongs.get(position).getSong().getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

//                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("FVRSongAdapter")) {
                favoriteList = favoriteListAdapter;
                if (position < favoriteList.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + favoriteList.get(position).getSong().getId());
                musicService.createMediaPlayer(position);
                //metaData(uri);
                from = "FVRSongAdapter";
                position = position;
                id = favoriteList.get(position).getSong().getId();
                nameSong = favoriteList.get(position).getSong().getName();
                artist_name = favoriteList.get(position).getSong().getSinger().getName();
                thumbnail = favoriteList.get(position).getSong().getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

//                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("PlayListSongAdapter")) {
                songs = PlayListScreenActivity.songPList;
                if (position < songs.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId());
                musicService.createMediaPlayer(position);
                //metaData(uri);
                from = "PlayListSongAdapter";
                position = position;
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

//                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("SuggestAdapter")) {
                tops = PersonalFragment.suggestedList;
                if (position < tops.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId());
                musicService.createMediaPlayer(position);
                //noti
                from = "SuggestAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

//                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("FindingAdapter")) {
                songs = FindingMusicActivity.findingList;
                if (position < tops.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId());
                musicService.createMediaPlayer(position);

                //noti
                from = "FindingAdapter";
                position = position;
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

//                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("HighlightAdapter")) {
                tops = HomeFragment.topList;
                if (position < tops.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId());
                musicService.createMediaPlayer(position);

                //noti
                from = "HighlightAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();

//                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("SongLibsAdapter")) {

//                musicService.showNotification(R.drawable.ic_baseline_play_arrow_24);
                audios = SongsLibActivity.audioModelList;
                if (position < audios.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(audios.get(position).getPath());
                musicService.createMediaPlayer(position);
                //noti
                from = "SongLibsAdapter";
                position = position;
                id = audios.get(position).getId();
                nameSong = audios.get(position).getName();
                artist_name = audios.get(position).getArtist();
                thumbnail = audios.get(position).getImgPath();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(musicService.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (musicService != null) {
                            int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                musicService.onCompleted();
//                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
        }

    }

    private void playThreadBtn() {
        playThread = new Thread() {
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

    public void pauseBtnClicked() {
        if (musicService.isPlaying()) {
            btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            musicService.pause();
            seekBar.setMax(musicService.getDuration() / 1000);
            MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this::run, 1000);
                }
            });
        } else {
            btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
            musicService.start();
            seekBar.setMax(musicService.getDuration() / 1000);
            MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this::run, 1000);
                }
            });


        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
        musicService = myBinder.getService();
        musicService.setCallback(this);
        Toast.makeText(this, "Connected " + musicService, Toast.LENGTH_SHORT).show();

        seekBar.setMax(musicService.getDuration() / 1000);
        tvMinutePerSong.setText(convertFormat(musicService.getDuration() / 1000));
        musicService.onCompleted();
    }

    public static void pause(){
        if (musicService.isPlaying()) {
            musicService.pause();
        }
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicService = null;
    }
    @Override
    public void onBackPressed() {
        if (musicService.isPlaying()){
            pauseBtnClicked();
            finish();
        }
        pause();
        finish();
        // super.onBackPressed();
    }
}