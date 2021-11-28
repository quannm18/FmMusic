package com.example.fmmusic.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fmmusic.Adapter.SliderAdapter;
import com.example.fmmusic.Adapter.TopAdapter;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankingsFragment extends Fragment {
    private RecyclerView rcvTop;
    private SliderView sliderTop;
    private TopAdapter topAdapter;
    private SliderAdapter sliderAdapter;
    private String[] sliderList;
    public static List<Top> topList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rankings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTop = (RecyclerView) view.findViewById(R.id.rcvTop);
        sliderTop = (SliderView) view.findViewById(R.id.sliderTop);
        topList = new ArrayList<>();
        sliderList = new String[3];
        getDataTop();
        topAdapter = new TopAdapter(topList);
        rcvTop.setAdapter(topAdapter);
        rcvTop.setLayoutManager(new LinearLayoutManager(getContext()));

        sliderAdapter = new SliderAdapter(Arrays.asList(sliderList));
        sliderTop.setSliderAdapter(sliderAdapter);
        sliderTop.setAutoCycle(true);
        Toast.makeText(getContext(), ""+topList.size(), Toast.LENGTH_SHORT).show();
    }
    void getDataTop(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url ="https://mp3.zing.vn/xhr/chart-realtime?songId=0&videoId=0&albumId=0&chart=song&time=-1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Singer singer;
                            JSONObject data = response.getJSONObject("data");
                            JSONArray items = data.getJSONArray("song");
                            for (int i = 0; i < items.length()-1; i++) {
                                JSONObject obOfItems = (JSONObject) items.get(i);
                                String id = obOfItems.getString("id");
                                String name = obOfItems.getString("name");
                                String code = obOfItems.getString("code");

                                String artists_names = obOfItems.getString("artists_names");
                                String performer = obOfItems.getString("performer");
                                String link = obOfItems.getString("link");
                                String thumbnail_0 = obOfItems.getString("thumbnail");
                                String thumbnail = thumbnail_0.substring(0,33)+((thumbnail_0.substring(47,thumbnail_0.indexOf("?"))));
                                int duration = Integer.parseInt(obOfItems.getString("duration"));
                                int total = Integer.parseInt(obOfItems.getString("total"));
                                String rankStatus = obOfItems.getString("rank_status");
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
                                int position = Integer.parseInt(obOfItems.getString("position"));


                                if (i<3){
                                    sliderList[i] = thumbnail;
                                }
                                Top top = new Top(id,name,code,singer,artists_names,performer,link,thumbnail,duration,total,position,rankStatus);
                                topList.add(top);
                                sliderAdapter.notifyDataSetChanged();
                                topAdapter.notifyDataSetChanged();
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