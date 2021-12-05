package com.example.fmmusic.View.Activity.Persional;

import static com.example.fmmusic.View.Activity.FindingMusicActivity.DOMAIN_IMG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.fmmusic.Adapter.FindingAdapter;
import com.example.fmmusic.Adapter.SongsOfSingerFavoriteAdapter;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.FindingMusicActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SingersLibActivity extends AppCompatActivity {
    private SongsOfSingerFavoriteAdapter singerFavoriteAdapter;
    private ImageView imgSingerLib;
    private TextView tvNameOfSinger;
    private RecyclerView rcvSongOfTheSing;
    public List<Song> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singers_lib);
        imgSingerLib = (ImageView) findViewById(R.id.imgSingerLib);
        tvNameOfSinger = (TextView) findViewById(R.id.tvNameOfSinger);
        rcvSongOfTheSing = (RecyclerView) findViewById(R.id.rcvSongOfTheSing);

        Intent intent = getIntent();
        String singerNameHome = intent.getStringExtra("singer_name_home");
        String singerImgHome = intent.getStringExtra("singer_img_home");
        String url = "http://ac.mp3.zing.vn/complete?type=artist,song,key,code&num=20&query="+singerNameHome;
        Glide.with(SingersLibActivity.this)
                .load(singerImgHome)
                .centerCrop()
                .into(imgSingerLib);
        tvNameOfSinger.setText(singerNameHome);
        songList = new ArrayList<>();
        getDataTop(url);
        singerFavoriteAdapter = new SongsOfSingerFavoriteAdapter(songList);
        rcvSongOfTheSing.setAdapter(singerFavoriteAdapter);
        rcvSongOfTheSing.setLayoutManager(new LinearLayoutManager(SingersLibActivity.this));


    }
    void getDataTop(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            JSONObject item = data.getJSONObject(0);
                            JSONArray song = item.getJSONArray("song");
                            for (int i = 0; i < song.length(); i++) {
                                JSONObject obOfItems = (JSONObject) song.get(i);

                                String thumbnail = obOfItems.getString("thumb");
                                String artists_names = obOfItems.getString("artist");
                                String artists_id = obOfItems.getString("artistIds");
                                int duration = Integer.parseInt(obOfItems.getString("duration"));
                                String id = obOfItems.getString("id");
                                String name = obOfItems.getString("name");

                                Song mSong = new Song(id,name,new Singer(artists_id,artists_names),DOMAIN_IMG+thumbnail,duration);
                                songList.add(mSong);
                                singerFavoriteAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Loi"+error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}