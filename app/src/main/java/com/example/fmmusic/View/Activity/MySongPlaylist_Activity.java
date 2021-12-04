package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fmmusic.Adapter.FavoriteSongsAdapter;
import com.example.fmmusic.Adapter.PLLSongAdapter;
import com.example.fmmusic.Adapter.SingerFavoriteAdapter;
import com.example.fmmusic.Adapter.SuggestedAdapter;
import com.example.fmmusic.DAO.FavoriteDAO;
import com.example.fmmusic.DAO.PLLDAO;
import com.example.fmmusic.DAO.PLLSongDAO;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.Genres.Genres;
import com.example.fmmusic.Model.PLLSong;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.Persional.FavoritesLibActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MySongPlaylist_Activity extends AppCompatActivity {
    private TextView textView;
    private TextView tvUserPLLName;
    private Button button;
    private RecyclerView rcvUserPLL;
    private CardView cvBottomPlayBars;
    private CardView cvThumbnail;
    private ImageView imgThumbnail;
    private ImageView imgPlay;
    private ImageView imgPrevious;
    private ImageView imgNext;
    private ImageView imgPause;
    private TextView tvNameSong;
    private Bundle bundle;
    private List<PLLSong> pllSongList;
    private PLLSongDAO pllSongDAO;
    private PLLSongAdapter pllSongAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_song_playlist);

        textView = (TextView) findViewById(R.id.textView);
        tvUserPLLName = (TextView) findViewById(R.id.tvUserPLLName);
        button = (Button) findViewById(R.id.button);
        rcvUserPLL = (RecyclerView) findViewById(R.id.rcvUserPLL);
        cvBottomPlayBars = (CardView) findViewById(R.id.cvBottomPlayBars);
        cvThumbnail = (CardView) findViewById(R.id.cvThumbnail);
        imgThumbnail = (ImageView) findViewById(R.id.imgThumbnail);
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        imgPrevious = (ImageView) findViewById(R.id.imgPrevious);
        imgNext = (ImageView) findViewById(R.id.imgNext);
        imgPause = (ImageView) findViewById(R.id.imgPause);
        tvNameSong = (TextView) findViewById(R.id.tvNameSong);

        pllSongList = new ArrayList<>();
        pllSongDAO = new PLLSongDAO(MySongPlaylist_Activity.this);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("idPLLFromMyPlaylistAdapter");
        int getIdPLL = bundle.getInt("MyPlayListAdapter");

        pllSongList = pllSongDAO.getSongFromPLL(getIdPLL);
        pllSongAdapter = new PLLSongAdapter(pllSongList);
        pllSongList = new ArrayList<>();
        getDataAnyID();

        rcvUserPLL.setAdapter(pllSongAdapter);
        rcvUserPLL.setLayoutManager(new LinearLayoutManager(MySongPlaylist_Activity.this));

    }
    void getDataAnyID() {
        for (int i = 0; i < pllSongList.size(); i++) {
            getInfoFromAPI(pllSongList.get(i));
        }
    }

    void getInfoFromAPI(PLLSong pllSong) {
        RequestQueue requestQueue = Volley.newRequestQueue(MySongPlaylist_Activity.this);
        String url = "https://mp3.zing.vn/xhr/media/get-info?type=audio&id=" + pllSong.getSong().getId();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Genres genres;
                            JSONObject data = response.getJSONObject("data");
                            JSONArray items = data.getJSONArray("artists");
                            JSONObject obOfItems = (JSONObject) items.get(0);
                            String id_artist = obOfItems.getString("id");
                            String name_artist = obOfItems.getString("name");
                            String thumbnail = obOfItems.getString("thumbnail");
                            Singer singer = new Singer(thumbnail,name_artist);
                            Song song = new Song();

                            pllSongAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MySongPlaylist_Activity.this, "Loi" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}