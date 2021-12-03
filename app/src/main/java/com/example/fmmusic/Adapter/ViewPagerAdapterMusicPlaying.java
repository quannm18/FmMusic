package com.example.fmmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fmmusic.View.Fragment.SongsDetailFragment;
import com.example.fmmusic.View.Fragment.SongsPlayingFragment;

import java.util.List;

public class ViewPagerAdapterMusicPlaying extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    public ViewPagerAdapterMusicPlaying(@NonNull FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
