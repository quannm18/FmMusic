package com.example.fmmusic.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fmmusic.R;
import com.smarteist.autoimageslider.SliderView;

public class RankingsFragment extends Fragment {
    private RecyclerView rcvTop;
    private SliderView sliderTop;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTop = (RecyclerView) view.findViewById(R.id.rcvTop);
        sliderTop = (SliderView) view.findViewById(R.id.sliderTop);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rankings, container, false);
    }
}