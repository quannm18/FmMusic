package com.example.fmmusic.View.Activity;

import static com.example.fmmusic.Controller.ApplicationClass.ACTION_PLAY;
import static com.example.fmmusic.Controller.ApplicationClass.ACTION_PREVIOUS;
import static com.example.fmmusic.Controller.ApplicationClass.CHANNEL_ID_2;

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

import com.example.fmmusic.Adapter.ViewPagerAdapterMusicPlaying;
import com.example.fmmusic.Controller.ActionPlaying;
import com.example.fmmusic.Controller.MusicService;
import com.example.fmmusic.Controller.NotificationReceiver;
import com.example.fmmusic.DAO.FavoriteDAO;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.AudioModel;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
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
    public static List<Song> songs = new ArrayList<>();
    public static List<AudioModel> audios = new ArrayList<>();
    public static List<Top> tops = new ArrayList<>();
    public static Uri uri;
    //    public static MediaPlayer mediaPlayer;
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
    MusicService musicService;
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

        mediaSessionCompat = new MediaSessionCompat(getBaseContext(), "My audio");
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
            Log.e("size", tops.size() + "TopAdapter");

            if (!tops.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("SuggestAdapter")) {
            tops = PersonalFragment.suggestedList;
            Log.e("size", tops.size() + "SuggestAdapter");

            if (!tops.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("FindingAdapter")) {
            songs = FindingMusicActivity.findingList;
            if (songs.isEmpty()) {
                Toast.makeText(MusicPlayingActivity.this, "K phải Finding", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("size", songs.size() + "FindingAdapter");
            }
            if (!songs.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("HighlightAdapter")) {
            tops = HomeFragment.topList;
            Log.e("size", tops.size() + "HighlightAdapter");

            if (!tops.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
            }
            startService(intentService);
        }
        if (from.equals("SongLibsAdapter")) {
            audios = SongsLibActivity.audioModelList;
            Log.e("size", audios.size() + "SongLibsAdapter");

            if (!audios.isEmpty()) {
                showNotification(R.drawable.ic_baseline_pause_circle_outline_24);
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

    private void preBtnClicked() {
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
                if (position > 0) {
                    position--;
                } else {
                    position = songs.size() - 1;
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
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
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
                showNotification(R.drawable.ic_baseline_pause_circle_outline_24);
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
            if (from.equals("SongLibsAdapter")) {
                audios = SongsLibActivity.audioModelList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
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
                showNotification(R.drawable.ic_baseline_play_arrow_24);
                musicService.onCompleted();
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
        }
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

    private void nextBtnClicked() {
        if (musicService.isPlaying()) {
            musicService.stop();
            musicService.release();
            if (from.equals("TopAdapter")) {
                tops = RankingsFragment.topList;
                if (position < tops.size()) {
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
                audios = SongsLibActivity.audioModelList;
                if (position < audios.size() - 1) {
                    position++;
                } else {
                    position = 0;
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
                showNotification(R.drawable.ic_baseline_pause_circle_outline_24);
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
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
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
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
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
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
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
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            }
            if (from.equals("SongLibsAdapter")) {
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
                showNotification(R.drawable.ic_baseline_play_arrow_24);
                musicService.onCompleted();
                btnPlay.setImageResource(R.drawable.play_button_musicplayer);
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

            showNotification(R.drawable.ic_baseline_play_arrow_24);
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

            showNotification(R.drawable.ic_baseline_pause_circle_outline_24);
            btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
            musicService.start();
            seekBar.setMax(musicService.getDuration() / 1000);
            MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
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
        Toast.makeText(this, "Connected " + musicService, Toast.LENGTH_SHORT).show();

        seekBar.setMax(musicService.getDuration() / 1000);
        tvMinutePerSong.setText(convertFormat(musicService.getDuration() / 1000));

        musicService.onCompleted();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicService = null;
    }

    void showNotification(int playPauseBtn) {
        Intent intent = new Intent(this, MusicPlayingActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent preIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_PREVIOUS);
        PendingIntent prevPending = PendingIntent.getBroadcast(this, 0, preIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_PLAY);
        PendingIntent pausePending = PendingIntent.getBroadcast(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_PLAY);
        PendingIntent nextPending = PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        String[] picture = null;
//        picture = getImg();
//        Bitmap thumb = BitmapFactory.decodeFile(picture[position]);
        Bitmap thumb = getBitmapFromUri(audios.get(position).getImgPath());
        if (getBitmapFromUri(audios.get(position).getImgPath())!=null){
            Notification notification =
                    new NotificationCompat.Builder(this, CHANNEL_ID_2)
                            .setSmallIcon(playPauseBtn)
                            .setLargeIcon(thumb)
                            .setContentTitle(audios.get(position).getName())
                            .setContentText(audios.get(position).getArtist())
                            .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", prevPending)
                            .addAction(playPauseBtn, "Pause", pausePending)
                            .addAction(R.drawable.ic_baseline_skip_next_24, "Next", nextPending)
                            .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                    .setMediaSession(mediaSessionCompat.getSessionToken()))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setOnlyAlertOnce(true)
                            .build();
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0,notification);
            Toast.makeText(getApplicationContext(), "Show noti", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
            return null;
        }
    }
    public Bitmap getBitmapFromUri(String uri){
        Bitmap bitmap =null;
        try
        {
             bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , Uri.parse(uri));
        }
        catch (Exception e)        {
            e.printStackTrace();
        }
        return bitmap;
    }
}