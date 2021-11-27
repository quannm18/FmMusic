package com.example.fmmusic.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fmmusic.Adapter.SuggestedAdapter;
import com.example.fmmusic.Adapter.TopAdapter;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.Persional.FavoritesLibActivity;
import com.example.fmmusic.View.Activity.Persional.PlaylistActivity;
import com.example.fmmusic.View.Activity.Persional.SingersLibActivity;
import com.example.fmmusic.View.Activity.Persional.SongsLibActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PersonalFragment extends Fragment {
    private RecyclerView rcvSongsLibGoiY;
    private CardView cvPlaylistLibLib;
    private CardView cvSongsLib;
    private CardView cvFavotitesLib;
    private CardView cvSingerLib;
    private SuggestedAdapter suggestedAdapter;
    private List<Top> suggestedList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvSongsLibGoiY = (RecyclerView) view.findViewById(R.id.rcvSongsLibGoiY);
        cvPlaylistLibLib = (CardView) view.findViewById(R.id.cvPlaylistLibLib);
        cvSongsLib = (CardView) view.findViewById(R.id.cvSongsLib);
        cvFavotitesLib = (CardView) view.findViewById(R.id.cvFavotitesLib);
        cvSingerLib = (CardView) view.findViewById(R.id.cvSingerLib);
        suggestedList = new ArrayList<>();
        getDataSuggested();
        suggestedAdapter = new SuggestedAdapter(suggestedList);
        rcvSongsLibGoiY.setAdapter(suggestedAdapter);
        rcvSongsLibGoiY.setLayoutManager(new LinearLayoutManager(getContext()));
        cvSongsLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SongsLibActivity.class));

            }
        });
        cvFavotitesLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FavoritesLibActivity.class));

            }
        });
        cvSingerLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SingersLibActivity.class));

            }
        });
        cvPlaylistLibLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PlaylistActivity.class));

            }
        });
    }
    void getDataSuggested(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url ="https://mp3.zing.vn/xhr/recommend?type=audio&id=ZWBUA8B8";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Singer singer;
                            JSONObject data = response.getJSONObject("data");
                            JSONArray items = data.getJSONArray("items");
                            for (int i = 0; i < items.length()-1; i++) {
                                JSONObject obOfItems = (JSONObject) items.get(i);
                                String id = obOfItems.getString("id");
                                String name = obOfItems.getString("name");
                                String code = obOfItems.getString("code");

                                String artists_names = obOfItems.getString("artists_names");
                                String performer = obOfItems.getString("performer");
                                String link = obOfItems.getString("link");
                                String thumbnail = obOfItems.getString("thumbnail");

                                int duration = Integer.parseInt(obOfItems.getString("duration"));
                                singer = new Singer();
                                JSONArray artists = obOfItems.getJSONArray("artists");
                                if (artists.length()>0){
                                    for (int j = 0; j < artists.length(); j++) {
                                        JSONObject obSinger = artists.getJSONObject(j);

                                        String nameSinger = obSinger.getString("name");
                                        String idOfSinger = obSinger.getString("link");
                                        singer.setName(nameSinger);
                                        singer.setId(idOfSinger);
                                    }

                                }
                                Top top = new Top(id,name,code,singer,artists_names,performer,link,thumbnail,duration,-1,-1,"rankStatus");
                                suggestedList.add(top);
                                suggestedAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Loi"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}