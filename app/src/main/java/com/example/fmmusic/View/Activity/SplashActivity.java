package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fmmusic.Controller.CheckInternet;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.Persional.SongsLibActivity;

public class SplashActivity extends AppCompatActivity {
    private TextView tvSplash;
    private ImageView imgSplash;
    private LottieAnimationView ltSplashMain;
    private LottieAnimationView ltSplashProgress;

    public static SplashActivity wifiInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        wifiInstance = this;

        tvSplash = (TextView) findViewById(R.id.tvSplash);
        imgSplash = (ImageView) findViewById(R.id.imgSplash);
        ltSplashMain = (LottieAnimationView) findViewById(R.id.ltSplashMain);
        ltSplashProgress = (LottieAnimationView) findViewById(R.id.ltSplashProgress);

        Animation animText = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.text_drop_down);
        tvSplash.startAnimation(animText);
        Animation alpha = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.alpha);
        imgSplash.startAnimation(alpha);
        Animation scale = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.translate_to_left);
        ltSplashMain.startAnimation(scale);
        ltSplashProgress.startAnimation(alpha);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean ret = CheckInternet.isConnected();
                String msg;
                if (ret == true) {
                    msg = "Thiết bị đã kết nối internet";
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                } else {
                    msg = "Thiết bị chưa kết nối internet";
                    startActivity(new Intent(SplashActivity.this, SongsLibActivity.class));
                    finish();
                }
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                System.out.println(msg);
            }
        },3000);

    }
    public static synchronized SplashActivity getInstance() {

        return wifiInstance;
    }
}