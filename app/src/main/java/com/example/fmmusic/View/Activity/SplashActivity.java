package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fmmusic.R;

public class SplashActivity extends AppCompatActivity {
    private TextView tvSplash;
    private ImageView imgSplash;
    private LottieAnimationView ltSplashMain;
    private LottieAnimationView ltSplashProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
    }
}