package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fmmusic.Adapter.FindingAdapter;
import com.example.fmmusic.Adapter.SuggestedAdapter;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindingMusicActivity extends AppCompatActivity {
    private TextInputLayout tilFinding;
    private TextView tvSongFinding;
    private RecyclerView rcvFinding;
    private List<Song> topList;
    private FindingAdapter findingAdapter;
    private AlertDialog alertDialog;
    public static String DOMAIN_IMG = "https://photo-resize-zmp3.zadn.vn/";
    private int time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_music);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));

        tilFinding = (TextInputLayout) findViewById(R.id.tilFinding);
        tvSongFinding = (TextView) findViewById(R.id.tvSongFinding);
        rcvFinding = (RecyclerView) findViewById(R.id.rcvFinding);
        time = 3;
        tilFinding.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String nameFind =tilFinding.getEditText().getText().toString();
                    topList = new ArrayList<>();
                    String url = "http://ac.mp3.zing.vn/complete?type=artist,song,key,code&num=500&query="+nameFind;
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindingMusicActivity.this);
                    builder.setTitle("Chờ chút");
                    getDataTop(url);
                    findingAdapter = new FindingAdapter(topList);
                    rcvFinding.setAdapter(findingAdapter);
                    rcvFinding.setLayoutManager(new LinearLayoutManager(FindingMusicActivity.this));
                    return true;
                }
                return false;
            }
        });
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
                                    topList.add(mSong);
                                    findingAdapter.notifyDataSetChanged();
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