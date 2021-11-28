package com.example.fmmusic.View.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
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
import com.example.fmmusic.Adapter.HighlightsAdapter;
import com.example.fmmusic.Adapter.NewSongHomeAdapter;
import com.example.fmmusic.Adapter.SingerHomeAdapter;
import com.example.fmmusic.Adapter.SliderAdapter;
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

public class HomeFragment extends Fragment {
    private RecyclerView rcvNoiBat;
    private RecyclerView rcvSinger;
    private RecyclerView rcvNews;
    private TextView tvNoiBat;
    private SliderView imgSliderThumbail;
    private ScrollView scrollViewHome;

    private String[] sliderList;
    public static List<Top> topList;
    public static List<Top> songNewList;
    private List<Singer> singerList;

    private SliderAdapter sliderAdapter;
    private HighlightsAdapter highlightsAdapter;
    private NewSongHomeAdapter songNewsAdapter;
    private SingerHomeAdapter singerHomeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvNoiBat = (RecyclerView) view.findViewById(R.id.rcvNoiBat);
        tvNoiBat = (TextView) view.findViewById(R.id.tvNoiBat);
        imgSliderThumbail = (SliderView) view.findViewById(R.id.imgSliderThumbail);
        rcvSinger = (RecyclerView) view.findViewById(R.id.rcvSinger);
        rcvNews = (RecyclerView) view.findViewById(R.id.rcvNews);
        scrollViewHome = (ScrollView) view.findViewById(R.id.scrollViewHome);
        sliderList = new String[3];
        topList = new ArrayList<>();
        songNewList = new ArrayList<>();
        getDataTop();
        setListSingers();

        sliderAdapter = new SliderAdapter(Arrays.asList(sliderList));
        imgSliderThumbail.setSliderAdapter(sliderAdapter);
        imgSliderThumbail.setAutoCycle(true);

        highlightsAdapter = new HighlightsAdapter(topList);
        songNewsAdapter = new NewSongHomeAdapter(songNewList);
        singerHomeAdapter = new SingerHomeAdapter(singerList);

        RecyclerView.LayoutManager layoutManagerHighlights = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManagerSongNew = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManagerSinger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        rcvNoiBat.setAdapter(highlightsAdapter);
        rcvNoiBat.setLayoutManager(layoutManagerHighlights);

        rcvNews.setAdapter(songNewsAdapter);
        rcvNews.setLayoutManager(layoutManagerSongNew);

        rcvSinger.setAdapter(singerHomeAdapter);
        rcvSinger.setLayoutManager(layoutManagerSinger);
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
                            for (int i = 0; i < 30; i++) {
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


                                if (i==6){
                                    sliderList[0] = thumbnail;
                                }
                                if (i==7){
                                    sliderList[1] = thumbnail;
                                }
                                if (i==8){
                                    sliderList[2] = thumbnail;
                                }

                                Top top = new Top(id,name,code,singer,artists_names,performer,link,thumbnail,duration,total,position,rankStatus);
                                if (i>20){
                                    songNewList.add(top);
                                    songNewsAdapter.notifyDataSetChanged();
                                }
                                if (i<10){
                                    topList.add(top);
                                    highlightsAdapter.notifyDataSetChanged();
                                }
                                sliderAdapter.notifyDataSetChanged();
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

    void setListSingers(){
        singerList = new ArrayList<>();
        singerList.add(new Singer("https://photo-resize-zmp3.zadn.vn/covers/f/a/fa8586e9353a5f80c9d22c63a88d222b_1504987991.jpg","Sơn Tùng MTP"));
        singerList.add(new Singer("https://photo-resize-zmp3.zadn.vn/avatars/4/4/7/4/4474062bb6b56be949da975c6963d4b6.jpg","Trịnh Đình Quang"));
        singerList.add(new Singer("https://photo-resize-zmp3.zadn.vn/avatars/4/3/43d8be33dc00a33132c82adb9d0d3a54_1509355224.jpg","Bích Phương"));
        singerList.add(new Singer("https://photo-resize-zmp3.zadn.vn/avatars/1/3/d/9/13d91c5df0cc3c5ff6536b611cd00b83.jpg","Quân A.P"));
        singerList.add(new Singer("https://photo-resize-zmp3.zadn.vn/avatars/8/3/9/9/8399a5082648b18af768c1d1db87804a.jpg","Amee"));
        singerList.add(new Singer("https://photo-resize-zmp3.zadn.vn/avatars/2/1/3/0/2130334d4358f2727fbd721274791421.jpg","Mr Siro"));
        singerList.add(new Singer("https://photo-resize-zmp3.zadn.vn/avatars/c/0/3/f/c03f60341b00fdc0492dc0469020fcf9.jpg","Hiền Hồ"));
    }
}