package com.example.fmmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fmmusic.View.Fragment.HomeFragment;
import com.example.fmmusic.View.Fragment.PersonalFragment;
import com.example.fmmusic.View.Fragment.RankingsFragment;

public class ViewPagerAdapterHome extends FragmentStatePagerAdapter {
    public ViewPagerAdapterHome(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new PersonalFragment();
            case 2:
                return new RankingsFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}

