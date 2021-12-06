package com.example.fmmusic.View.Activity.Persional;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
import com.example.fmmusic.Adapter.SingerFavoriteAdapter;
import com.example.fmmusic.Adapter.SingerHomeAdapter;
import com.example.fmmusic.DAO.FavoriteDAO;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.Genres.Genres;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.PlaylistSongs;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoritesLibActivity extends AppCompatActivity {
    private TextInputLayout tilFindFavoriteSong;
    private TextView textView2;
    private RecyclerView rcvlistArtits;
    private TextView textView3;
    private RecyclerView rcvListFavoriteSong;
    private FavoriteDAO favoriteDAO;
    private List<Favorite> favoriteList;
    private FavoriteSongsAdapter favoriteSongsAdapter;
    private List<Singer> singerList;
    private SingerFavoriteAdapter singerFavoriteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_lib);
        tilFindFavoriteSong = (TextInputLayout) findViewById(R.id.tilFindFavoriteSong);
        textView2 = (TextView) findViewById(R.id.textView2);
        rcvlistArtits = (RecyclerView) findViewById(R.id.rcvlistArtits);
        textView3 = (TextView) findViewById(R.id.textView3);
        rcvListFavoriteSong = (RecyclerView) findViewById(R.id.rcvListFavoriteSong);

        favoriteList = new ArrayList<>();
        favoriteDAO = new FavoriteDAO(FavoritesLibActivity.this);
        favoriteList = favoriteDAO.getAllFVR();
        favoriteSongsAdapter = new FavoriteSongsAdapter(favoriteList);

        singerList = new ArrayList<>();
        getDataAnyID();
        singerFavoriteAdapter = new SingerFavoriteAdapter(singerList);

        rcvListFavoriteSong.setAdapter(favoriteSongsAdapter);
        rcvListFavoriteSong.setLayoutManager(new LinearLayoutManager(FavoritesLibActivity.this));

        rcvlistArtits.setAdapter(singerFavoriteAdapter);
        rcvlistArtits.setLayoutManager(new LinearLayoutManager(FavoritesLibActivity.this,RecyclerView.HORIZONTAL,false));
    }
    void getDataAnyID() {
        for (int i = 0; i < favoriteList.size(); i++) {
            getInfoFromAPI(favoriteList.get(i));
        }
    }

    void getInfoFromAPI(Favorite favorite) {
        RequestQueue requestQueue = Volley.newRequestQueue(FavoritesLibActivity.this);
        String url = "https://mp3.zing.vn/xhr/media/get-info?type=audio&id=" + favorite.getSong().getId();
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
//                            Log.e("Huy Len", thumbnail_0);
//                            thumbnail = thumbnail_0.substring(0, 33) + ((thumbnail_0.substring(48, thumbnail_0.length())));
                            Log.e("Huy Ngu", thumbnail);

                            singerList.add(new Singer(thumbnail,name_artist));
                            singerFavoriteAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FavoritesLibActivity.this, "Loi" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}