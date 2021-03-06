package com.example.fmmusic.View.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fmmusic.Adapter.SuggestedAdapter;
import com.example.fmmusic.DAO.FavoriteDAO;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.Top;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.Personal.FavoritesLibActivity;
import com.example.fmmusic.View.Activity.Personal.PlaylistActivity;
import com.example.fmmusic.View.Activity.Personal.SingerFavoriteActivity;
import com.example.fmmusic.View.Activity.Personal.SongsLibActivity;
import com.example.fmmusic.View.Activity.SplashActivity;

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
    public static List<Top> suggestedList;
    private FavoriteDAO favoriteDAO;
    private List<Favorite> favoriteList;


    private AppCompatButton btnCancelDelete;
    private AppCompatButton btnYesDelete;
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
        SharedPreferences sdf = getActivity().getSharedPreferences("USER_CURRENT", MODE_PRIVATE);
        String username = sdf.getString("USERNAME","");
        cvSongsLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionsGranted()){
                    Log.e("permission", "Permission already granted");
                }else {
                    takePermission();
                }
                startActivity(new Intent(getContext(), SongsLibActivity.class));

            }
        });
        cvFavotitesLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteList = new ArrayList<>();
                favoriteDAO = new FavoriteDAO(getContext());
                favoriteList = favoriteDAO.getFvrFromUsername(username);
                if (favoriteList.size()>0){
                    startActivity(new Intent(getContext(), FavoritesLibActivity.class));
                }else {
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.check_emty_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    btnYesDelete = (AppCompatButton) dialog.findViewById(R.id.btnYesDelete);
                    btnYesDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            }
        });
        cvSingerLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteList = new ArrayList<>();
                favoriteDAO = new FavoriteDAO(getContext());
                favoriteList = favoriteDAO.getFvrFromUsername(username);
                if (favoriteList.size()>0){
                    startActivity(new Intent(getContext(), SingerFavoriteActivity.class));
                }else {
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.check_emty_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    btnYesDelete = (AppCompatButton) dialog.findViewById(R.id.btnYesDelete);
                    btnYesDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
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
                                String thumbnail_0 = obOfItems.getString("thumbnail");
                                String thumbnail = thumbnail_0.substring(0,33)+((thumbnail_0.substring(47,thumbnail_0.indexOf("?"))));

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
                        Log.e("PersonalVolley",error.toString());
                        startActivity(new Intent(getActivity().getApplicationContext(), SplashActivity.class));
                        getActivity().finish();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    private boolean isPermissionsGranted(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }else {
            int readEnternalStoragePermission =
                    ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE);
            return readEnternalStoragePermission == PackageManager.PERMISSION_GRANTED;
        }
    }
    private void takePermission(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getContext().getPackageName())));
                startActivityForResult(intent,100);
            }catch (Exception e){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent,100);
                e.printStackTrace();
            }
        }else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
        }
    }
}