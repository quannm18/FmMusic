package com.example.fmmusic.View.Activity.Personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fmmusic.Adapter.SingerListAdapter;
import com.example.fmmusic.DAO.FavoriteDAO;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SingerFavoriteActivity extends AppCompatActivity {
    private RecyclerView rcvSingerFavorite;
    private FavoriteDAO favoriteDAO;
    private List<Favorite> favoriteList;
    private List<Singer> singerList;
    private SingerListAdapter singerListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer_favorite);
        rcvSingerFavorite = (RecyclerView) findViewById(R.id.rcvSingerFavorite);
        favoriteDAO = new FavoriteDAO(SingerFavoriteActivity.this);
        SharedPreferences sdf = getSharedPreferences("USER_CURRENT", MODE_PRIVATE);
        String username = sdf.getString("USERNAME","");
        favoriteList = favoriteDAO.getFvrFromUsername(username);
        singerList = new ArrayList<>();
        getDataAnyID();
        singerListAdapter = new SingerListAdapter(singerList);

        rcvSingerFavorite.setAdapter(singerListAdapter);
        rcvSingerFavorite.setLayoutManager(new LinearLayoutManager(SingerFavoriteActivity.this,RecyclerView.VERTICAL,false));
    }
    void getDataAnyID() {
        for (int i = 0; i < favoriteList.size(); i++) {
            getInfoFromAPI(favoriteList.get(i));
        }
    }

    void getInfoFromAPI(Favorite favorite) {
        RequestQueue requestQueue = Volley.newRequestQueue(SingerFavoriteActivity.this);
        String url = "https://mp3.zing.vn/xhr/media/get-info?type=audio&id=" + favorite.getSong().getId();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            JSONArray items = data.getJSONArray("artists");
                            JSONObject obOfItems = (JSONObject) items.get(0);
                            String id_artist = obOfItems.getString("id");
                            String name_artist = obOfItems.getString("name");
                            String thumbnail_0 = obOfItems.getString("thumbnail");
                            String thumbnail=null;
                            if (!thumbnail_0.contains("default")&&thumbnail_0.contains("jpeg")){
                                thumbnail = thumbnail_0.substring(0, 33) + ((thumbnail_0.substring(48, thumbnail_0.length())));
                            }
                            if (!thumbnail_0.contains("default")&&thumbnail_0.contains("png")){
                                thumbnail = thumbnail_0.substring(0, 34) + ((thumbnail_0.substring(48, thumbnail_0.length())));
                            }
                            if (thumbnail_0.contains("default")){
                                thumbnail = "https://photo-resize-zmp3.zadn.vn/avatars/7/3/73688444a73a76169d03b689a7e785cf_1404904575.jpg";
                            }
                            Log.e("SingerFavorite", thumbnail);
                            singerList.add(new Singer(thumbnail,name_artist));
                            singerListAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley",error.toString()+" - "+singerList.size());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}