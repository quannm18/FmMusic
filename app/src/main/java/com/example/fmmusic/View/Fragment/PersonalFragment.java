package com.example.fmmusic.View.Fragment;

import android.os.Bundle;
<<<<<<< HEAD
=======

import androidx.fragment.app.Fragment;

>>>>>>> origin/quan
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< HEAD
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
=======
import com.example.fmmusic.R;

public class PersonalFragment extends Fragment {
>>>>>>> origin/quan

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< HEAD
=======
        // Inflate the layout for this fragment
>>>>>>> origin/quan
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }
}