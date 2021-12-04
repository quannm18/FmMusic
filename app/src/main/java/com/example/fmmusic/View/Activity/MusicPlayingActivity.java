package com.example.fmmusic.View.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.fmmusic.Adapter.FindingAdapter;
import com.example.fmmusic.Adapter.TopAdapter;
import com.example.fmmusic.Adapter.ViewPagerAdapterMusicPlaying;
import com.example.fmmusic.Controller.ActionPlaying;
import com.example.fmmusic.Controller.MusicService;
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

import java.util.ArrayList;
import java.util.List;

public class MusicPlayingActivity extends AppCompatActivity
        implements MediaPlayer.OnCompletionListener, ActionPlaying, ServiceConnection {
    public static String DOMAIN_PLAY = "http://api.mp3.zing.vn/api/streaming/audio/";
    static List<Song> songs = new ArrayList<>();
    static List<AudioModel> audios = new ArrayList<>();
    static List<Top> tops = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    public int position;
    public String from;
    public String id;
    public String nameSong;
    public String artist_name;
    public String thumbnail;
    public String performer;
    public String duration;
    public Handler handler = new Handler();
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
    MusicService musicService;

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
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
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
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
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

        if (from.equals("TopAdapter")) {
            tops = RankingsFragment.topList;
            Log.e("size", tops.size() + "TopAdapter");

            if (!tops.isEmpty()) {

                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
            }
            createMediaPlayer();
        }
        if (from.equals("SuggestAdapter")) {
            tops = PersonalFragment.suggestedList;
            Log.e("size", tops.size() + "SuggestAdapter");

            if (!tops.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
            }
            createMediaPlayer();
        }
        if (from.equals("FindingAdapter")) {

            songs = FindingAdapter.songList;
            if (songs.isEmpty()) {
                Toast.makeText(MusicPlayingActivity.this, "K phải Finding", Toast.LENGTH_SHORT).show();

            } else {
                Log.e("size", songs.size() + "FindingAdapter");
            }
            if (!songs.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
                Log.d("TAG", songs.size()+"FindingAdapter");
            }
            createMediaPlayer();
        }
        if (from.equals("HighlightAdapter")) {
            tops = HomeFragment.topList;
            Log.e("size", tops.size() + "HighlightAdapter");

            if (!tops.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
            }
            createMediaPlayer();
        }
        if (from.equals("SongLibsAdapter")) {
            audios = SongsLibActivity.audioModelList;
            Log.e("size", audios.size() + "SongLibsAdapter");

            if (!audios.isEmpty()) {
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                uri = Uri.parse(audios.get(position).getPath());
            }
            createMediaPlayer();
        }
    }

    public void createMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000);
        tvMinutePerSong.setText(convertFormat(mediaPlayer.getDuration() / 1000));
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
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (from.equals("TopAdapter")) {
                tops = TopAdapter.topList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                from = "TopAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
            if (from.equals("SuggestAdapter")) {
                tops = PersonalFragment.suggestedList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                from = "SuggestAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
            if (from.equals("FindingAdapter")) {
                songs = FindingAdapter.songList;
                if (position > 0) {
                    position--;
                } else {
                    position = songs.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                from = "FindingAdapter";
                position = position;
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
            if (from.equals("HighlightAdapter")) {
                tops = HomeFragment.topList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                from = "HighlightAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
            if (from.equals("SongLibsAdapter")) {
                audios = SongsLibActivity.audioModelList;
                if (position > 0) {
                    position--;
                } else {
                    position = audios.size() - 1;
                }
                uri = Uri.parse(audios.get(position).getPath());
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                from = "SongLibsAdapter";
                position = position;
                id = audios.get(position).getId();
                nameSong = audios.get(position).getName();
                artist_name = audios.get(position).getArtist();
                thumbnail = audios.get(position).getImgPath();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (from.equals("TopAdapter")) {
                tops = RankingsFragment.topList;
                if (position > 0) {
                    position--;
                } else {
                    position = tops.size() - 1;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                //noti
                from = "TopAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
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
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                //noti
                from = "SuggestAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
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
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                //noti
                from = "FindingAdapter";
                position = position;
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
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
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                //noti
                from = "HighlightAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
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
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                from = "SongLibsAdapter";
                position = position;
                id = audios.get(position).getId();
                nameSong = audios.get(position).getName();
                artist_name = audios.get(position).getArtist();
                thumbnail = audios.get(position).getImgPath();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
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
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (from.equals("TopAdapter")) {
                tops = RankingsFragment.topList;
                if (position < tops.size()) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                //reset adapter
                from = "TopAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);

                mediaPlayer.start();
            }
            if (from.equals("SuggestAdapter")) {
                tops = PersonalFragment.suggestedList;
                if (position < tops.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                from = "SuggestAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }

            if (from.equals("FindingAdapter")) {
                songs = FindingAdapter.songList;
                if (position < songs.size() -1) {
                    position++;

                } else {
                    position = 0;
                }
                Log.e("pos", position+"" );
                uri = Uri.parse(DOMAIN_PLAY + songs.get(position).getId() +"/320");
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                from = "FindingAdapter";
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
            if (from.equals("HighlightAdapter")) {
                tops = HomeFragment.topList;
                if (position < tops.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                from = "HighlightAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
            if (from.equals("SongLibsAdapter")) {
                audios = SongsLibActivity.audioModelList;
                if (position < audios.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(audios.get(position).getPath());
                mediaPlayer = MediaPlayer.create(MusicPlayingActivity.this, uri);
                from = "SongLibsAdapter";
                position = position;
                id = audios.get(position).getId();
                nameSong = audios.get(position).getName();
                artist_name = audios.get(position).getArtist();
                thumbnail = audios.get(position).getImgPath();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
                btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
                mediaPlayer.start();
            }
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (from.equals("TopAdapter")) {
                tops = RankingsFragment.topList;
                if (position < tops.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                uri = Uri.parse(DOMAIN_PLAY + tops.get(position).getId());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                //metaData(uri);
                from = "TopAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));

                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
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
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                //noti
                from = "SuggestAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
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
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                //noti
                from = "FindingAdapter";
                position = position;
                id = songs.get(position).getId();
                nameSong = songs.get(position).getName();
                artist_name = songs.get(position).getSinger().getName();
                thumbnail = songs.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
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
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                //noti
                from = "HighlightAdapter";
                position = position;
                id = tops.get(position).getId();
                nameSong = tops.get(position).getName();
                artist_name = tops.get(position).getSinger().getName();
                thumbnail = tops.get(position).getThumbnail();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
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
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                //noti
                from = "SongLibsAdapter";
                position = position;
                id = audios.get(position).getId();
                nameSong = audios.get(position).getName();
                artist_name = audios.get(position).getArtist();
                thumbnail = audios.get(position).getImgPath();
                performer = artist_name;

                viewPagerAdapterMusicPlaying.notifyDataSetChanged();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                tvMinutePerSong.setText(convertFormat(seekBar.getMax()));
                MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
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
        if (mediaPlayer.isPlaying()) {

            btnPlay.setImageResource(R.drawable.play_button_musicplayer);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this::run, 1000);
                }
            });
        } else {
            btnPlay.setImageResource(R.drawable.pause_button_musicplayer);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            MusicPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
//                        tvMinuteRunning.setText(convertFormat(mCurrentPosition));
                    }
                    handler.postDelayed(this::run, 1000);
                }
            });


        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicService.MyBinder myBinder;
        myBinder = (MusicService.MyBinder) service;
        musicService = myBinder.getService();
        Toast.makeText(this, "Connected" + musicService, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicService = null;
    }
}