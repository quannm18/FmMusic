package com.example.fmmusic.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fmmusic.R;
import com.smarteist.autoimageslider.SliderView;

public class HomeFragment extends Fragment {
    private RecyclerView rcvNoiBat;
    private RecyclerView rcvSinger;
    private RecyclerView rcvNews;
    private TextView tvNoiBat;
    private SliderView imgSliderThumbail;
    private ScrollView scrollViewHome;

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

    }

}