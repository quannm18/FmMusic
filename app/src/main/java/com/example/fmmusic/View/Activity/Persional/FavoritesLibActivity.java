package com.example.fmmusic.View.Activity.Persional;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fmmusic.R;
import com.google.android.material.textfield.TextInputLayout;

public class FavoritesLibActivity extends AppCompatActivity {
    private TextInputLayout tilFindFavoriteSong;
    private TextView textView2;
    private RecyclerView rcvlistArtits;
    private TextView textView3;
    private RecyclerView rcvListFavoriteSong;
    private CardView cvBottomPlayBars;
    private CardView cvThumbnail;
    private ImageView imgThumbnail;
    private ImageView imgPlay;
    private ImageView imgPrevious;
    private ImageView imgNext;
    private ImageView imgPause;
    private TextView tvNameSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_lib);
        tilFindFavoriteSong = (TextInputLayout) findViewById(R.id.tilFindFavoriteSong);
        textView2 = (TextView) findViewById(R.id.textView2);
        rcvlistArtits = (RecyclerView) findViewById(R.id.rcvlistArtits);
        textView3 = (TextView) findViewById(R.id.textView3);
        rcvListFavoriteSong = (RecyclerView) findViewById(R.id.rcvListFavoriteSong);
        cvBottomPlayBars = (CardView) findViewById(R.id.cvBottomPlayBars);
        cvThumbnail = (CardView) findViewById(R.id.cvThumbnail);
        imgThumbnail = (ImageView) findViewById(R.id.imgThumbnail);
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        imgPrevious = (ImageView) findViewById(R.id.imgPrevious);
        imgNext = (ImageView) findViewById(R.id.imgNext);
        imgPause = (ImageView) findViewById(R.id.imgPause);
        tvNameSong = (TextView) findViewById(R.id.tvNameSong);




    }
}