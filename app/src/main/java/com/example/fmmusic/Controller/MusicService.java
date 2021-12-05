package com.example.fmmusic.Controller;

import static com.example.fmmusic.Controller.ApplicationClass.ACTION_NEXT;
import static com.example.fmmusic.Controller.ApplicationClass.ACTION_PLAY;
import static com.example.fmmusic.Controller.ApplicationClass.ACTION_PREVIOUS;
import static com.example.fmmusic.Controller.ApplicationClass.CHANNEL_ID_2;
import static com.example.fmmusic.View.Activity.MusicPlayingActivity.DOMAIN_PLAY;
import static com.example.fmmusic.View.Activity.MusicPlayingActivity.audios;
import static com.example.fmmusic.View.Activity.MusicPlayingActivity.context;
import static com.example.fmmusic.View.Activity.MusicPlayingActivity.songs;
import static com.example.fmmusic.View.Activity.MusicPlayingActivity.tops;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.example.fmmusic.Model.Songs.AudioModel;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.MusicPlayingActivity;

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
    int myPosition= -1;
    String from;
    ActionPlaying actionPlaying;
    MediaSessionCompat mediaSessionCompat;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaSessionCompat = new MediaSessionCompat(getBaseContext(), "My audio");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionName = intent.getStringExtra("ActionName");
        myPosition = intent.getIntExtra("servicePosition", -1);
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
        }
        if (myPosition != -1) {
            playMedia(myPosition);
        }

            return START_STICKY;
    }

    private void playMedia(int startPosition) {
        position = startPosition ;
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

    public void createMediaPlayer(int positionInner) {
        position = positionInner;
//        Uri uriT = Uri.parse("http://api.mp3.zing.vn/api/streaming/audio/ZW67OIA0/320");
        if (from!=null){
            if (from.equals("TopAdapter")) {
                uri = Uri.parse(DOMAIN_PLAY + topList.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
            }
            if (from.equals("SuggestAdapter")) {
                uri = Uri.parse(DOMAIN_PLAY + topList.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
            }
            if (from.equals("FindingAdapter")) {
                uri = Uri.parse(DOMAIN_PLAY + songList.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
            }
            if (from.equals("HighlightAdapter")) {
                uri = Uri.parse(DOMAIN_PLAY + topList.get(position).getId() + "/320");
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
            }
            if (from.equals("SongLibsAdapter")) {
                uri = Uri.parse(audioModelList.get(position).getPath());
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
            if (mediaPlayer!=null){
                createMediaPlayer(position);
                mediaPlayer.start();
                onCompleted();
            }
        }
    }

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
    public void stopService(){
        Log.d("SERVICE","STOP");
        mediaPlayer.stop();
        //release the media player if you want to
        stopSelf();
    }

    public void setCallback(ActionPlaying actionPlaying){
        this.actionPlaying = actionPlaying;
    }
//    public void showNotification(int playPauseBtn) {
//        Intent intent = new Intent(this, MusicPlayingActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//
//        Intent pauseIntent = new Intent(this, NotificationReceiver.class)
//                .setAction(ACTION_PLAY);
//        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pausePending = PendingIntent
//                .getBroadcast(this, 0, pauseIntent
//                        , PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Intent nextIntent = new Intent(this, NotificationReceiver.class)
//                .setAction(ACTION_NEXT);
//        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent nextPending = PendingIntent
//                .getBroadcast(this, 0, nextIntent
//                        , PendingIntent.FLAG_UPDATE_CURRENT);
//        Intent prevIntent = new Intent(this, NotificationReceiver.class)
//                .setAction(ACTION_PREVIOUS);
//        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent prevPending = PendingIntent
//                .getBroadcast(this, 0, prevIntent
//                        , PendingIntent.FLAG_UPDATE_CURRENT);
//
//        if (from!=null) {
//            if (from.equals("SongLibsAdapter")) {
//                Bitmap thumb = getBitmapFromUri(audioModelList.get(position).getImgPath());
//                if (getBitmapFromUri(audioModelList.get(position).getImgPath()) != null) {
//                    Notification notification =
//                            new NotificationCompat.Builder(this, CHANNEL_ID_2)
//                                    .setSmallIcon(playPauseBtn)
//                                    .setLargeIcon(thumb)
//                                    .setContentTitle(audioModelList.get(position).getName())
//                                    .setContentText(audioModelList.get(position).getArtist())
//                                    .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", prevPending)
//                                    .addAction(playPauseBtn, "Pause", pausePending)
//                                    .addAction(R.drawable.ic_baseline_skip_next_24, "Next", nextPending)
//                                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
//                                            .setMediaSession(mediaSessionCompat.getSessionToken()))
//                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                    .setOnlyAlertOnce(false)
//                                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                                    .build();
//                    startForeground(2, notification);
//                    Toast.makeText(getApplicationContext(), "Show noti", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//    public static Bitmap getBitmapFromURL(String src) {
//        Bitmap bitmap = null;
//        try {
//            bitmap = Glide
//                    .with(context)
//                    .asBitmap()
//                    .load(src)
//                    .submit()
//                    .get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
//    public Bitmap getBitmapFromUri(String uri){
//        Bitmap bitmap =null;
//        try
//        {
//            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , Uri.parse(uri));
//        }
//        catch (Exception e)        {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
}