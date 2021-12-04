package com.example.fmmusic.Controller;

import static com.example.fmmusic.View.Activity.MusicPlayingActivity.DOMAIN_PLAY;
import static com.example.fmmusic.View.Activity.MusicPlayingActivity.audios;
import static com.example.fmmusic.View.Activity.MusicPlayingActivity.songs;
import static com.example.fmmusic.View.Activity.MusicPlayingActivity.tops;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.fmmusic.Model.Songs.AudioModel;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.Model.Songs.Top;

import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    public List<AudioModel> audioModelList = new ArrayList<>();
    public List<Song> songList = new ArrayList<>();
    public List<Top> topList = new ArrayList<>();
    IBinder mBinder = new MyBinder();
    MediaPlayer mediaPlayer;
    Uri uri;
    int position = -1;
    String from;
    ActionPlaying actionPlaying;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionName = intent.getStringExtra("ActionName");
        if (actionName!=null){
            Log.e("sv",actionName);
            switch (actionName){
                case "playPause":
                    if (actionPlaying!=null){
                        Toast.makeText(this, "PlayPause", Toast.LENGTH_SHORT).show();
                        actionPlaying.pauseBtnClicked();
                    }
                    break;
                case "next":
                    if (actionPlaying!=null){
                        Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
                        actionPlaying.nextBtnClicked();
                    }
                    break;
                case "previous":
                    if (actionPlaying!=null){
                        Toast.makeText(this, "Previous", Toast.LENGTH_SHORT).show();
                        actionPlaying.preBtnClicked();
                    }
                    break;
            }
        }
        from = intent.getStringExtra("from");
        if (from!=null){
            if (from.equals("TopAdapter")) {
                topList = tops;
            }
            if (from.equals("SuggestAdapter")) {
                topList = tops;
            }
            if (from.equals("FindingAdapter")) {
                songList = songs;
            }
            if (from.equals("HighlightAdapter")) {
                topList = tops;
            }
            if (from.equals("SongLibsAdapter")) {
                audioModelList = audios;
            }
            int myPosition = intent.getIntExtra("servicePosition", -1);
            if (myPosition != -1) {
                playMedia(myPosition);
            }
        }

            return START_STICKY;
    }

    private void playMedia(int startPosition) {
        position = startPosition;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (mediaPlayer != null) {
                createMediaPlayer(position);
                mediaPlayer.start();
            }
        } else {
            createMediaPlayer(position);
            mediaPlayer.start();
        }
    }

    public void start() {
        mediaPlayer.start();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void release() {
        mediaPlayer.release();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void createMediaPlayer(int pos) {
//        Uri uriT = Uri.parse("http://api.mp3.zing.vn/api/streaming/audio/ZW67OIA0/320");
        if (from!=null){
            if (from.equals("TopAdapter")) {
                uri = Uri.parse(DOMAIN_PLAY + topList.get(pos).getId() + "/320");
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
            }
            if (from.equals("SuggestAdapter")) {
                uri = Uri.parse(DOMAIN_PLAY + topList.get(pos).getId() + "/320");
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
            }
            if (from.equals("FindingAdapter")) {
                uri = Uri.parse(DOMAIN_PLAY + songList.get(pos).getId() + "/320");
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
            }
            if (from.equals("HighlightAdapter")) {
                uri = Uri.parse(DOMAIN_PLAY + topList.get(pos).getId() + "/320");
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
            }
            if (from.equals("SongLibsAdapter")) {
                uri = Uri.parse(audioModelList.get(pos).getPath());
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
            }
        }
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void onCompleted() {
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (actionPlaying != null) {
            actionPlaying.nextBtnClicked();
        }
        createMediaPlayer(position);
        mediaPlayer.start();
        onCompleted();
    }

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
    public void setCallback(ActionPlaying actionPlaying){
        this.actionPlaying = actionPlaying;
    }
}