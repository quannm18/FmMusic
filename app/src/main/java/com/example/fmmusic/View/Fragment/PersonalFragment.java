package com.example.fmmusic.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.Persional.FavoritesLibActivity;
import com.example.fmmusic.View.Activity.Persional.PlaylistActivity;
import com.example.fmmusic.View.Activity.Persional.SingersLibActivity;
import com.example.fmmusic.View.Activity.Persional.SongsLibActivity;


public class PersonalFragment extends Fragment {
    private RecyclerView rcvSongsLibGoiY;
    private CardView cvPlaylistLibLib;
    private CardView cvSongsLib;
    private CardView cvFavotitesLib;
    private CardView cvSingerLib;

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
}