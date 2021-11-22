package com.example.fmmusic.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fmmusic.R;


public class PersonalFragment extends Fragment {
    private CardView cvThuVien;
    private CardView cvPlayList;
    private CardView cvCaSi;
    private RecyclerView rcvBaiHatGoiY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cvThuVien = (CardView) view.findViewById(R.id.cvBaiHat);
        cvPlayList = (CardView) view.findViewById(R.id.cvYeuThich);
        cvCaSi = (CardView) view.findViewById(R.id.cvPlaylist);
        rcvBaiHatGoiY = (RecyclerView) view.findViewById(R.id.rcvBaiHatGoiY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }
}