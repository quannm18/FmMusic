package com.example.fmmusic.View.Activity.Personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fmmusic.Adapter.MyPlaylistAdapter;
import com.example.fmmusic.Adapter.PlaylistAdapter;
import com.example.fmmusic.DAO.PLLDAO;
import com.example.fmmusic.Model.PLL;
import com.example.fmmusic.Model.Songs.Playlist;
import com.example.fmmusic.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {
    private ScrollView scrollView2;
    private TextInputLayout tilFindPlaylist;
    private TextView tvtPlaylist;
    private RecyclerView rcvFmMusicPlaylist;
    private RecyclerView rcvPlaylist;
    private ImageView btnAddPlaylist;

    private PlaylistAdapter playlistAdapter;
    private List<Playlist> playlistList;
    private List<Playlist> findingList;
    private List<PLL>listpll;
    private MyPlaylistAdapter myPlaylistAdapter;

    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        btnAddPlaylist = (ImageView) findViewById(R.id.btnAddPlaylist);
        rcvPlaylist = (RecyclerView) findViewById(R.id.rcvPlaylist);
        scrollView2 = (ScrollView) findViewById(R.id.scrollView2);
        tilFindPlaylist = (TextInputLayout) findViewById(R.id.tilFindPlaylist);
        tvtPlaylist = (TextView) findViewById(R.id.tvtPlaylist);
        rcvFmMusicPlaylist = (RecyclerView) findViewById(R.id.rcvFmMusicPlaylist);

        setListData();

        playlistAdapter = new PlaylistAdapter(playlistList);
        rcvFmMusicPlaylist.setAdapter(playlistAdapter);
        rcvFmMusicPlaylist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        SharedPreferences sdf = getSharedPreferences("USER_CURRENT",MODE_PRIVATE);
        String userName =sdf.getString("USERNAME","");

        String CheckLogin =sdf.getString("CHECKLOGIN","");
        Log.e(" ",CheckLogin);
        if(CheckLogin == "SKIPLOGIN")
        {
            tvtPlaylist.setVisibility(View.GONE);
            rcvPlaylist.setVisibility(View.GONE);
            findViewById(R.id.cardView10).setVisibility(View.GONE);
            findViewById(R.id.textView4).setVisibility(View.GONE);
            findViewById(R.id.horizontalScrollView).setVisibility(View.GONE);
        }
        listpll = new ArrayList<>();
        PLLDAO plldao = new PLLDAO(this);
        listpll = plldao.getDataUser(userName);
        myPlaylistAdapter = new MyPlaylistAdapter(listpll);
        rcvPlaylist.setAdapter(myPlaylistAdapter);
        rcvPlaylist.setLayoutManager(new LinearLayoutManager(PlaylistActivity.this,RecyclerView.HORIZONTAL,false));

        btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
            private TextView textView11;
            private TextInputLayout tilNamePlaylist;
            private MaterialButton btnAdd;
            @Override
            public void onClick(View v) {
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.add_playlist_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                SharedPreferences sdf = v.getContext().getSharedPreferences("USER_CURRENT", MODE_PRIVATE);

                textView11 = (TextView) dialog.findViewById(R.id.textView11);
                tilNamePlaylist = (TextInputLayout) dialog.findViewById(R.id.tilNamePlaylist);
                btnAdd = dialog.findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String namePlaylist = tilNamePlaylist.getEditText().getText().toString();
                        PLL pll1 = new PLL();
                        pll1.setNamePll(namePlaylist);
                        pll1.setIdUser(sdf.getString("USERNAME", ""));
                        PLLDAO plldao = new PLLDAO(v.getContext());

                        long checking = plldao.insertPLL(pll1);
                        if (checking > 0) {
                            Toast.makeText(v.getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            listpll.clear();
                            listpll.addAll(plldao.getDataUser(userName));
                            myPlaylistAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(v.getContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();

                        }
                    }

                });
                dialog.show();
            }
        });
        rcvPlaylist.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {


                return false;
            }
        });

        tilFindPlaylist.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String finding = tilFindPlaylist.getEditText().getText().toString();
                    findSong(finding);
                    return true;
                }
                return false;
            }
        });
    }

    private void findSong(String finding) {
        int pos = -1;
        findingList = new ArrayList<>();
        for (int i = 0; i < playlistList.size(); i++) {
            if (playlistList.get(i).getText().equals(finding)
                    ||playlistList.get(i).getText().equalsIgnoreCase(finding)
                    ||playlistList.get(i).getText().contains(finding)){
                findingList.add(playlistList.get(i));
                pos++;
            }
        }
        if (pos==-1){
            playlistAdapter = new PlaylistAdapter(playlistList);
            Toast.makeText(PlaylistActivity.this, "Không tìm thấy "+finding, Toast.LENGTH_SHORT).show();
        }else {
            playlistAdapter = new PlaylistAdapter(findingList);
            Toast.makeText(PlaylistActivity.this, "Đã tìm thấy "+finding, Toast.LENGTH_SHORT).show();
        }
        rcvFmMusicPlaylist.setAdapter(playlistAdapter);
        rcvFmMusicPlaylist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    void setListData(){
        String url1="https://pimobfptedu.github.io/img/";
        playlistList = new ArrayList<>();
        playlistList.add(new Playlist(url1 + "img_nhac_tre.png","Nhạc Trẻ"));
        playlistList.add(new Playlist(url1 + "img_nhac_tru_tinh.png","Nhạc Trữ Tình"));
        playlistList.add(new Playlist(url1 + "nhac_dong_que.png","Nhạc Đồng Quê"));
        playlistList.add(new Playlist(url1 + "nhac_vang.png","Nhạc Vàng"));
        playlistList.add(new Playlist(url1 + "nhac_bolero.png","Nhạc Bolero"));
        playlistList.add(new Playlist(url1 + "nhac_hai_ngoai.png","Nhạc Hải Ngoại"));
        playlistList.add(new Playlist(url1 + "nhac_que_huong.png","Nhạc Quê Hương"));
        playlistList.add(new Playlist(url1 + "nhac_trinh-cong-son.png","Nhạc Trịnh"));
        playlistList.add(new Playlist(url1 + "nhac_hoa_tau.png","Nhạc Hòa tấu"));
        playlistList.add(new Playlist(url1 + "nhac-khong-loi.png","Nhạc Không lời"));
        playlistList.add(new Playlist(url1 + "nhac_thien.png","Nhạc Thiền"));
        playlistList.add(new Playlist(url1 + "nhac_indie.png","Nhạc Indie"));
        playlistList.add(new Playlist(url1 + "nhac_hiphop.png","Nhạc Hiphop"));
        playlistList.add(new Playlist(url1 + "nhac_dance.png","Nhạc Dance"));
        playlistList.add(new Playlist(url1 + "nhac_remix.png","Nhạc Remix"));
        playlistList.add(new Playlist(url1 + "nhac_san.png","Nhạc Sàn"));
        playlistList.add(new Playlist(url1 + "nhac-edm.png","Nhạc EDM"));
        playlistList.add(new Playlist(url1 + "nhac_pop.png","Nhạc Pop"));
        playlistList.add(new Playlist(url1 + "nhac_rock.png","Nhạc Rock"));
        playlistList.add(new Playlist(url1 + "nhac_karaoke.jpg","Nhạc Karaoke"));
        playlistList.add(new Playlist(url1 + "nhac_dj_nonstop.png","Nhạc DJ-Nonstop"));
        playlistList.add(new Playlist(url1 + "nhac_thieu_nhi.png","Nhạc Thiếu Nhi"));
        playlistList.add(new Playlist(url1 + "nhac_rb.png","Nhạc R&B"));
        playlistList.add(new Playlist(url1 + "nhac_blue.png","Nhạc Blue"));
        playlistList.add(new Playlist(url1 + "nhac-jazz.png","Nhạc Jazz"));
        playlistList.add(new Playlist(url1 + "nhac_latin.png","Nhạc Latin"));
        playlistList.add(new Playlist(url1 + "hoatran.jpg","Nhạc Việt Nam"));
        playlistList.add(new Playlist(url1 + "nhac_usuk.png","Nhạc Âu Mỹ"));
        playlistList.add(new Playlist(url1 + "nhac_tieng_anh.jpg","Nhạc Tiếng Anh"));
        playlistList.add(new Playlist(url1 + "nhac_nhat.png","Nhạc Nhật"));
        playlistList.add(new Playlist(url1 + "nhac_hoa.png","Nhạc Hoa"));
        playlistList.add(new Playlist(url1 + "nhac_han.png","Nhạc Hàn"));
        playlistList.add(new Playlist(url1 + "nhac_thai.png","Nhạc Thái"));
        playlistList.add(new Playlist(url1 + "nhac_gum.png","Nhạc Tập Gym"));
        playlistList.add(new Playlist(url1 + "nhac_tam_trang.png","Nhạc Tâm Trạng"));
        playlistList.add(new Playlist(url1 + "xom-cafe.png","Nhạc Quán Cà Phê"));
        playlistList.add(new Playlist(url1 + "nhac_phim.png","Nhạc Phim"));
        playlistList.add(new Playlist(url1 + "maxresdefault.jpg","Nhạc Tình Yêu"));
        playlistList.add(new Playlist(url1 + "Nhac_piano.png","Nhạc Piano"));
        playlistList.add(new Playlist(url1 + "nhac_edm.png","Nhạc Game"));
        playlistList.add(new Playlist(url1 + "nhac_acoustic.png","Nhạc Acoustic"));
        playlistList.add(new Playlist(url1 + "nhac_rap.png","Nhạc Rap"));
        playlistList.add(new Playlist(url1 + "nhac_xuan.png","Nhạc Xuân"));

    }
}