package com.example.fmmusic.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fmmusic.Adapter.ViewPagerAdapterHome;
import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private TextInputLayout tilFind;
    private CardView cvProfileUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.vpHome);
        tilFind = (TextInputLayout) findViewById(R.id.tilFind);
        cvProfileUser = (CardView) findViewById(R.id.cvProfileUser);

        cvProfileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences sdf = getSharedPreferences("USER_FILE", MODE_PRIVATE);

        SharedPreferences sdfS = getSharedPreferences("USER_CURRENT",MODE_PRIVATE);
        String userName =sdfS.getString("USERNAME","");

        String CheckLogin =sdfS.getString("CHECKLOGIN","");
        Log.e(" ",CheckLogin);
        if(CheckLogin == "SKIPLOGIN")
        {
            findViewById(R.id.imgUser).setVisibility(View.GONE);
            findViewById(R.id.cvProfileUser).setVisibility(View.GONE);
        }
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        setViewPager();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeFragment:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.playListFragment:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.topFragment:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
        tilFind.getEditText().setEnabled(true);
        tilFind.getEditText().setTextIsSelectable(true);
        tilFind.getEditText().setFocusable(false);
        tilFind.getEditText().setFocusableInTouchMode(false);
        tilFind.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,FindingMusicActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setViewPager(){
        ViewPagerAdapterHome viewPagerAdapter_Home = new ViewPagerAdapterHome(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter_Home);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.homeFragment).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.playListFragment).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.topFragment).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}