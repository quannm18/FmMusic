package com.example.fmmusic.View.Activity.Persional;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fmmusic.Adapter.PlayListSongAdapter;
import com.example.fmmusic.Model.Genres.Genres;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.PlaylistSongs;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayListScreenActivity extends AppCompatActivity {
    private List<PlaylistSongs> playlistSongsList;
    private List<Song> songList;
    private PlayListSongAdapter playListSongAdapter;
    private RecyclerView rcvPlaylist;
    private TextView tvtPlaylistSinger;
//    private String thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_screen);
        rcvPlaylist = (RecyclerView) findViewById(R.id.rcvPlaylist);
        tvtPlaylistSinger = (TextView) findViewById(R.id.tvtPlaylistSinger);

        songList = new ArrayList<>();
        getIntentData();
        getDataAnyID();
        playListSongAdapter = new PlayListSongAdapter(songList);
        rcvPlaylist.setAdapter(playListSongAdapter);
        rcvPlaylist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("song_suggested");
        String from = bundle.getString("from");

        // 1.Nhạc Trẻ
        if (from.equalsIgnoreCase("Nhạc Trẻ")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Tan Vỡ", "ZU96I8D9"));
            playlistSongsList.add(new PlaylistSongs("Anh Lại Làm Em Khóc", "ZU99FBUW"));
            playlistSongsList.add(new PlaylistSongs("Thay Lòng", "ZU7C8FDU\n"));
            playlistSongsList.add(new PlaylistSongs("Cưa Là Đổ", "ZU9WOC9E\n"));
            playlistSongsList.add(new PlaylistSongs("Nhắn Rằng Anh Nhớ Em", "ZU77WA8Z\n"));
            playlistSongsList.add(new PlaylistSongs("Bao Lâu Ta Lại Yêu Một Ngườ", "ZU7UEUD0\n"));
            playlistSongsList.add(new PlaylistSongs("Đông Phai Mờ Dáng Ai", "ZU89DBIU\n"));
            playlistSongsList.add(new PlaylistSongs("Độ Tộc 2", "ZUUUEEIE\n"));
            playlistSongsList.add(new PlaylistSongs("Em Là Con Thuyền Cô Đơ", "ZU7UC9ZC\n"));
            playlistSongsList.add(new PlaylistSongs("Phi Hành Gia", "ZU9CE99E\n"));
            tvtPlaylistSinger.setText("Nhạc Trẻ");
        }
        // 2.Nhạc Trữ Tình
        if (from.equalsIgnoreCase("Nhạc Trữ Tình")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Bậu Ơi Đừng Khóc", "ZOF979FD"));
            playlistSongsList.add(new PlaylistSongs("Vùng Lá Me Bay", "ZWZBWWOZ"));
            playlistSongsList.add(new PlaylistSongs("Liên Khúc Trúc Phương: Nửa Đêm Ngoài Phố", "ZOB0WWOE"));
            playlistSongsList.add(new PlaylistSongs("Ốc Đắng Buồn Ai", "ZO7UBIE9"));
            playlistSongsList.add(new PlaylistSongs("Đêm Buồn Tỉnh Lẻ", "ZWAFCEAE"));
            playlistSongsList.add(new PlaylistSongs("Liên Khúc Bolero Chuyện Tình Các Loài Hoa", "ZOFD6D60"));
            playlistSongsList.add(new PlaylistSongs("Đò Sang Ngang", "ZWB0DFU6"));
            playlistSongsList.add(new PlaylistSongs("Không Bao Giờ Quên Anh", "ZOB0B6FO"));
            playlistSongsList.add(new PlaylistSongs("Ngồi Buồn Nhớ Mẹ", "ZWDCDDB9"));
            playlistSongsList.add(new PlaylistSongs("Mắt Chị", "ZO09Z9E6"));
            tvtPlaylistSinger.setText("Nhạc Trữ Tình");

        }
        // 3.Nhạc Đồng Quê
        if (from.equalsIgnoreCase("Nhạc Đồng Quê")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Chiều Đồng Quê", "ZUOFC78E"));
            playlistSongsList.add(new PlaylistSongs("Thương Nhau Tới Bến", "ZUZAC09I"));
            playlistSongsList.add(new PlaylistSongs("Rồi Tới Luôn", "ZUOZWCUC"));
            playlistSongsList.add(new PlaylistSongs("Yêu Là Cưới", "ZU6IEI66"));
            playlistSongsList.add(new PlaylistSongs("Khuê Mộc Lang", "ZUUECEIC"));
            playlistSongsList.add(new PlaylistSongs("Cô Đơn Dành Cho Ai", "ZOAFI9D9"));
            playlistSongsList.add(new PlaylistSongs("Con Đò Lỡ Hẹn (Lofi Version)", "ZUW8F7EB"));
            playlistSongsList.add(new PlaylistSongs("Túp Lều Vàng", "ZUZBBOFF"));
            playlistSongsList.add(new PlaylistSongs("Hạ Còn Vương Nắng", "ZOAFBWB0"));
            playlistSongsList.add(new PlaylistSongs("Độ Tộc 2", "ZUUUEEIE"));
            tvtPlaylistSinger.setText("Nhạc Đồng Quê");
        }
        // 4.Nhạc Vàng
        if (from.equalsIgnoreCase("Nhạc Vàng")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Mưa Nửa Đêm", "ZWAB0E9E"));
            playlistSongsList.add(new PlaylistSongs("Hai Lối Mộng", "ZWZ9ZD8E"));
            playlistSongsList.add(new PlaylistSongs("Con Đường Xưa Em Đi", "ZWZCC7AW"));
            playlistSongsList.add(new PlaylistSongs("Liên Khúc: Chuyện Chúng Mình & Ngày Sau Sẽ Ra Sao", "ZW78EO9Z"));
            playlistSongsList.add(new PlaylistSongs("Bản Tình Cuối", "ZW60F6OA"));
            playlistSongsList.add(new PlaylistSongs("Đừng Nói Xa Nhau", "ZWZADO09"));
            playlistSongsList.add(new PlaylistSongs("Kiếp Nghèo", "ZW66C7UU"));
            playlistSongsList.add(new PlaylistSongs("Dấu Chân Kỷ Niệm", "ZWZABADO"));
            playlistSongsList.add(new PlaylistSongs("Xin Thời Gian Qua Mau", "ZWZB079A"));
            playlistSongsList.add(new PlaylistSongs("Đêm Tâm Sự", "ZW6ZEI9O"));
            tvtPlaylistSinger.setText("Nhạc Vàng");
        }
        //5.Nhạc Bolero
        if (from.equalsIgnoreCase("Nhạc Bolero")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Mai Lỡ Mình Xa Nhau", "ZW7UBI7U"));
            playlistSongsList.add(new PlaylistSongs("Xin Gọi Nhau Là Cố Nhân", "ZW7876E6"));
            playlistSongsList.add(new PlaylistSongs("Nếu Anh Đừng Hẹn", "ZWZCA9EA"));
            playlistSongsList.add(new PlaylistSongs("Hai Lối Mộng", "ZWZ9ZD8E"));
            playlistSongsList.add(new PlaylistSongs("Đường Tình Đôi Ngã", "ZWZABBZI"));
            playlistSongsList.add(new PlaylistSongs("Lạnh Trọn Đêm Mưa", "ZW786ZAA"));
            playlistSongsList.add(new PlaylistSongs("Trách Ai Vô Tình", "ZW68EBC0"));
            playlistSongsList.add(new PlaylistSongs("Mưa Đêm Ngoại Ô", "ZW7966OI"));
            playlistSongsList.add(new PlaylistSongs("Duyên Phận", "ZWZ986O8"));
            playlistSongsList.add(new PlaylistSongs("Đêm Tâm Sự", "ZW70WWDU"));
            tvtPlaylistSinger.setText("Nhạc Bolero");
        }
        //6.Nhạc Hải Ngoại
        if (from.equalsIgnoreCase("Nhạc Hải Ngoại")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Gọi Tên Ngày Mới", "ZWZEBCC0"));
            playlistSongsList.add(new PlaylistSongs("Đã Không Yêu Thì Thôi", "ZWZABE0Z"));
            playlistSongsList.add(new PlaylistSongs("Hoa Nào Anh Quên", "ZWZ97EZD"));
            playlistSongsList.add(new PlaylistSongs("Chiếc Lá Mùa Đông", "ZWZ9Z6Z7"));
            playlistSongsList.add(new PlaylistSongs("Tình Đã Phai", "ZW60E7IE"));
            playlistSongsList.add(new PlaylistSongs("Em Ở Đâu", "ZWZ9Z6FD"));
            playlistSongsList.add(new PlaylistSongs("999 Đóa Hồng", "ZWZABCEC"));
            playlistSongsList.add(new PlaylistSongs("Yêu Một Người", "ZWZ97EZ8"));
            playlistSongsList.add(new PlaylistSongs("Triệu Đóa Hoa Hồng", "ZWZABZC0"));
            playlistSongsList.add(new PlaylistSongs("Trái Tim Bên Lề", "ZWZ9Z6C7"));
            tvtPlaylistSinger.setText("Nhạc Hải Ngoại");
        }
        //7.Nhạc Quê Hương
        if (from.equalsIgnoreCase("Nhạc Quê Hương")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Bậu Ơi Đừng Khóc", "ZOF979FD"));
            playlistSongsList.add(new PlaylistSongs("Thương Con Chốt Sang Sông", "ZWAED07F"));
            playlistSongsList.add(new PlaylistSongs("Tình Em Tháp Mười", "ZUI08E8B"));
            playlistSongsList.add(new PlaylistSongs("Tình Thắm Duyên Quê", "ZUI08E89"));
            playlistSongsList.add(new PlaylistSongs("Một Lần Lỡ Bước", "ZUI08E8F"));
            playlistSongsList.add(new PlaylistSongs("Biết Nói Gì Đây", "ZUI08E90"));
            playlistSongsList.add(new PlaylistSongs("Vọng Cổ Buồn", "ZUI08E8C"));
            playlistSongsList.add(new PlaylistSongs("Phận Đời Xa Quê", "ZOWU9DAW"));
            playlistSongsList.add(new PlaylistSongs("Đổi Thay", "ZUI08E8A"));
            playlistSongsList.add(new PlaylistSongs("Tìm Em Câu Ví Sông Lam", "ZOWIEZEU"));
            tvtPlaylistSinger.setText("Nhạc Quê Hương");
        }
        //8.Nhạc Trịnh
        if (from.equalsIgnoreCase("Nhạc Trịnh")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Ta Đã Thấy Gì Trong Đêm Nay", "ZWBO0D89"));
            playlistSongsList.add(new PlaylistSongs("Diễm Xưa", "ZU0AUDO9"));
            playlistSongsList.add(new PlaylistSongs("Khúc Thụy Du", "ZU0AUDOB"));
            playlistSongsList.add(new PlaylistSongs("Dấu Chân Địa Đàng", "ZU0AUDO7"));
            playlistSongsList.add(new PlaylistSongs("Một Cõi Đi Về", "ZO76E79F"));
            playlistSongsList.add(new PlaylistSongs("Còn Tuổi Nào Cho Em", "ZOIBF9AO"));
            playlistSongsList.add(new PlaylistSongs("Biển Nhớ", "ZWD0C607"));
            playlistSongsList.add(new PlaylistSongs("Hoa Xuân Ca", "ZWB0OOOW"));
            playlistSongsList.add(new PlaylistSongs("Như Cánh Vạc Bay", "ZO76E7AW"));
            playlistSongsList.add(new PlaylistSongs("Để Gió Cuốn Đi", "ZO76E7AU"));
            tvtPlaylistSinger.setText("Nhạc Trịnh");
        }
        //9.Nhạc Hòa tấu
        if (from.equalsIgnoreCase("Nhạc Hòa Tấu")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("A Comme Amour", "ZWZC8UE0"));
            playlistSongsList.add(new PlaylistSongs("Kiss The Rain", "ZWZD909W"));
            playlistSongsList.add(new PlaylistSongs("Romance De Amour", "ZW6AF8WZ"));
            playlistSongsList.add(new PlaylistSongs("River Flows In You", "ZWZ970I8"));
            playlistSongsList.add(new PlaylistSongs("Marriaged D'amour", "ZW6AF8UF"));
            playlistSongsList.add(new PlaylistSongs("A Love For Life", "ZWZ9E6UC"));
            playlistSongsList.add(new PlaylistSongs("Godfather", "ZW6AF8OW"));
            playlistSongsList.add(new PlaylistSongs("Million Scarlet Roses", "ZW697A68"));
            playlistSongsList.add(new PlaylistSongs("Balada Para Adelina", "ZW6AF8U8"));
            playlistSongsList.add(new PlaylistSongs("Hungarian Sonata", "ZW6ZB896"));
            tvtPlaylistSinger.setText("Nhạc Hòa Tấu");
        }
        //10.Nhạc Không Lời
        if (from.equalsIgnoreCase("Nhạc Không lời")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("아나요 Let You Know (Inst.)", "ZU768W0B"));
            playlistSongsList.add(new PlaylistSongs("Shadow of You (Inst.)", "ZU8DDZ0A"));
            playlistSongsList.add(new PlaylistSongs("Is it me? (Inst.)", "ZU8CADOE"));
            playlistSongsList.add(new PlaylistSongs("As it was a lie (Inst.)", "ZU88FBIE"));
            playlistSongsList.add(new PlaylistSongs("A Long Sleep (Inst.)", "ZU8EWBAO"));
            playlistSongsList.add(new PlaylistSongs("Moon with Starry Night (Inst.)", "ZU97UAUU"));
            playlistSongsList.add(new PlaylistSongs("OMG! (Inst.)", "ZU7C98B6"));
            playlistSongsList.add(new PlaylistSongs("When your tears wet my eyes (Inst.)", "ZU79BZ8W"));
            playlistSongsList.add(new PlaylistSongs("Night Flower (Inst.)", "ZU8CADBC"));
            playlistSongsList.add(new PlaylistSongs("Always, be with you (Inst.)", "ZU8CBCB8"));
            tvtPlaylistSinger.setText("Nhạc Không lời");
        }
        //11.Nhạc Thiền
        if (from.equalsIgnoreCase("Nhạc Thiền")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Phase IV - Relaxation", "ZW68IAIA"));
            playlistSongsList.add(new PlaylistSongs("Von Schnäbeln", "ZW6W8F7U"));
            playlistSongsList.add(new PlaylistSongs("Balancing", "ZW67EEEO"));
            playlistSongsList.add(new PlaylistSongs("Inspired By Nature", "ZW7ICZBZ"));
            playlistSongsList.add(new PlaylistSongs("Yoga Healing", "ZW6IOE78"));
            playlistSongsList.add(new PlaylistSongs("Yoga or Meditate to Ambient Sounds", "ZWAAE07Z"));
            playlistSongsList.add(new PlaylistSongs("From The Earth To The Moon", "ZW7IC6I9"));
            playlistSongsList.add(new PlaylistSongs("Wulu Dream", "ZW68Z0F6"));
            playlistSongsList.add(new PlaylistSongs("Within New Trees", "ZW6ZBW6A"));
            playlistSongsList.add(new PlaylistSongs("Eyes Like An Ocean", "ZW7WAFWA"));
            tvtPlaylistSinger.setText("Nhạc Thiền");
        }
        //12.Nhạc Indie
        if (from.equalsIgnoreCase("Nhạc Indie")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Never End", "ZUW6ZDUB"));
            playlistSongsList.add(new PlaylistSongs("1/90 (九十分之一)", "ZUUAIE0U"));
            playlistSongsList.add(new PlaylistSongs("Ánh Trăng Ngàn Dặm / 明月千里照故人", "ZUWWZ0UE"));
            playlistSongsList.add(new PlaylistSongs("An Hà Kiều / 安和桥", "ZW9FA77I"));
            playlistSongsList.add(new PlaylistSongs("Bày Tỏ Một Lời Tạm Biệt / 表了個拜拜", "ZUI9W86C"));
            playlistSongsList.add(new PlaylistSongs("Thổ Chưởng Phòng / 土掌房", "ZWADZUCA"));
            playlistSongsList.add(new PlaylistSongs("原来这里没有你 / Hóa Ra Nơi Đây Không Có Em", "ZW6UCB78"));
            playlistSongsList.add(new PlaylistSongs("Đi Không / 走马", "ZW9FOZAO"));
            playlistSongsList.add(new PlaylistSongs("Ngọn Gió Trên Đỉnh Đầu Em (你头顶的风)", "ZO9U6U0W"));
            playlistSongsList.add(new PlaylistSongs("Kiếp Sau Không Chắc Còn Có Thể Gặp Được Anh (下辈子不一定还能遇见你) / Guitar Bản (吉他版)", "ZOFBAEAU"));
            tvtPlaylistSinger.setText("Nhạc Indie");
        }
        //13.Nhạc Hiphop
        if (from.equalsIgnoreCase("Nhạc Hiphop")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Alright", "ZUW0UUOO"));
            playlistSongsList.add(new PlaylistSongs("Team", "ZW7OEIUF"));
            playlistSongsList.add(new PlaylistSongs("Better Now", "ZW9C07C6"));
            playlistSongsList.add(new PlaylistSongs("Give It To Me", "ZW60D7WD"));
            playlistSongsList.add(new PlaylistSongs("Godzilla", "ZOAZWCEZ"));
            playlistSongsList.add(new PlaylistSongs("LLC", "ZW9DUUCO"));
            playlistSongsList.add(new PlaylistSongs("No Limit", "ZWAA7IO7"));
            playlistSongsList.add(new PlaylistSongs("Twerkulator", "ZOFDEBE9"));
            playlistSongsList.add(new PlaylistSongs("Wobble Up", "ZWAD0E8U"));
            playlistSongsList.add(new PlaylistSongs("ATM", "ZOE790UC"));
            tvtPlaylistSinger.setText("Nhạc Hiphop");
        }
        //14.Nhạc Dance
        if (from.equalsIgnoreCase("Nhạc Dance")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Ái Nộ", "ZUAE700W"));
            playlistSongsList.add(new PlaylistSongs("Liếc Mắt Đưa Tình", "ZOW06O00"));
            playlistSongsList.add(new PlaylistSongs("Phố Đã Lên Đèn (Masew Remix)", "ZUWEADBF"));
            playlistSongsList.add(new PlaylistSongs("Anh Muốn Đưa Em Về Không?", "ZO7Z068Z"));
            playlistSongsList.add(new PlaylistSongs("72 Phép Thần Thông", "ZO6CZAF6"));
            playlistSongsList.add(new PlaylistSongs("Hai Phút Hơn", "ZWBI9WA8"));
            playlistSongsList.add(new PlaylistSongs("05 (Không Phai) (Masew Remix)", "ZWDUE6WI"));
            playlistSongsList.add(new PlaylistSongs("Sương Hoa Đưa Lối", "ZO8ZF7C7"));
            playlistSongsList.add(new PlaylistSongs("No Boyfriend", "ZW9C88A9"));
            playlistSongsList.add(new PlaylistSongs("Cần Xa", "ZWAEIZCW"));
            tvtPlaylistSinger.setText("Nhạc Dance");
        }
        //15.Nhạc Remix
        if (from.equalsIgnoreCase("Nhạc Remix")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("05 (Không Phai) (Masew Remix)", "ZWDUE6WI"));
            playlistSongsList.add(new PlaylistSongs("Yêu Là Cưới (Remix)", "ZU68E6EC"));
            playlistSongsList.add(new PlaylistSongs("Đúng Người Đúng Thời Điểm (Nemo Remix)", "ZWB0IC9Z"));
            playlistSongsList.add(new PlaylistSongs("Yêu Nhau Nhé Bạn Thân (Remix)", "ZUIOB80O"));
            playlistSongsList.add(new PlaylistSongs("Người Chơi Hệ Đẹp (Cucak Remix)", "ZUUWZBZD"));
            playlistSongsList.add(new PlaylistSongs("Tránh Duyên (Remix)", "ZWADFZ0Z"));
            playlistSongsList.add(new PlaylistSongs("Rồi Tới Luôn (Remix Version)", "ZUU68U78"));
            playlistSongsList.add(new PlaylistSongs("Thôi Miên (Remix)", "ZU0D9IA9"));
            playlistSongsList.add(new PlaylistSongs("Cua (Remix)", "ZUIOWFBO"));
            playlistSongsList.add(new PlaylistSongs("Trời Đày Nhân Duyên (Remix)", "ZOE0EUCE"));
            tvtPlaylistSinger.setText("Nhạc Remix");
        }
        //16.Nhạc Sàn
        if (from.equalsIgnoreCase("Nhạc Sàn")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Rồi Tới Luôn (Bibo Remix)", "ZUZ09UII"));
            playlistSongsList.add(new PlaylistSongs("Yêu Thầm (Remix)", "ZU7OD08U"));
            playlistSongsList.add(new PlaylistSongs("Yêu Là Cưới (Remix)", "ZU68E6EC"));
            playlistSongsList.add(new PlaylistSongs("Đau Bởi Vì Ai (Remix)", "ZWAFZ0DW"));
            playlistSongsList.add(new PlaylistSongs("Dừng Lại Đây Thôi (Remix)", "ZWAEEEE8"));
            playlistSongsList.add(new PlaylistSongs("Cum Cắc Cùm Cum", "ZU70U6BE"));
            playlistSongsList.add(new PlaylistSongs("Badaboo Của Anh (Orinn Remix)", "ZUW6OACI"));
            playlistSongsList.add(new PlaylistSongs("Vô Tình (Vinahouse)", "ZWBWW7EU"));
            playlistSongsList.add(new PlaylistSongs("Sai Cách Yêu (Remix)", "ZUU8FUIU"));
            playlistSongsList.add(new PlaylistSongs("Mất Bao Lâu (Remix)", "ZWAFIF80"));
            tvtPlaylistSinger.setText("Nhạc Sàn");
        }
        //17.Nhạc EDM
        if (from.equalsIgnoreCase("Nhạc EDM")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Phố Đã Lên Đèn (Masew Remix)", "ZUWEADBF"));
            playlistSongsList.add(new PlaylistSongs("Hai Phút Hơn", "ZWBI9WA8"));
            playlistSongsList.add(new PlaylistSongs("Chúng Ta Chỉ Là Đã Từng Yêu (Remix)", "ZO0B0Z06"));
            playlistSongsList.add(new PlaylistSongs("05 (Không Phai) (Masew Remix)", "ZWDUE6WI"));
            playlistSongsList.add(new PlaylistSongs("Nếu Em Hết Thương Rồi (Remix)", "ZWBIFCFC"));
            playlistSongsList.add(new PlaylistSongs("Kiếp Duyên Không Thành (Remix)", "ZOWEW7ZU"));
            playlistSongsList.add(new PlaylistSongs("Đau Bởi Vì Ai (Remix)", "ZWAFZ0DW"));
            playlistSongsList.add(new PlaylistSongs("Nắm (EDM Version)", "ZWBWBI9U"));
            playlistSongsList.add(new PlaylistSongs("Tuyệt Sắc (Remix)", "ZO6II7ZB"));
            playlistSongsList.add(new PlaylistSongs("Kết Duyên", "ZWB00D7E"));
            tvtPlaylistSinger.setText("Nhạc EDM");
        }
        //18.Nhạc Pop
        if (from.equalsIgnoreCase("Nhạc Pop")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("All Too Well (10 Minute Version) (Taylor's Version) (From The Vault)", "ZUB8D8IU"));
            playlistSongsList.add(new PlaylistSongs("Easy On Me", "ZU9IDI0E"));
            playlistSongsList.add(new PlaylistSongs("STAY", "ZUWIB0AW"));
            playlistSongsList.add(new PlaylistSongs("THATS WHAT I WANT", "ZU6BI00F"));
            playlistSongsList.add(new PlaylistSongs("Telepath", "ZUAIB0I6"));
            playlistSongsList.add(new PlaylistSongs("Cold Heart (PNAU Remix)", "ZUU7FI78"));
            playlistSongsList.add(new PlaylistSongs("Ghost", "ZOAZZBZB"));
            playlistSongsList.add(new PlaylistSongs("Angel Baby", "ZU6ZBZ78"));
            playlistSongsList.add(new PlaylistSongs("One Right Now", "ZUAEZ7AO"));
            playlistSongsList.add(new PlaylistSongs("Need to Know", "ZU0BUO8W"));
            tvtPlaylistSinger.setText("Nhạc Pop");
        }
        //19.Nhạc Rock
        if (from.equalsIgnoreCase("Nhạc Rock")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Ngã Tư", "ZWBW67D6"));
            playlistSongsList.add(new PlaylistSongs("Xin Đừng Đi Trước Anh Một Bước", "ZO8A8788"));
            playlistSongsList.add(new PlaylistSongs("Nồng Nàn Cao Nguyên", "ZUBUC7IE"));
            playlistSongsList.add(new PlaylistSongs("Âm Nhạc II", "ZUA0ZZWA"));
            playlistSongsList.add(new PlaylistSongs("Người Đàn Bà Hóa Đá (Cover)", "ZOIAO8ZC"));
            playlistSongsList.add(new PlaylistSongs("Kiêu Hãnh Việt Nam", "ZWB06D9F"));
            playlistSongsList.add(new PlaylistSongs("Hoa Dã Quỳ", "ZWB0WUOB"));
            playlistSongsList.add(new PlaylistSongs("Những Cánh Chim Luôn Biết Tự Do", "ZU8FFBAC"));
            playlistSongsList.add(new PlaylistSongs("Phượt", "ZO60BB6W"));
            playlistSongsList.add(new PlaylistSongs("Ngày Bé, Lúc Lớn", "ZUA0ZZW6"));
            tvtPlaylistSinger.setText("Nhạc Rock");
        }
        //20.Nhạc Karaoke
        if (from.equalsIgnoreCase("Nhạc Karaoke")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Tình Tan", "ZWAFWEBD"));
            playlistSongsList.add(new PlaylistSongs("Nỗi Đau Ai Ngờ (Chỉ Là Câu Hứa)", "ZWBU7IIF"));
            playlistSongsList.add(new PlaylistSongs("Thay Thế", "ZW9C7FFZ"));
            playlistSongsList.add(new PlaylistSongs("Thay Tôi Yêu Cô Ấy", "ZWAEU0EE"));
            playlistSongsList.add(new PlaylistSongs("Đánh Mất Em", "ZWDBBADB"));
            playlistSongsList.add(new PlaylistSongs("Quên Một Người Từng Yêu", "ZWBEC67I"));
            playlistSongsList.add(new PlaylistSongs("Hoa Hải Đường", "ZWDAAU8Z"));
            playlistSongsList.add(new PlaylistSongs("Mỹ Nhân", "ZWAF7I77"));
            playlistSongsList.add(new PlaylistSongs("Nỗi Buồn Sau Tiếng Cười", "ZW7U8668"));
            playlistSongsList.add(new PlaylistSongs("Cầu Vồng Khuyết", "ZWZ99A8C"));
            tvtPlaylistSinger.setText("Nhạc Karaoke");
        }
        //21.Nhạc DJ-Nonstop
        if (from.equalsIgnoreCase("Nhạc DJ-Nonstop")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Nonstop DJ Bay Cùng Mùa Xuân 2016", "ZW7O9D9F"));
            playlistSongsList.add(new PlaylistSongs("Nonstop Xuân 2020 (Remix)", "ZWB06IUE"));
            playlistSongsList.add(new PlaylistSongs("Nếu Biết Trước Tình Phai", "ZWZEBBOF"));
            playlistSongsList.add(new PlaylistSongs("Băng Giá", "ZW79OWIU"));
            playlistSongsList.add(new PlaylistSongs("Vọng Cổ Tình Xuân (Remix)", "ZW78BD9A"));
            playlistSongsList.add(new PlaylistSongs("Trang Giấy Trắng Remix", "ZWZFF7CZ"));
            playlistSongsList.add(new PlaylistSongs("Khói Thuốc Và Người Tình (Remix)", "ZW6DCBUI"));
            playlistSongsList.add(new PlaylistSongs("Mưa Mùa Đông (Remix)", "ZW6ZOUWB"));
            playlistSongsList.add(new PlaylistSongs("Thiên Đường Ảo (Remix)", "ZW6CA8FO"));
            playlistSongsList.add(new PlaylistSongs("Tình Yêu Trong Sáng (Remix)", "ZW7U9IUA"));
            tvtPlaylistSinger.setText("Nhạc DJ-Nonstop");
        }
        //22.Nhạc Thiếu Nhi
        if (from.equalsIgnoreCase("Nhạc Thiếu Nhi")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Ước Mơ Của Mẹ", "ZWCO00ID"));
            playlistSongsList.add(new PlaylistSongs("Chiếc Đèn Ông Sao", "ZU6BCIAI"));
            playlistSongsList.add(new PlaylistSongs("Anh Cá Bảy Màu", "ZWECWOO8"));
            playlistSongsList.add(new PlaylistSongs("Liên Khúc: Con Cò Bé Bé - Con Chim Vành Khuyên", "ZWAFC6U0"));
            playlistSongsList.add(new PlaylistSongs("Cái Patin", "ZWBUB6ZD"));
            playlistSongsList.add(new PlaylistSongs("Đi Học Thêm", "ZO9AAW79"));
            playlistSongsList.add(new PlaylistSongs("Bắc Kim Thang", "ZOO96UAA"));
            playlistSongsList.add(new PlaylistSongs("Trống Cơm", "ZOFC0DEZ"));
            playlistSongsList.add(new PlaylistSongs("Quê Tôi", "ZWCO00IC"));
            playlistSongsList.add(new PlaylistSongs("Bé Chơi Lồng Đèn", "ZU6BCIA0"));
            tvtPlaylistSinger.setText("Nhạc Thiếu Nhi");
        }
        //23.Nhạc R&B
        if (from.equalsIgnoreCase("Nhạc R&B")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Heartbreak Anniversary", "ZWBWI8BI"));
            playlistSongsList.add(new PlaylistSongs("Present", "ZU98OB6U"));
            playlistSongsList.add(new PlaylistSongs("Say So", "ZWBOIWO8"));
            playlistSongsList.add(new PlaylistSongs("Like That", "ZWAFZCI8"));
            playlistSongsList.add(new PlaylistSongs("Go Crazy", "ZWBOW9BC"));
            playlistSongsList.add(new PlaylistSongs("Freak", "ZWCWDC7U"));
            playlistSongsList.add(new PlaylistSongs("Save Your Tears (Remix)", "ZOE8A80U"));
            playlistSongsList.add(new PlaylistSongs("Blinding Lights", "ZO9FZ6FA"));
            playlistSongsList.add(new PlaylistSongs("Juicy", "ZWAE6CIB"));
            playlistSongsList.add(new PlaylistSongs("Save Your Tears", "ZO9FZ6F9"));
            tvtPlaylistSinger.setText("Nhạc R&B");
        }
        //24.Nhạc Blue
        if (from.equalsIgnoreCase("Nhạc Blue")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Santa Baby", "ZW6EB9IE"));
            playlistSongsList.add(new PlaylistSongs("Despacito", "ZOWDBZDU"));
            playlistSongsList.add(new PlaylistSongs("Sunset Painter", "ZWAEA97F"));
            playlistSongsList.add(new PlaylistSongs("Someone You Loved", "ZWEIZBCB"));
            playlistSongsList.add(new PlaylistSongs("God Rest Ye Merry, Gentlemen", "ZUBZEED8"));
            playlistSongsList.add(new PlaylistSongs("Moonlight Sonata", "ZWAF099F"));
            playlistSongsList.add(new PlaylistSongs("Do I Love You", "ZU7DD0W7"));
            playlistSongsList.add(new PlaylistSongs("The Good Earth", "ZWADU9BE"));
            playlistSongsList.add(new PlaylistSongs("I Get A Kick Out Of You", "ZUOEDFB8"));
            playlistSongsList.add(new PlaylistSongs("Angels We Have Heard On High", "ZUBZE90B"));
            tvtPlaylistSinger.setText("Nhạc Thái");
        }
        //25.Nhạc JAZZ
        if (from.equalsIgnoreCase("Nhạc JAZZ")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("California Dreamin'", "ZO9ZDI9W"));
            playlistSongsList.add(new PlaylistSongs("Dream Person", "ZW6WBW6F"));
            playlistSongsList.add(new PlaylistSongs("What A Difference A Day Made", "ZW6UZEE6"));
            playlistSongsList.add(new PlaylistSongs("Tịch Dương Chi Ca", "IW7FO60E"));
            playlistSongsList.add(new PlaylistSongs("Thiên Thiên Khuyết Ca", "IW6O800U"));
            playlistSongsList.add(new PlaylistSongs("错的都是我 / Sai Lầm Vẫn Là Tôi", "ZWZ9ECZ8"));
            playlistSongsList.add(new PlaylistSongs("神話情話/ Thiên Hạ Hữu Tình Nhân (Thần Điêu Đại Hiệp 1995 OST)", "ZWZC0CUI"));
            playlistSongsList.add(new PlaylistSongs("Love You And Love Me", "ZW60DZ06"));
            playlistSongsList.add(new PlaylistSongs("月亮代表我的心/ Ánh Trăng Nói Hộ Lòng Tôi", "ZWZBO0WB"));
            playlistSongsList.add(new PlaylistSongs("Mộng Uyên Ương Hồ Điệp / 新鴛鴦蝴蝶夢", "ZW9D8OA0"));
            tvtPlaylistSinger.setText("Nhạc JAZZ");
        }
        //26.Nhạc Latin
        if (from.equalsIgnoreCase("Nhạc Latin")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Don't Go Yet", "ZUWDAF0Z"));
            playlistSongsList.add(new PlaylistSongs("Tutu (Remix)", "ZWAFI6FF"));
            playlistSongsList.add(new PlaylistSongs("Calma (Alan Walker Remix)", "ZWADCEO6"));
            playlistSongsList.add(new PlaylistSongs("Timber	", "ZWA80C89"));
            playlistSongsList.add(new PlaylistSongs("In Da Getto", "ZUIBICZZ"));
            playlistSongsList.add(new PlaylistSongs("LA FAMA", "ZUB7IZEB"));
            playlistSongsList.add(new PlaylistSongs("telepatía	", "ZOAFWDZD"));
            playlistSongsList.add(new PlaylistSongs("Tutu	", "ZWAEUDIE"));
            playlistSongsList.add(new PlaylistSongs("Baila Conmigo", "ZO9F70Z6"));
            playlistSongsList.add(new PlaylistSongs("Todo De Ti", "ZOFD6BIO"));
            tvtPlaylistSinger.setText("Nhạc Latin");
        }
        //27.Nhạc Việt Nam
        if (from.equalsIgnoreCase("Nhạc Việt Nam")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Bi Hài", "ZUB7U9Z9"));
            playlistSongsList.add(new PlaylistSongs("Phản Bội Chính Mình", "ZUBU607A"));
            playlistSongsList.add(new PlaylistSongs("Ái Nộ", "ZUAE700W"));
            playlistSongsList.add(new PlaylistSongs("3 1 0 7 7 - W/N ft. ( titie,Duongg )	", "ZUAUDZCC\n"));
            playlistSongsList.add(new PlaylistSongs("Cảm Ơn Em Đã Đến", "ZUAW0F0A"));
            playlistSongsList.add(new PlaylistSongs("Mùa Hè Bất Tận", "ZUAOUO09"));
            playlistSongsList.add(new PlaylistSongs("Phai Nhòa Cảm Xúc", "ZUAOZFA7"));
            playlistSongsList.add(new PlaylistSongs("Anh Đưa Em Đi", "ZU98DB06"));
            playlistSongsList.add(new PlaylistSongs("Tan Vỡ\n", "ZU96I8D9\n"));
            playlistSongsList.add(new PlaylistSongs("Anh Lại Làm Em Khóc", "ZU99FBUW\n"));
            tvtPlaylistSinger.setText("Nhạc Việt Nam");
        }
        //28.Nhạc Âu Mỹ
        if (from.equalsIgnoreCase("Nhạc Âu Mỹ")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("A Whole New World (Aladdin's Theme)", "ZO899AOE"));
            playlistSongsList.add(new PlaylistSongs("I Will Always Love You\n", "ZWZCZIFW"));
            playlistSongsList.add(new PlaylistSongs("My Heart Will Go On (Love Theme from \"Titanic\")", "ZW9CD9C9"));
            playlistSongsList.add(new PlaylistSongs("Beauty and the Beast (from the Soundtrack \"Beauty and the Beast\")", "ZWA7ZCA9"));
            playlistSongsList.add(new PlaylistSongs("Circle of Life (From \"The Lion King\"/Soundtrack Version)", "ZWZA0DBE"));
            playlistSongsList.add(new PlaylistSongs("I Have Nothing (Film Version)", "ZW8W7706"));
            playlistSongsList.add(new PlaylistSongs("Take My Breath Away", "ZW60DZFB"));
            playlistSongsList.add(new PlaylistSongs("She's Like the Wind (From \"Dirty Dancing\" Soundtrack)", "ZWA76OC9"));
            playlistSongsList.add(new PlaylistSongs("Because You Loved Me (Theme from \"Up Close and Personal\")", "ZWA7ZCAA"));
            playlistSongsList.add(new PlaylistSongs("Love Theme from \"Romeo and Juliet\" (A Time for Us)", "ZWAF098O"));
            tvtPlaylistSinger.setText("Nhạc Âu Mỹ");
        }
        //29.Nhạc Tiếng Anh
        if (from.equalsIgnoreCase("Nhạc Tiếng Anh")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("No (Galantis Remix)", "ZUBOAW8Z"));
            playlistSongsList.add(new PlaylistSongs("In My Mind (Joel Corry Remix)", "ZU798FOW"));
            playlistSongsList.add(new PlaylistSongs("Cold Heart (PNAU Remix)", "ZUU7FI78"));
            playlistSongsList.add(new PlaylistSongs("Equal in the Darkness (Steve Aoki Character X Version)", "ZUA89IO8"));
            playlistSongsList.add(new PlaylistSongs("Pick Me Up (Billen Ted Remix)", "ZU0BZWAC"));
            playlistSongsList.add(new PlaylistSongs("September (CORSAK Remix)", "ZUUWF98D"));
            playlistSongsList.add(new PlaylistSongs("Giants (Sam Feldt Remix)", "ZODE7I8U"));
            playlistSongsList.add(new PlaylistSongs("You (Topic Remix)", "ZOF9B7Z6"));
            playlistSongsList.add(new PlaylistSongs("Sweet Dreams (DES3ETT Remix)", "ZUODB6FA"));
            playlistSongsList.add(new PlaylistSongs("Chasing Stars (VIP Mix)", "ZU7CIUB8"));
            tvtPlaylistSinger.setText("Nhạc Tiếng Anh");
        }
        //30.Nhạc Nhật
        if (from.equalsIgnoreCase("Nhạc Nhật")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Lemon", "ZWAADCEU"));
            playlistSongsList.add(new PlaylistSongs("たぶん", "ZOF9FEWW"));
            playlistSongsList.add(new PlaylistSongs("夜に駆ける", "ZOFAWUIU"));
            playlistSongsList.add(new PlaylistSongs("DICE", "ZU6A8WDZ"));
            playlistSongsList.add(new PlaylistSongs("Gurenge", "ZWADCCDE"));
            playlistSongsList.add(new PlaylistSongs("Inochi No Akashi", "ZUI9796U"));
            playlistSongsList.add(new PlaylistSongs("Dark Spiral Journey", "ZUW69OWC"));
            playlistSongsList.add(new PlaylistSongs("怪物", "ZOFA0ZDC"));
            playlistSongsList.add(new PlaylistSongs("Kaikai Kitan", "ZOFA0IW7"));
            playlistSongsList.add(new PlaylistSongs(" Into The Night", "ZUI677IA"));
            tvtPlaylistSinger.setText("Nhạc Nhật");
        }
        //31.Nhạc Hoa
        if (from.equalsIgnoreCase("Nhạc Hoa")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs(" Ta Tên Trường An, Người Tên Cố Lý (我叫長安,你叫故里)", "ZOF7IFA6"));
            playlistSongsList.add(new PlaylistSongs("Mang Chủng / 芒种", "ZWAEU7OE"));
            playlistSongsList.add(new PlaylistSongs("Thiên Sơn Tuyết (千山雪)", "ZUAE80UW"));
            playlistSongsList.add(new PlaylistSongs("Trái Tim Không Bình Yên / 心未偏安", "ZUUF0BD7"));
            playlistSongsList.add(new PlaylistSongs("Bức Họa Nguyệt Lão (月老墨畫)", "ZUAOEEUW"));
            playlistSongsList.add(new PlaylistSongs("Ánh Trăng Ngàn Dặm / 明月千里照故人", "ZUAOEEUW"));
            playlistSongsList.add(new PlaylistSongs(" Mộ Hạ / 慕夏", "ZWB0EW67"));
            playlistSongsList.add(new PlaylistSongs("Hỉ / 囍", "ZWBI66U8"));
            playlistSongsList.add(new PlaylistSongs(" Hồng Mã (红马)", "ZOBCDUAC"));
            playlistSongsList.add(new PlaylistSongs(" Chỉ Mặc Giang Nam (纸墨江南)", "ZOED8IUD"));
            tvtPlaylistSinger.setText("Nhạc Hoa");
        }
        //32.Nhạc Thái
        if (from.equalsIgnoreCase("Nhạc Thái")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs(" Falling Into You", "ZWAAD6W0"));
            playlistSongsList.add(new PlaylistSongs("Không Nói Với Em (OST Tuổi Nổi Loạn)", "ZW67BF99"));
            playlistSongsList.add(new PlaylistSongs("Thinking First", "ZWAA7A0C"));
            playlistSongsList.add(new PlaylistSongs(" Phí Tình Yêu (OST Tình Yêu Không Có Lỗi, Lỗi Ở... Bạn Thân)", "ZW7WDEFO"));
            playlistSongsList.add(new PlaylistSongs(" Tôi Khác Biệt (OST Tuổi Nổi Loạn)", "ZW67A9EO"));
            playlistSongsList.add(new PlaylistSongs("Muốn Thời Gian Ngừng Lại (OST Tình Người Duyên Ma)", "ZW670C0O"));
            playlistSongsList.add(new PlaylistSongs(" Tình Yêu Không Có Lỗi", "ZW7WDEFU"));
            playlistSongsList.add(new PlaylistSongs("Splash Out", "ZW67BU0B"));
            playlistSongsList.add(new PlaylistSongs("Ban Deaw Kan", "ZW68FAB8"));
            playlistSongsList.add(new PlaylistSongs(" Nắm Lấy Bàn Tay Em (OST Tình Người Duyên Ma)", "ZW670C0U"));
            tvtPlaylistSinger.setText("Nhạc Thái");
        }
        //33.Nhạc Tập Gym
        if (from.equalsIgnoreCase("Nhạc Tập Gym ")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("One Touch ", "ZWAD9O8B"));
            playlistSongsList.add(new PlaylistSongs("Higher", "ZWAE69BF"));
            playlistSongsList.add(new PlaylistSongs("Starsigns", "ZWAEI8F6"));
            playlistSongsList.add(new PlaylistSongs("Takeaway", "ZWAEOZO9"));
            playlistSongsList.add(new PlaylistSongs("Mother's Daughter", "ZWAD8D9U"));
            playlistSongsList.add(new PlaylistSongs(" Power is Power", "ZWADUCU6"));
            playlistSongsList.add(new PlaylistSongs("Woman Like Me", "ZWAC9ZEU"));
            playlistSongsList.add(new PlaylistSongs("High On Life", "ZW9DIFFZ"));
            playlistSongsList.add(new PlaylistSongs(" Sorry Not Sorry", "ZW807B9D"));
            playlistSongsList.add(new PlaylistSongs("All The Stars", "ZW9A9BD6"));
            tvtPlaylistSinger.setText("Nhạc Tập Gym");
        }
        //34.Nhạc Quán Cà Phê
        if (from.equalsIgnoreCase("Nhạc Quán Cà Phê")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("River Flows in You (Orchestra Version)", "ZOA976OZ"));
            playlistSongsList.add(new PlaylistSongs("Dreamland", "ZWAF9W6I"));
            playlistSongsList.add(new PlaylistSongs("The Moment", "ZWZ9ADUA"));
            playlistSongsList.add(new PlaylistSongs("Kiss The Rain", "ZWZD909W"));
            playlistSongsList.add(new PlaylistSongs("River Flows In You", "ZWZ970I8"));
            playlistSongsList.add(new PlaylistSongs("Forever In Love (Instrumental)", "ZWZ9AD87"));
            playlistSongsList.add(new PlaylistSongs(" Going Home", "ZWZ9ADUI"));
            playlistSongsList.add(new PlaylistSongs("You Raise Me Up", "ZW669ODC"));
            playlistSongsList.add(new PlaylistSongs("Có Khi Nào Rời Xa", "ZW6DZZF9"));
            playlistSongsList.add(new PlaylistSongs("Blue Moon", "ZWB7FB67"));
            tvtPlaylistSinger.setText("Nhạc Quán cà phê");
        }
        //35.Nhạc Phim
        if (from.equalsIgnoreCase("Nhạc Phim")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Shine for You (<Shining like you>Inspirational theme song)", "ZUAAOW6E"));
            playlistSongsList.add(new PlaylistSongs("Center Stage (<Shining like you>Yan Fang & Qiyi LU Love theme song)", "ZUAAOW70"));
//                playlistSongsList.add(new PlaylistSongs("Red Scarf ("Till We Meet Again" Movie Theme Song)", "ZUAOADBC"));
//                playlistSongsList.add(new PlaylistSongs("Round & Round ("Close to You" LINE TV Series Theme Song)", "ZO9F9FBF"));
            playlistSongsList.add(new PlaylistSongs(" Một Đời Một Lần Rung Động (一生一次心一动)", "ZUBD9ACD"));
            playlistSongsList.add(new PlaylistSongs("Luôn Có Người Rời Đi Ở Lại (总有离人留)", "ZUBD9AC9"));
            playlistSongsList.add(new PlaylistSongs("Tuyên Khắc (镌刻)", "ZUBD9ADW"));
            playlistSongsList.add(new PlaylistSongs("Cứ Hôn Đừng Phân Tâm (以无旁骛之吻)", "ZUBD9ABO"));
            playlistSongsList.add(new PlaylistSongs(" Mạc Ly (莫离)", "ZUAU7F0B"));
//                playlistSongsList.add(new PlaylistSongs("You Don't Belong to Me ("More than Blue" TV Series Theme Song)", "ZU900WDI"));
            tvtPlaylistSinger.setText("Nhạc Phim");
        }
        //36.Nhạc Tình Yêu
        if (from.equalsIgnoreCase("Nhạc Tình Yêu")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Mình Là Gì Của Nhau", "ZW78I80B"));
            playlistSongsList.add(new PlaylistSongs("Trời Giấu Trời Mang Đi", "ZWAFIZZ0"));
            playlistSongsList.add(new PlaylistSongs("Bông Hoa Đẹp Nhất", "ZWDZCE80"));
            playlistSongsList.add(new PlaylistSongs("Không Yêu Đừng Gây Thương Nhớ", "ZWAEU7AW"));
            playlistSongsList.add(new PlaylistSongs("Yêu Nhầm Bạn Thân", "ZWBW8IIW"));
            playlistSongsList.add(new PlaylistSongs("Em Gái Mưa", "ZW8IZECW"));
            playlistSongsList.add(new PlaylistSongs("Tri Kỷ", "ZW7UIB89"));
            playlistSongsList.add(new PlaylistSongs("Sau 30", "ZO88W8FU"));
            playlistSongsList.add(new PlaylistSongs("Ta Đã Yêu Chưa Vậy", "ZW80C0BB"));
            playlistSongsList.add(new PlaylistSongs(" Người Mình Yêu Chưa Chắc Đã Yêu Mình", "ZWAD6BE7"));
            tvtPlaylistSinger.setText("Nhạc Tình Yêu");
        }
        //37.Nhạc piano
        if (from.equalsIgnoreCase("Nhạc piano")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("God Is Able", "ZW7WE89B"));
            playlistSongsList.add(new PlaylistSongs("I Surrender", "ZW7WE89D"));
            playlistSongsList.add(new PlaylistSongs("God He Riegns", "ZW7WE89F"));
            playlistSongsList.add(new PlaylistSongs("Lead Me To The Cross", "ZW7WE8A0"));
            playlistSongsList.add(new PlaylistSongs(" Christ Is Enough", "ZW7WE8AI"));
            playlistSongsList.add(new PlaylistSongs("Cornerstone", "ZW7WE8AW"));
            playlistSongsList.add(new PlaylistSongs("Oceans (Where My Feet May Fail)", "ZW7WE8AB"));
            playlistSongsList.add(new PlaylistSongs(" Mighty To Save", "ZW7WE8AC"));
            playlistSongsList.add(new PlaylistSongs("Soothing Baby Music", "ZWACE97B"));
            playlistSongsList.add(new PlaylistSongs("Peaceful Baby Music", "ZWACE97D"));
            tvtPlaylistSinger.setText("Nhạc piano");
        }
        //38.Nhạc Tâm Trạng
        if (from.equalsIgnoreCase("Nhạc Tâm Trạng")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Vẫn Luôn Chờ Em", "ZOFAZ906"));
            playlistSongsList.add(new PlaylistSongs("Anh Vẫn Ở Đây", "ZUB8CDE9"));
            playlistSongsList.add(new PlaylistSongs("Bỏ Lỡ Một Người", "ZWCZDFCA"));
            playlistSongsList.add(new PlaylistSongs("Anh Từng Cố Gắng", "ZO0AFICD"));
            playlistSongsList.add(new PlaylistSongs("Cứ Vội Vàng", "ZWFC6O86"));
            playlistSongsList.add(new PlaylistSongs(" Tình Yêu Hóa Đá", "ZU0DDD7U"));
            playlistSongsList.add(new PlaylistSongs("Gặp Nhưng Không Ở Lại", "ZOWE6F6E"));
            playlistSongsList.add(new PlaylistSongs("Bước Vu Quy", "ZOWZF8DW"));
            playlistSongsList.add(new PlaylistSongs("Sai Lầm Của Anh", "ZWAFWIOZ"));
            playlistSongsList.add(new PlaylistSongs("Suốt Đời Không Xứng", "ZO9AEOF6"));
            tvtPlaylistSinger.setText("Nhạc Tâm Trạng");
        }
        //39.Nhạc Hàn
        if (from.equalsIgnoreCase("Nhạc Hàn")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Day And Night / Love All", "ZW6WWUZB"));
            playlistSongsList.add(new PlaylistSongs("Day By Day", "ZW60FF9E"));
            playlistSongsList.add(new PlaylistSongs("Roly Poly", "ZWZC7D0C"));
            playlistSongsList.add(new PlaylistSongs("TTL (Time To Love)", "ZWZ9FC60"));
            playlistSongsList.add(new PlaylistSongs("We Were In Love", "ZWZEC687"));
            playlistSongsList.add(new PlaylistSongs("Cry Cry", "ZW6WWUZ9"));
            playlistSongsList.add(new PlaylistSongs("Sexy Love", "ZW6WWUZ9"));
            playlistSongsList.add(new PlaylistSongs("We Were In Love", "ZWZED7EA"));
            playlistSongsList.add(new PlaylistSongs("Lovey Dovey", "ZWZED7E9"));
            tvtPlaylistSinger.setText("Nhạc Hàn");
        }
        //40.Nhạc Game
        if (from.equalsIgnoreCase("Nhạc Game")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Monody", "ZW9ABBFC"));
            playlistSongsList.add(new PlaylistSongs("Heroes Tonight", "ZW9ADOCO"));
            playlistSongsList.add(new PlaylistSongs("Believers", "ZOFUDFE8"));
            playlistSongsList.add(new PlaylistSongs("The Ocean", "ZW7UE78B"));
            playlistSongsList.add(new PlaylistSongs("Sign", "ZW9ABBFZ"));
            playlistSongsList.add(new PlaylistSongs("On My Way", "ZWABOA0F"));
            playlistSongsList.add(new PlaylistSongs("Follow Me (Shaun Frank Remix)", "ZWA7ZZWA"));
            playlistSongsList.add(new PlaylistSongs("Let Me Love You (Zedd Remix)", "ZW78BAZE"));
            playlistSongsList.add(new PlaylistSongs("Lily", "ZWA0OA6F"));
            playlistSongsList.add(new PlaylistSongs("The River", "ZW9AE770"));
            tvtPlaylistSinger.setText("Nhạc Game");
        }
        //41.Nhạc Acoutsic
        if (from.equalsIgnoreCase("Nhạc Acoutsic")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Nothing Breaks Like a Heart (Acoustic Version)", "ZWAWZ9ZI"));
            playlistSongsList.add(new PlaylistSongs("Diamonds (Acoustic)", "ZOFDUAZD"));
            playlistSongsList.add(new PlaylistSongs("Break Up Song (Acoustic Version)", "ZWBW8B8Z"));
            playlistSongsList.add(new PlaylistSongs("The Man Who Can't Be Moved (Acoustic)", "ZW9BDBU0"));
            playlistSongsList.add(new PlaylistSongs("Lullaby (Acoustic)", "ZW9BBO69"));
            playlistSongsList.add(new PlaylistSongs("Slow Hands (Acoustic)", "ZW800IZ8"));
            playlistSongsList.add(new PlaylistSongs("I Learned My Lesson (acoustic)", "ZW9CO0E9"));
            playlistSongsList.add(new PlaylistSongs("Naked (Acoustic Version)", "ZW9FO789"));
            playlistSongsList.add(new PlaylistSongs("If You Wanna Love Somebody (Acoustic)", "ZW9CEAF8"));
            playlistSongsList.add(new PlaylistSongs("We Are Young (Acoustic)", "ZW6O80WW"));
            tvtPlaylistSinger.setText("Nhạc Acoutsic");
        }
        //42.Nhạc Rap
        if (from.equalsIgnoreCase("Nhạc Rap")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Phi Hành Gia", "ZU9CE99E"));
            playlistSongsList.add(new PlaylistSongs("Nghe Như Tình Yêu", "ZUWW8WCA"));
            playlistSongsList.add(new PlaylistSongs("Lạc", "ZW67D8W6"));
            playlistSongsList.add(new PlaylistSongs("Bật Nhạc Lên (feat. Harmonie)", "ZU06CBOI"));
            playlistSongsList.add(new PlaylistSongs("BlackJack", "ZOW0OBU8"));
            playlistSongsList.add(new PlaylistSongs("Tay To", "ZUI7WC8C"));
            playlistSongsList.add(new PlaylistSongs("Chưa Từng Vì Nhau", "ZUU9I9F6"));
            playlistSongsList.add(new PlaylistSongs("Xin Đừng Nhấc Máy", "ZOADOC9W"));
            playlistSongsList.add(new PlaylistSongs("Cho Mình Em", "ZOAC7BUF"));
            playlistSongsList.add(new PlaylistSongs("Sao Em Lại Tắt Máy?", "ZO0FW0Z8"));
            tvtPlaylistSinger.setText("Nhạc Rap");
        }
        //43.Nhạc Xuân
        if (from.equalsIgnoreCase("Nhạc Xuân")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Chào Năm Con Trâu", "ZO8ZU9UD"));
            playlistSongsList.add(new PlaylistSongs("Như Hoa Mùa Xuân", "ZW69EIBU"));
            playlistSongsList.add(new PlaylistSongs("Thì Thầm Mùa Xuân", "ZO77ADEA"));
            playlistSongsList.add(new PlaylistSongs("Xuân Ơi Ở Lại Chơi", "ZW7O6EE6"));
            playlistSongsList.add(new PlaylistSongs("Hoa cỏ mùa xuân", "ZW789EFE"));
            playlistSongsList.add(new PlaylistSongs("Đoản Xuân Ca", "ZO8AFA6I"));
            playlistSongsList.add(new PlaylistSongs("Lắng Nghe Mùa Xuân Về", "ZWB0ZUZI"));
            playlistSongsList.add(new PlaylistSongs("Con Bướm Xuân", "ZW696EO9"));
            playlistSongsList.add(new PlaylistSongs("Xuân Sum Vầy", "ZW7O8A90"));
            playlistSongsList.add(new PlaylistSongs("Anh Cho Em Mùa Xuân (Remix)", "ZWB0O0I0"));
            tvtPlaylistSinger.setText("Nhạc Xuân");
        }

    }

    void getDataAnyID() {
        for (int i = 0; i < playlistSongsList.size(); i++) {
            getInfoFromAPI(playlistSongsList.get(i));
        }
    }

    void getInfoFromAPI(PlaylistSongs playlistSongsList) {
        RequestQueue requestQueue = Volley.newRequestQueue(PlayListScreenActivity.this);
        String url = "https://mp3.zing.vn/xhr/media/get-info?type=audio&id=" + playlistSongsList.getPlID();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Genres genres;
                            JSONObject data = response.getJSONObject("data");
                            JSONArray items = data.getJSONArray("artists");
                            JSONObject obOfItems = (JSONObject) items.get(0);
                            String id_artist = obOfItems.getString("id");
                            String name_artist = obOfItems.getString("name");
                            String thumbnail = obOfItems.getString("thumbnail");
//                            Log.e("Huy Len", thumbnail_0);
//                            thumbnail = thumbnail_0.substring(0, 33) + ((thumbnail_0.substring(48, thumbnail_0.length())));
                            Log.e("Huy Ngu", thumbnail);
                            String name = playlistSongsList.getPlName();
                            String id = playlistSongsList.getPlName();
                            Song song = new Song(id, name, new Singer(id_artist, name_artist), thumbnail, 320);
                            songList.add(song);
                            playListSongAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PlayListScreenActivity.this, "Loi" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}