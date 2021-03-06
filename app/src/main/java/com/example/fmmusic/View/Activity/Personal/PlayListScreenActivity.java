package com.example.fmmusic.View.Activity.Personal;

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
import com.example.fmmusic.Adapter.SliderAdapter;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.PlaylistSongs;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.R;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayListScreenActivity extends AppCompatActivity {
    public List<PlaylistSongs> playlistSongsList;
    public static List<Song> songPList;
    private PlayListSongAdapter playListSongAdapter;
    private RecyclerView rcvPlaylist;
    private TextView tvtPlaylistSinger;
    private SliderView imgSliderThumbnail;
    private SliderAdapter sliderAdapter;
    private List<String> stringList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_screen);
        rcvPlaylist = (RecyclerView) findViewById(R.id.rcvPlaylist);
        tvtPlaylistSinger = (TextView) findViewById(R.id.tvtPlaylistSinger);
        imgSliderThumbnail = (SliderView) findViewById(R.id.imgSliderThumbnail);

        songPList = new ArrayList<>();
        getIntentData();
        getDataAnyID();
        stringList = new ArrayList<>();
        sliderAdapter = new SliderAdapter(stringList);
        imgSliderThumbnail.setSliderAdapter(sliderAdapter);
        playListSongAdapter = new PlayListSongAdapter(songPList);
        rcvPlaylist.setAdapter(playListSongAdapter);
        rcvPlaylist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }

    void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("song_suggested");
        String from = bundle.getString("from");

        // 1.Nh???c Tr???
        if (from.equalsIgnoreCase("Nh???c Tr???")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Tan V???", "ZU96I8D9"));
            playlistSongsList.add(new PlaylistSongs("Anh L???i L??m Em Kh??c", "ZU99FBUW"));
            playlistSongsList.add(new PlaylistSongs("Thay L??ng", "ZU7C8FDU"));
            playlistSongsList.add(new PlaylistSongs("C??a L?? ?????", "ZU9WOC9E"));
            playlistSongsList.add(new PlaylistSongs("Nh???n R???ng Anh Nh??? Em", "ZU77WA8Z"));
            playlistSongsList.add(new PlaylistSongs("Bao L??u Ta L???i Y??u M???t Ng?????", "ZU7UEUD0"));
            playlistSongsList.add(new PlaylistSongs("????ng Phai M??? D??ng Ai", "ZU89DBIU"));
            playlistSongsList.add(new PlaylistSongs("????? T???c 2", "ZUUUEEIE"));
            playlistSongsList.add(new PlaylistSongs("Em L?? Con Thuy???n C?? ????", "ZU7UC9ZC"));
            playlistSongsList.add(new PlaylistSongs("Phi H??nh Gia", "ZU9CE99E"));
            tvtPlaylistSinger.setText("Nh???c Tr???");
        }
        // 2.Nh???c Tr??? T??nh
        if (from.equalsIgnoreCase("Nh???c Tr??? T??nh")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("B???u ??i ?????ng Kh??c", "ZOF979FD"));
            playlistSongsList.add(new PlaylistSongs("V??ng L?? Me Bay", "ZWZBWWOZ"));
            playlistSongsList.add(new PlaylistSongs("Li??n Kh??c Tr??c Ph????ng: N???a ????m Ngo??i Ph???", "ZOB0WWOE"));
            playlistSongsList.add(new PlaylistSongs("???c ?????ng Bu???n Ai", "ZO7UBIE9"));
            playlistSongsList.add(new PlaylistSongs("????m Bu???n T???nh L???", "ZWAFCEAE"));
            playlistSongsList.add(new PlaylistSongs("Li??n Kh??c Bolero Chuy???n T??nh C??c Lo??i Hoa", "ZOFD6D60"));
            playlistSongsList.add(new PlaylistSongs("???? Sang Ngang", "ZWB0DFU6"));
            playlistSongsList.add(new PlaylistSongs("Kh??ng Bao Gi??? Qu??n Anh", "ZOB0B6FO"));
            playlistSongsList.add(new PlaylistSongs("Ng???i Bu???n Nh??? M???", "ZWDCDDB9"));
            playlistSongsList.add(new PlaylistSongs("M???t Ch???", "ZO09Z9E6"));
            tvtPlaylistSinger.setText("Nh???c Tr??? T??nh");

        }
        // 3.Nh???c ?????ng Qu??
        if (from.equalsIgnoreCase("Nh???c ?????ng Qu??")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Chi???u ?????ng Qu??", "ZUOFC78E"));
            playlistSongsList.add(new PlaylistSongs("Th????ng Nhau T???i B???n", "ZUZAC09I"));
            playlistSongsList.add(new PlaylistSongs("R???i T???i Lu??n", "ZUOZWCUC"));
            playlistSongsList.add(new PlaylistSongs("Y??u L?? C?????i", "ZU6IEI66"));
            playlistSongsList.add(new PlaylistSongs("Khu?? M???c Lang", "ZUUECEIC"));
            playlistSongsList.add(new PlaylistSongs("C?? ????n D??nh Cho Ai", "ZOAFI9D9"));
            playlistSongsList.add(new PlaylistSongs("Con ???? L??? H???n (Lofi Version)", "ZUW8F7EB"));
            playlistSongsList.add(new PlaylistSongs("T??p L???u V??ng", "ZUZBBOFF"));
            playlistSongsList.add(new PlaylistSongs("H??? C??n V????ng N???ng", "ZOAFBWB0"));
            playlistSongsList.add(new PlaylistSongs("????? T???c 2", "ZUUUEEIE"));
            tvtPlaylistSinger.setText("Nh???c ?????ng Qu??");
        }
        // 4.Nh???c V??ng
        if (from.equalsIgnoreCase("Nh???c V??ng")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("M??a N???a ????m", "ZWAB0E9E"));
            playlistSongsList.add(new PlaylistSongs("Hai L???i M???ng", "ZWZ9ZD8E"));
            playlistSongsList.add(new PlaylistSongs("Con ???????ng X??a Em ??i", "ZWZCC7AW"));
            playlistSongsList.add(new PlaylistSongs("Li??n Kh??c: Chuy???n Ch??ng M??nh & Ng??y Sau S??? Ra Sao", "ZW78EO9Z"));
            playlistSongsList.add(new PlaylistSongs("B???n T??nh Cu???i", "ZW60F6OA"));
            playlistSongsList.add(new PlaylistSongs("?????ng N??i Xa Nhau", "ZWZADO09"));
            playlistSongsList.add(new PlaylistSongs("Ki???p Ngh??o", "ZW66C7UU"));
            playlistSongsList.add(new PlaylistSongs("D???u Ch??n K??? Ni???m", "ZWZABADO"));
            playlistSongsList.add(new PlaylistSongs("Xin Th???i Gian Qua Mau", "ZWZB079A"));
            playlistSongsList.add(new PlaylistSongs("????m T??m S???", "ZW6ZEI9O"));
            tvtPlaylistSinger.setText("Nh???c V??ng");
        }
        //5.Nh???c Bolero
        if (from.equalsIgnoreCase("Nh???c Bolero")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Mai L??? M??nh Xa Nhau", "ZW7UBI7U"));
            playlistSongsList.add(new PlaylistSongs("Xin G???i Nhau L?? C??? Nh??n", "ZW7876E6"));
            playlistSongsList.add(new PlaylistSongs("N???u Anh ?????ng H???n", "ZWZCA9EA"));
            playlistSongsList.add(new PlaylistSongs("Hai L???i M???ng", "ZWZ9ZD8E"));
            playlistSongsList.add(new PlaylistSongs("???????ng T??nh ????i Ng??", "ZWZABBZI"));
            playlistSongsList.add(new PlaylistSongs("L???nh Tr???n ????m M??a", "ZW786ZAA"));
            playlistSongsList.add(new PlaylistSongs("Tr??ch Ai V?? T??nh", "ZW68EBC0"));
            playlistSongsList.add(new PlaylistSongs("M??a ????m Ngo???i ??", "ZW7966OI"));
            playlistSongsList.add(new PlaylistSongs("Duy??n Ph???n", "ZWZ986O8"));
            playlistSongsList.add(new PlaylistSongs("????m T??m S???", "ZW70WWDU"));
            tvtPlaylistSinger.setText("Nh???c Bolero");
        }
        //6.Nh???c H???i Ngo???i
        if (from.equalsIgnoreCase("Nh???c H???i Ngo???i")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("G???i T??n Ng??y M???i", "ZWZEBCC0"));
            playlistSongsList.add(new PlaylistSongs("???? Kh??ng Y??u Th?? Th??i", "ZWZABE0Z"));
            playlistSongsList.add(new PlaylistSongs("Hoa N??o Anh Qu??n", "ZWZ97EZD"));
            playlistSongsList.add(new PlaylistSongs("Chi???c L?? M??a ????ng", "ZWZ9Z6Z7"));
            playlistSongsList.add(new PlaylistSongs("T??nh ???? Phai", "ZW60E7IE"));
            playlistSongsList.add(new PlaylistSongs("Em ??? ????u", "ZWZ9Z6FD"));
            playlistSongsList.add(new PlaylistSongs("999 ????a H???ng", "ZWZABCEC"));
            playlistSongsList.add(new PlaylistSongs("Y??u M???t Ng?????i", "ZWZ97EZ8"));
            playlistSongsList.add(new PlaylistSongs("Tri???u ????a Hoa H???ng", "ZWZABZC0"));
            playlistSongsList.add(new PlaylistSongs("Tr??i Tim B??n L???", "ZWZ9Z6C7"));
            tvtPlaylistSinger.setText("Nh???c H???i Ngo???i");
        }
        //7.Nh???c Qu?? H????ng
        if (from.equalsIgnoreCase("Nh???c Qu?? H????ng")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("B???u ??i ?????ng Kh??c", "ZOF979FD"));
            playlistSongsList.add(new PlaylistSongs("Th????ng Con Ch???t Sang S??ng", "ZWAED07F"));
            playlistSongsList.add(new PlaylistSongs("T??nh Em Th??p M?????i", "ZUI08E8B"));
            playlistSongsList.add(new PlaylistSongs("T??nh Th???m Duy??n Qu??", "ZUI08E89"));
            playlistSongsList.add(new PlaylistSongs("M???t L???n L??? B?????c", "ZUI08E8F"));
            playlistSongsList.add(new PlaylistSongs("Bi???t N??i G?? ????y", "ZUI08E90"));
            playlistSongsList.add(new PlaylistSongs("V???ng C??? Bu???n", "ZUI08E8C"));
            playlistSongsList.add(new PlaylistSongs("Ph???n ?????i Xa Qu??", "ZOWU9DAW"));
            playlistSongsList.add(new PlaylistSongs("?????i Thay", "ZUI08E8A"));
            playlistSongsList.add(new PlaylistSongs("T??m Em C??u V?? S??ng Lam", "ZOWIEZEU"));
            tvtPlaylistSinger.setText("Nh???c Qu?? H????ng");
        }
        //8.Nh???c Tr???nh
        if (from.equalsIgnoreCase("Nh???c Tr???nh")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Ta ???? Th???y G?? Trong ????m Nay", "ZWBO0D89"));
            playlistSongsList.add(new PlaylistSongs("Di???m X??a", "ZU0AUDO9"));
            playlistSongsList.add(new PlaylistSongs("Kh??c Th???y Du", "ZU0AUDOB"));
            playlistSongsList.add(new PlaylistSongs("D???u Ch??n ?????a ????ng", "ZU0AUDO7"));
            playlistSongsList.add(new PlaylistSongs("M???t C??i ??i V???", "ZO76E79F"));
            playlistSongsList.add(new PlaylistSongs("C??n Tu???i N??o Cho Em", "ZOIBF9AO"));
            playlistSongsList.add(new PlaylistSongs("Bi???n Nh???", "ZWD0C607"));
            playlistSongsList.add(new PlaylistSongs("Hoa Xu??n Ca", "ZWB0OOOW"));
            playlistSongsList.add(new PlaylistSongs("Nh?? C??nh V???c Bay", "ZO76E7AW"));
            playlistSongsList.add(new PlaylistSongs("????? Gi?? Cu???n ??i", "ZO76E7AU"));
            tvtPlaylistSinger.setText("Nh???c Tr???nh");
        }
        //9.Nh???c H??a t???u
        if (from.equalsIgnoreCase("Nh???c H??a T???u")) {
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
            tvtPlaylistSinger.setText("Nh???c H??a T???u");
        }
        //10.Nh???c Kh??ng L???i
        if (from.equalsIgnoreCase("Nh???c Kh??ng l???i")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("????????? Let You Know (Inst.)", "ZU768W0B"));
            playlistSongsList.add(new PlaylistSongs("Shadow of You (Inst.)", "ZU8DDZ0A"));
            playlistSongsList.add(new PlaylistSongs("Is it me? (Inst.)", "ZU8CADOE"));
            playlistSongsList.add(new PlaylistSongs("As it was a lie (Inst.)", "ZU88FBIE"));
            playlistSongsList.add(new PlaylistSongs("A Long Sleep (Inst.)", "ZU8EWBAO"));
            playlistSongsList.add(new PlaylistSongs("Moon with Starry Night (Inst.)", "ZU97UAUU"));
            playlistSongsList.add(new PlaylistSongs("OMG! (Inst.)", "ZU7C98B6"));
            playlistSongsList.add(new PlaylistSongs("When your tears wet my eyes (Inst.)", "ZU79BZ8W"));
            playlistSongsList.add(new PlaylistSongs("Night Flower (Inst.)", "ZU8CADBC"));
            playlistSongsList.add(new PlaylistSongs("Always, be with you (Inst.)", "ZU8CBCB8"));
            tvtPlaylistSinger.setText("Nh???c Kh??ng l???i");
        }
        //11.Nh???c Thi???n
        if (from.equalsIgnoreCase("Nh???c Thi???n")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Phase IV - Relaxation", "ZW68IAIA"));
            playlistSongsList.add(new PlaylistSongs("Von Schn??beln", "ZW6W8F7U"));
            playlistSongsList.add(new PlaylistSongs("Balancing", "ZW67EEEO"));
            playlistSongsList.add(new PlaylistSongs("Inspired By Nature", "ZW7ICZBZ"));
            playlistSongsList.add(new PlaylistSongs("Yoga Healing", "ZW6IOE78"));
            playlistSongsList.add(new PlaylistSongs("Yoga or Meditate to Ambient Sounds", "ZWAAE07Z"));
            playlistSongsList.add(new PlaylistSongs("From The Earth To The Moon", "ZW7IC6I9"));
            playlistSongsList.add(new PlaylistSongs("Wulu Dream", "ZW68Z0F6"));
            playlistSongsList.add(new PlaylistSongs("Within New Trees", "ZW6ZBW6A"));
            playlistSongsList.add(new PlaylistSongs("Eyes Like An Ocean", "ZW7WAFWA"));
            tvtPlaylistSinger.setText("Nh???c Thi???n");
        }
        //12.Nh???c Indie
        if (from.equalsIgnoreCase("Nh???c Indie")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Never End", "ZUW6ZDUB"));
            playlistSongsList.add(new PlaylistSongs("1/90 (???????????????)", "ZUUAIE0U"));
            playlistSongsList.add(new PlaylistSongs("??nh Tr??ng Ng??n D???m / ?????????????????????", "ZUWWZ0UE"));
            playlistSongsList.add(new PlaylistSongs("An H?? Ki???u / ?????????", "ZW9FA77I"));
            playlistSongsList.add(new PlaylistSongs("B??y T??? M???t L???i T???m Bi???t / ???????????????", "ZUI9W86C"));
            playlistSongsList.add(new PlaylistSongs("Th??? Ch?????ng Ph??ng / ?????????", "ZWADZUCA"));
            playlistSongsList.add(new PlaylistSongs("????????????????????? / H??a Ra N??i ????y Kh??ng C?? Em", "ZW6UCB78"));
            playlistSongsList.add(new PlaylistSongs("??i Kh??ng / ??????", "ZW9FOZAO"));
            playlistSongsList.add(new PlaylistSongs("Ng???n Gi?? Tr??n ?????nh ?????u Em (???????????????)", "ZO9U6U0W"));
            playlistSongsList.add(new PlaylistSongs("Ki???p Sau Kh??ng Ch???c C??n C?? Th??? G???p ???????c Anh (?????????????????????????????????) / Guitar B???n (?????????)", "ZOFBAEAU"));
            tvtPlaylistSinger.setText("Nh???c Indie");
        }
        //13.Nh???c Hiphop
        if (from.equalsIgnoreCase("Nh???c Hiphop")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Alright", "ZUW0UUOO"));
            playlistSongsList.add(new PlaylistSongs("Lemon", "ZW8WUIIB"));
            playlistSongsList.add(new PlaylistSongs("MONTERO (Call Me By Your Name)", "ZOA8669D"));
            playlistSongsList.add(new PlaylistSongs("Give It To Me", "ZW60D7WD"));
            playlistSongsList.add(new PlaylistSongs("Taste", "ZW9CO60D"));
            playlistSongsList.add(new PlaylistSongs("Move Ya Hips", "ZWCIBB66"));
            playlistSongsList.add(new PlaylistSongs("No Limit", "ZWAA7IO7"));
            playlistSongsList.add(new PlaylistSongs("11-Jul", "ZW6EABCA"));
            playlistSongsList.add(new PlaylistSongs("Wobble Up", "ZWAD0E8U"));
            playlistSongsList.add(new PlaylistSongs("No Brainer", "ZW9DIDZC"));
            tvtPlaylistSinger.setText("Nh???c Hiphop");
        }
        //14.Nh???c Dance
        if (from.equalsIgnoreCase("Nh???c Dance")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("??i N???", "ZUAE700W"));
            playlistSongsList.add(new PlaylistSongs("Li???c M???t ????a T??nh", "ZOW06O00"));
            playlistSongsList.add(new PlaylistSongs("Ph??? ???? L??n ????n (Masew Remix)", "ZUWEADBF"));
            playlistSongsList.add(new PlaylistSongs("Anh Mu???n ????a Em V??? Kh??ng?", "ZO7Z068Z"));
            playlistSongsList.add(new PlaylistSongs("72 Ph??p Th???n Th??ng", "ZO6CZAF6"));
            playlistSongsList.add(new PlaylistSongs("Hai Ph??t H??n", "ZWBI9WA8"));
            playlistSongsList.add(new PlaylistSongs("05 (Kh??ng Phai) (Masew Remix)", "ZWDUE6WI"));
            playlistSongsList.add(new PlaylistSongs("S????ng Hoa ????a L???i", "ZO8ZF7C7"));
            playlistSongsList.add(new PlaylistSongs("No Boyfriend", "ZW9C88A9"));
            playlistSongsList.add(new PlaylistSongs("C???n Xa", "ZWAEIZCW"));
            tvtPlaylistSinger.setText("Nh???c Dance");
        }
        //15.Nh???c Remix
        if (from.equalsIgnoreCase("Nh???c Remix")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("05 (Kh??ng Phai) (Masew Remix)", "ZWDUE6WI"));
            playlistSongsList.add(new PlaylistSongs("Y??u L?? C?????i (Remix)", "ZU68E6EC"));
            playlistSongsList.add(new PlaylistSongs("????ng Ng?????i ????ng Th???i ??i???m (Nemo Remix)", "ZWB0IC9Z"));
            playlistSongsList.add(new PlaylistSongs("Y??u Nhau Nh?? B???n Th??n (Remix)", "ZUIOB80O"));
            playlistSongsList.add(new PlaylistSongs("Ng?????i Ch??i H??? ?????p (Cucak Remix)", "ZUUWZBZD"));
            playlistSongsList.add(new PlaylistSongs("Tr??nh Duy??n (Remix)", "ZWADFZ0Z"));
            playlistSongsList.add(new PlaylistSongs("R???i T???i Lu??n (Remix Version)", "ZUU68U78"));
//            playlistSongsList.add(new PlaylistSongs("Th??i Mi??n (Remix)", "ZU0D9IA9"));
            playlistSongsList.add(new PlaylistSongs("Cua (Remix)", "ZUIOWFBO"));
            playlistSongsList.add(new PlaylistSongs("Tr???i ????y Nh??n Duy??n (Remix)", "ZOE0EUCE"));
            tvtPlaylistSinger.setText("Nh???c Remix");
        }
        //16.Nh???c S??n
        if (from.equalsIgnoreCase("Nh???c S??n")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("R???i T???i Lu??n (Bibo Remix)", "ZUZ09UII"));
            playlistSongsList.add(new PlaylistSongs("Y??u Th???m (Remix)", "ZU7OD08U"));
            playlistSongsList.add(new PlaylistSongs("Y??u L?? C?????i (Remix)", "ZU68E6EC"));
            playlistSongsList.add(new PlaylistSongs("??au B???i V?? Ai (Remix)", "ZWAFZ0DW"));
            playlistSongsList.add(new PlaylistSongs("D???ng L???i ????y Th??i (Remix)", "ZWAEEEE8"));
            playlistSongsList.add(new PlaylistSongs("Cum C???c C??m Cum", "ZU70U6BE"));
            playlistSongsList.add(new PlaylistSongs("Badaboo C???a Anh (Orinn Remix)", "ZUW6OACI"));
            playlistSongsList.add(new PlaylistSongs("V?? T??nh (Vinahouse)", "ZWBWW7EU"));
            playlistSongsList.add(new PlaylistSongs("Sai C??ch Y??u (Remix)", "ZUU8FUIU"));
            playlistSongsList.add(new PlaylistSongs("M???t Bao L??u (Remix)", "ZWAFIF80"));
            tvtPlaylistSinger.setText("Nh???c S??n");
        }
        //17.Nh???c EDM
        if (from.equalsIgnoreCase("Nh???c EDM")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Ph??? ???? L??n ????n (Masew Remix)", "ZUWEADBF"));
            playlistSongsList.add(new PlaylistSongs("Hai Ph??t H??n", "ZWBI9WA8"));
            playlistSongsList.add(new PlaylistSongs("Ch??ng Ta Ch??? L?? ???? T???ng Y??u (Remix)", "ZO0B0Z06"));
            playlistSongsList.add(new PlaylistSongs("05 (Kh??ng Phai) (Masew Remix)", "ZWDUE6WI"));
            playlistSongsList.add(new PlaylistSongs("N???u Em H???t Th????ng R???i (Remix)", "ZWBIFCFC"));
            playlistSongsList.add(new PlaylistSongs("Ki???p Duy??n Kh??ng Th??nh (Remix)", "ZOWEW7ZU"));
            playlistSongsList.add(new PlaylistSongs("??au B???i V?? Ai (Remix)", "ZWAFZ0DW"));
            playlistSongsList.add(new PlaylistSongs("N???m (EDM Version)", "ZWBWBI9U"));
            playlistSongsList.add(new PlaylistSongs("Tuy???t S???c (Remix)", "ZO6II7ZB"));
            playlistSongsList.add(new PlaylistSongs("K???t Duy??n", "ZWB00D7E"));
            tvtPlaylistSinger.setText("Nh???c EDM");
        }
        //18.Nh???c Pop
        if (from.equalsIgnoreCase("Nh???c Pop")) {
            playlistSongsList = new ArrayList<>();
//            playlistSongsList.add(new PlaylistSongs("All Too Well (10 Minute Version) (Taylor's Version) (From The Vault)", "ZUB8D8IU"));
            playlistSongsList.add(new PlaylistSongs("Easy On Me", "ZU9IDI0E"));
            playlistSongsList.add(new PlaylistSongs("STAY", "ZUWIB0AW"));
            playlistSongsList.add(new PlaylistSongs("THATS WHAT I WANT", "ZU6BI00F"));
            playlistSongsList.add(new PlaylistSongs("Working", "ZUIIU7W7"));
            playlistSongsList.add(new PlaylistSongs("Hi-Lo", "ZU80I0IC"));
            playlistSongsList.add(new PlaylistSongs("No", "ZUBOFZ9C"));
            playlistSongsList.add(new PlaylistSongs("Hopeless Romantic", "ZU9IZOC0"));
            playlistSongsList.add(new PlaylistSongs("Don't Go Yet", "ZUWDAF0Z"));
            playlistSongsList.add(new PlaylistSongs("Need to Know", "ZU0BUO8W"));
            tvtPlaylistSinger.setText("Nh???c Pop");
        }
        //19.Nh???c Rock
        if (from.equalsIgnoreCase("Nh???c Rock")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Ng?? T??", "ZWBW67D6"));
            playlistSongsList.add(new PlaylistSongs("Xin ?????ng ??i Tr?????c Anh M???t B?????c", "ZO8A8788"));
            playlistSongsList.add(new PlaylistSongs("N???ng N??n Cao Nguy??n", "ZUBUC7IE"));
            playlistSongsList.add(new PlaylistSongs("??m Nh???c II", "ZUA0ZZWA"));
            playlistSongsList.add(new PlaylistSongs("Ng?????i ????n B?? H??a ???? (Cover)", "ZOIAO8ZC"));
            playlistSongsList.add(new PlaylistSongs("Ki??u H??nh Vi???t Nam", "ZWB06D9F"));
            playlistSongsList.add(new PlaylistSongs("Hoa D?? Qu???", "ZWB0WUOB"));
            playlistSongsList.add(new PlaylistSongs("Nh???ng C??nh Chim Lu??n Bi???t T??? Do", "ZU8FFBAC"));
            playlistSongsList.add(new PlaylistSongs("Ph?????t", "ZO60BB6W"));
            playlistSongsList.add(new PlaylistSongs("Ng??y B??, L??c L???n", "ZUA0ZZW6"));
            tvtPlaylistSinger.setText("Nh???c Rock");
        }
        //20.Nh???c Karaoke
        if (from.equalsIgnoreCase("Nh???c Karaoke")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("T??nh Tan", "ZWAFWEBD"));
            playlistSongsList.add(new PlaylistSongs("N???i ??au Ai Ng??? (Ch??? L?? C??u H???a)", "ZWBU7IIF"));
            playlistSongsList.add(new PlaylistSongs("Thay Th???", "ZW9C7FFZ"));
            playlistSongsList.add(new PlaylistSongs("Thay T??i Y??u C?? ???y", "ZWAEU0EE"));
            playlistSongsList.add(new PlaylistSongs("????nh M???t Em", "ZWDBBADB"));
            playlistSongsList.add(new PlaylistSongs("Qu??n M???t Ng?????i T???ng Y??u", "ZWBEC67I"));
            playlistSongsList.add(new PlaylistSongs("Hoa H???i ???????ng", "ZWDAAU8Z"));
            playlistSongsList.add(new PlaylistSongs("M??? Nh??n", "ZWAF7I77"));
            playlistSongsList.add(new PlaylistSongs("N???i Bu???n Sau Ti???ng C?????i", "ZW7U8668"));
            playlistSongsList.add(new PlaylistSongs("C???u V???ng Khuy???t", "ZWZ99A8C"));
            tvtPlaylistSinger.setText("Nh???c Karaoke");
        }
        //21.Nh???c DJ-Nonstop
        if (from.equalsIgnoreCase("Nh???c DJ-Nonstop")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Nonstop DJ Bay C??ng M??a Xu??n 2016", "ZW7O9D9F"));
            playlistSongsList.add(new PlaylistSongs("Nonstop Xu??n 2020 (Remix)", "ZWB06IUE"));
            playlistSongsList.add(new PlaylistSongs("N???u Bi???t Tr?????c T??nh Phai", "ZWZEBBOF"));
            playlistSongsList.add(new PlaylistSongs("B??ng Gi??", "ZW79OWIU"));
            playlistSongsList.add(new PlaylistSongs("V???ng C??? T??nh Xu??n (Remix)", "ZW78BD9A"));
            playlistSongsList.add(new PlaylistSongs("Trang Gi???y Tr???ng Remix", "ZWZFF7CZ"));
            playlistSongsList.add(new PlaylistSongs("Kh??i Thu???c V?? Ng?????i T??nh (Remix)", "ZW6DCBUI"));
            playlistSongsList.add(new PlaylistSongs("M??a M??a ????ng (Remix)", "ZW6ZOUWB"));
            playlistSongsList.add(new PlaylistSongs("Thi??n ???????ng ???o (Remix)", "ZW6CA8FO"));
            playlistSongsList.add(new PlaylistSongs("T??nh Y??u Trong S??ng (Remix)", "ZW7U9IUA"));
            tvtPlaylistSinger.setText("Nh???c DJ-Nonstop");
        }
        //22.Nh???c Thi???u Nhi
        if (from.equalsIgnoreCase("Nh???c Thi???u Nhi")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("?????c M?? C???a M???", "ZWCO00ID"));
            playlistSongsList.add(new PlaylistSongs("Chi???c ????n ??ng Sao", "ZU6BCIAI"));
            playlistSongsList.add(new PlaylistSongs("Anh C?? B???y M??u", "ZWECWOO8"));
            playlistSongsList.add(new PlaylistSongs("Li??n Kh??c: Con C?? B?? B?? - Con Chim V??nh Khuy??n", "ZWAFC6U0"));
            playlistSongsList.add(new PlaylistSongs("C??i Patin", "ZWBUB6ZD"));
            playlistSongsList.add(new PlaylistSongs("??i H???c Th??m", "ZO9AAW79"));
            playlistSongsList.add(new PlaylistSongs("B???c Kim Thang", "ZOO96UAA"));
            playlistSongsList.add(new PlaylistSongs("Tr???ng C??m", "ZOFC0DEZ"));
            playlistSongsList.add(new PlaylistSongs("Qu?? T??i", "ZWCO00IC"));
            playlistSongsList.add(new PlaylistSongs("B?? Ch??i L???ng ????n", "ZU6BCIA0"));
            tvtPlaylistSinger.setText("Nh???c Thi???u Nhi");
        }
        //23.Nh???c R&B
        if (from.equalsIgnoreCase("Nh???c R&B")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Heartbreak Anniversary", "ZWBWI8BI"));
            playlistSongsList.add(new PlaylistSongs("Present", "ZU98OB6U"));
            playlistSongsList.add(new PlaylistSongs("Say So", "ZWBOIWO8"));
            playlistSongsList.add(new PlaylistSongs("Like That", "ZWAFZCI8"));
            playlistSongsList.add(new PlaylistSongs("Go Crazy", "ZWBOW9BC"));
            playlistSongsList.add(new PlaylistSongs("Freak", "ZWCWDC7U"));
//            playlistSongsList.add(new PlaylistSongs("Save Your Tears (Remix)", "ZOE8A80U"));
//            playlistSongsList.add(new PlaylistSongs("Blinding Lights", "ZO9FZ6FA"));
            playlistSongsList.add(new PlaylistSongs("Juicy", "ZWAE6CIB"));
//            playlistSongsList.add(new PlaylistSongs("Save Your Tears", "ZO9FZ6F9"));
            tvtPlaylistSinger.setText("Nh???c R&B");
        }
        //24.Nh???c Blue
        if (from.equalsIgnoreCase("Nh???c Blue")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Santa Baby", "ZW6EB9IE"));
            playlistSongsList.add(new PlaylistSongs("Despacito", "ZOWDBZDU"));
            playlistSongsList.add(new PlaylistSongs("Sunset Painter", "ZWAEA97F"));
            playlistSongsList.add(new PlaylistSongs("Someone You Loved", "ZWEIZBCB"));
            playlistSongsList.add(new PlaylistSongs("God Rest Ye Merry, Gentlemen", "ZUBZEED8"));
            playlistSongsList.add(new PlaylistSongs("Moonlight Sonata", "ZWAF099F"));
//            playlistSongsList.add(new PlaylistSongs("Do I Love You", "ZU7DD0W7"));
            playlistSongsList.add(new PlaylistSongs("The Good Earth", "ZWADU9BE"));
//            playlistSongsList.add(new PlaylistSongs("I Get A Kick Out Of You", "ZUOEDFB8"));
            playlistSongsList.add(new PlaylistSongs("Angels We Have Heard On High", "ZUBZE90B"));
            tvtPlaylistSinger.setText("Nh???c Blue");
        }
        //25.Nh???c JAZZ
        if (from.equalsIgnoreCase("Nh???c Jazz")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("California Dreamin'", "ZO9ZDI9W"));
            playlistSongsList.add(new PlaylistSongs("Dream Person", "ZW6WBW6F"));
            playlistSongsList.add(new PlaylistSongs("What A Difference A Day Made", "ZW6UZEE6"));
            playlistSongsList.add(new PlaylistSongs("T???ch D????ng Chi Ca", "IW7FO60E"));
            playlistSongsList.add(new PlaylistSongs("Thi??n Thi??n Khuy???t Ca", "IW6O800U"));
            playlistSongsList.add(new PlaylistSongs("??????????????? / Sai L???m V???n L?? T??i", "ZWZ9ECZ8"));
            playlistSongsList.add(new PlaylistSongs("????????????/ Thi??n H??? H???u T??nh Nh??n (Th???n ??i??u ?????i Hi???p 1995 OST)", "ZWZC0CUI"));
            playlistSongsList.add(new PlaylistSongs("Love You And Love Me", "ZW60DZ06"));
            playlistSongsList.add(new PlaylistSongs("?????????????????????/ ??nh Tr??ng N??i H??? L??ng T??i", "ZWZBO0WB"));
            playlistSongsList.add(new PlaylistSongs("M???ng Uy??n ????ng H??? ??i???p / ??????????????????", "ZW9D8OA0"));
            tvtPlaylistSinger.setText("Nh???c Jazz");
        }
        //26.Nh???c Latin
        if (from.equalsIgnoreCase("Nh???c Latin")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Don't Go Yet", "ZUWDAF0Z"));
            playlistSongsList.add(new PlaylistSongs("Tutu (Remix)", "ZWAFI6FF"));
            playlistSongsList.add(new PlaylistSongs("Calma (Alan Walker Remix)", "ZWADCEO6"));
            playlistSongsList.add(new PlaylistSongs("Timber	", "ZWA80C89"));
//            playlistSongsList.add(new PlaylistSongs("In Da Getto", "ZUIBICZZ"));
            playlistSongsList.add(new PlaylistSongs("LA FAMA", "ZUB7IZEB"));
//            playlistSongsList.add(new PlaylistSongs("telepat??a	", "ZOAFWDZD"));
            playlistSongsList.add(new PlaylistSongs("Tutu	", "ZWAEUDIE"));
//            playlistSongsList.add(new PlaylistSongs("Baila Conmigo", "ZO9F70Z6"));
            playlistSongsList.add(new PlaylistSongs("Todo De Ti", "ZOFD6BIO"));
            tvtPlaylistSinger.setText("Nh???c Latin");
        }
        //27.Nh???c Vi???t Nam
        if (from.equalsIgnoreCase("Nh???c Vi???t Nam")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Bi H??i", "ZUB7U9Z9"));
            playlistSongsList.add(new PlaylistSongs("Ph???n B???i Ch??nh M??nh", "ZUBU607A"));
            playlistSongsList.add(new PlaylistSongs("??i N???", "ZUAE700W"));
//            playlistSongsList.add(new PlaylistSongs("3 1 0 7 7 - W/N ft. ( titie,Duongg )	", "ZUAUDZCC\n"));
            playlistSongsList.add(new PlaylistSongs("C???m ??n Em ???? ?????n", "ZUAW0F0A"));
            playlistSongsList.add(new PlaylistSongs("M??a H?? B???t T???n", "ZUAOUO09"));
            playlistSongsList.add(new PlaylistSongs("Phai Nh??a C???m X??c", "ZUAOZFA7"));
            playlistSongsList.add(new PlaylistSongs("Anh ????a Em ??i", "ZU98DB06"));
            playlistSongsList.add(new PlaylistSongs("Tan V???", "ZU96I8D9"));
            playlistSongsList.add(new PlaylistSongs("Anh L???i L??m Em Kh??c", "ZU99FBUW"));
            tvtPlaylistSinger.setText("Nh???c Vi???t Nam");
        }
        //28.Nh???c ??u M???
        if (from.equalsIgnoreCase("Nh???c ??u M???")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("A Whole New World (Aladdin's Theme)", "ZO899AOE"));
            playlistSongsList.add(new PlaylistSongs("I Will Always Love You\n", "ZWZCZIFW"));
            playlistSongsList.add(new PlaylistSongs("My Heart Will Go On (Love Theme from \"Titanic\")", "ZW9CD9C9"));
            playlistSongsList.add(new PlaylistSongs("Beauty and the Beast (from the Soundtrack \"Beauty and the Beast\")", "ZWA7ZCA9"));
//            playlistSongsList.add(new PlaylistSongs("Circle of Life (From \"The Lion King\"/Soundtrack Version)", "ZWZA0DBE"));
            playlistSongsList.add(new PlaylistSongs("I Have Nothing (Film Version)", "ZW8W7706"));
            playlistSongsList.add(new PlaylistSongs("Take My Breath Away", "ZW60DZFB"));
            playlistSongsList.add(new PlaylistSongs("She's Like the Wind (From \"Dirty Dancing\" Soundtrack)", "ZWA76OC9"));
            playlistSongsList.add(new PlaylistSongs("Because You Loved Me (Theme from \"Up Close and Personal\")", "ZWA7ZCAA"));
            playlistSongsList.add(new PlaylistSongs("Love Theme from \"Romeo and Juliet\" (A Time for Us)", "ZWAF098O"));
            tvtPlaylistSinger.setText("Nh???c ??u M???");
        }
        //29.Nh???c Ti???ng Anh
        if (from.equalsIgnoreCase("Nh???c Ti???ng Anh")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("No (Galantis Remix)", "ZUBOAW8Z"));
            playlistSongsList.add(new PlaylistSongs("In My Mind (Joel Corry Remix)", "ZU798FOW"));
//            playlistSongsList.add(new PlaylistSongs("Cold Heart (PNAU Remix)", "ZUU7FI78"));
//            playlistSongsList.add(new PlaylistSongs("Equal in the Darkness (Steve Aoki Character X Version)", "ZUA89IO8"));
            playlistSongsList.add(new PlaylistSongs("Pick Me Up (Billen Ted Remix)", "ZU0BZWAC"));
            playlistSongsList.add(new PlaylistSongs("September (CORSAK Remix)", "ZUUWF98D"));
//            playlistSongsList.add(new PlaylistSongs("Giants (Sam Feldt Remix)", "ZODE7I8U"));
            playlistSongsList.add(new PlaylistSongs("You (Topic Remix)", "ZOF9B7Z6"));
            playlistSongsList.add(new PlaylistSongs("Sweet Dreams (DES3ETT Remix)", "ZUODB6FA"));
//            playlistSongsList.add(new PlaylistSongs("Chasing Stars (VIP Mix)", "ZU7CIUB8"));
            tvtPlaylistSinger.setText("Nh???c Ti???ng Anh");
        }
        //30.Nh???c Nh???t
        if (from.equalsIgnoreCase("Nh???c Nh???t")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Lemon", "ZWAADCEU"));
            playlistSongsList.add(new PlaylistSongs("?????????", "ZOF9FEWW"));
            playlistSongsList.add(new PlaylistSongs("???????????????", "ZOFAWUIU"));
            playlistSongsList.add(new PlaylistSongs("DICE", "ZU6A8WDZ"));
            playlistSongsList.add(new PlaylistSongs("Gurenge", "ZWADCCDE"));
            playlistSongsList.add(new PlaylistSongs("Inochi No Akashi", "ZUI9796U"));
            playlistSongsList.add(new PlaylistSongs("Dark Spiral Journey", "ZUW69OWC"));
            playlistSongsList.add(new PlaylistSongs("??????", "ZOFA0ZDC"));
            playlistSongsList.add(new PlaylistSongs("Kaikai Kitan", "ZOFA0IW7"));
            playlistSongsList.add(new PlaylistSongs(" Into The Night", "ZUI677IA"));
            tvtPlaylistSinger.setText("Nh???c Nh???t");
        }
        //31.Nh???c Hoa
        if (from.equalsIgnoreCase("Nh???c Hoa")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Xu???t S??n / ??????", "ZWA96B8C"));
            playlistSongsList.add(new PlaylistSongs("Mang Ch???ng / ??????", "ZWAEU7OE"));
            playlistSongsList.add(new PlaylistSongs("??nh Tr??ng Ng??n D???m / ?????????????????????", "ZUWWZ0UE"));
            playlistSongsList.add(new PlaylistSongs("C??? H????ng ?? Nh???t / ???????????", "ZWB0OWO0"));
            playlistSongsList.add(new PlaylistSongs("X??ch Linh / ??????", "ZWAFWA60"));
            playlistSongsList.add(new PlaylistSongs("M???ng V???ng ??o???n / ?????????", "ZW9E68OW"));
            playlistSongsList.add(new PlaylistSongs("Ly Nh??n S???u / ?????????", "ZW9D6C99"));
            playlistSongsList.add(new PlaylistSongs("H??? / ???", "ZWBI66U8"));
            playlistSongsList.add(new PlaylistSongs("T??n Qu?? Phi T??y T???u / ???????????????", "ZWADOOZ6"));
            playlistSongsList.add(new PlaylistSongs("T??y X??ch B??ch / ?????????", "ZW6OWIWO"));
            tvtPlaylistSinger.setText("Nh???c Hoa");
        }
        //32.Nh???c Th??i
        if (from.equalsIgnoreCase("Nh???c Th??i")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs(" Falling Into You", "ZWAAD6W0"));
            playlistSongsList.add(new PlaylistSongs("Kh??ng N??i V???i Em (OST Tu???i N???i Lo???n)", "ZW67BF99"));
            playlistSongsList.add(new PlaylistSongs("Thinking First", "ZWAA7A0C"));
            playlistSongsList.add(new PlaylistSongs(" Ph?? T??nh Y??u (OST T??nh Y??u Kh??ng C?? L???i, L???i ???... B???n Th??n)", "ZW7WDEFO"));
            playlistSongsList.add(new PlaylistSongs(" T??i Kh??c Bi???t (OST Tu???i N???i Lo???n)", "ZW67A9EO"));
            playlistSongsList.add(new PlaylistSongs("Mu???n Th???i Gian Ng???ng L???i (OST T??nh Ng?????i Duy??n Ma)", "ZW670C0O"));
            playlistSongsList.add(new PlaylistSongs(" T??nh Y??u Kh??ng C?? L???i", "ZW7WDEFU"));
            playlistSongsList.add(new PlaylistSongs("Splash Out", "ZW67BU0B"));
            playlistSongsList.add(new PlaylistSongs("Ban Deaw Kan", "ZW68FAB8"));
            playlistSongsList.add(new PlaylistSongs(" N???m L???y B??n Tay Em (OST T??nh Ng?????i Duy??n Ma)", "ZW670C0U"));
            tvtPlaylistSinger.setText("Nh???c Th??i");
        }
        //33.Nh???c T???p Gym
        if (from.equalsIgnoreCase("Nh???c T???p Gym")) {
            playlistSongsList = new ArrayList<>();
//            playlistSongsList.add(new PlaylistSongs("One Touch ", "ZWAD9O8B"));
            playlistSongsList.add(new PlaylistSongs("Higher", "ZWAE69BF"));
            playlistSongsList.add(new PlaylistSongs("Starsigns", "ZWAEI8F6"));
            playlistSongsList.add(new PlaylistSongs("Takeaway", "ZWAEOZO9"));
            playlistSongsList.add(new PlaylistSongs("Mother's Daughter", "ZWAD8D9U"));
            playlistSongsList.add(new PlaylistSongs(" Power is Power", "ZWADUCU6"));
            playlistSongsList.add(new PlaylistSongs("Woman Like Me", "ZWAC9ZEU"));
            playlistSongsList.add(new PlaylistSongs("High On Life", "ZW9DIFFZ"));
//            playlistSongsList.add(new PlaylistSongs(" Sorry Not Sorry", "ZW807B9D"));
//            playlistSongsList.add(new PlaylistSongs("All The Stars", "ZW9A9BD6"));
            tvtPlaylistSinger.setText("Nh???c T???p Gym");
        }
        //34.Nh???c Qu??n C?? Ph??
        if (from.equalsIgnoreCase("Nh???c Qu??n C?? Ph??")) {
            playlistSongsList = new ArrayList<>();
//            playlistSongsList.add(new PlaylistSongs("River Flows in You (Orchestra Version)", "ZOA976OZ"));
            playlistSongsList.add(new PlaylistSongs("Dreamland", "ZWAF9W6I"));
            playlistSongsList.add(new PlaylistSongs("The Moment", "ZWZ9ADUA"));
            playlistSongsList.add(new PlaylistSongs("Kiss The Rain", "ZWZD909W"));
            playlistSongsList.add(new PlaylistSongs("River Flows In You", "ZWZ970I8"));
            playlistSongsList.add(new PlaylistSongs("Forever In Love (Instrumental)", "ZWZ9AD87"));
            playlistSongsList.add(new PlaylistSongs(" Going Home", "ZWZ9ADUI"));
            playlistSongsList.add(new PlaylistSongs("You Raise Me Up", "ZW669ODC"));
            playlistSongsList.add(new PlaylistSongs("C?? Khi N??o R???i Xa", "ZW6DZZF9"));
            playlistSongsList.add(new PlaylistSongs("Blue Moon", "ZWB7FB67"));
            tvtPlaylistSinger.setText("Nh???c Qu??n c?? ph??");
        }
        //35.Nh???c Phim
        if (from.equalsIgnoreCase("Nh???c Phim")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Shine for You (<Shining like you>Inspirational theme song)", "ZUAAOW6E"));
            playlistSongsList.add(new PlaylistSongs("Center Stage (<Shining like you>Yan Fang & Qiyi LU Love theme song)", "ZUAAOW70"));
            playlistSongsList.add(new PlaylistSongs("Red Scarf ('Till We Meet Again' Movie Theme Song)", "ZUAOADBC"));
            playlistSongsList.add(new PlaylistSongs("Round & Round ('Close to You' LINE TV Series Theme Song)", "ZO9F9FBF"));
            playlistSongsList.add(new PlaylistSongs(" M???t ?????i M???t L???n Rung ?????ng (?????????????????????)", "ZUBD9ACD"));
            playlistSongsList.add(new PlaylistSongs("Lu??n C?? Ng?????i R???i ??i ??? L???i (???????????????)", "ZUBD9AC9"));
            playlistSongsList.add(new PlaylistSongs("Tuy??n Kh???c (??????)", "ZUBD9ADW"));
            playlistSongsList.add(new PlaylistSongs("C??? H??n ?????ng Ph??n T??m (??????????????????)", "ZUBD9ABO"));
            playlistSongsList.add(new PlaylistSongs(" M???c Ly (??????)", "ZUAU7F0B"));
            playlistSongsList.add(new PlaylistSongs("You Don't Belong to Me ('More than Blue TV Series Theme Song')", "ZU900WDI"));
            tvtPlaylistSinger.setText("Nh???c Phim");
        }
        //36.Nh???c T??nh Y??u
        if (from.equalsIgnoreCase("Nh???c T??nh Y??u")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("M??nh L?? G?? C???a Nhau", "ZW78I80B"));
            playlistSongsList.add(new PlaylistSongs("Tr???i Gi???u Tr???i Mang ??i", "ZWAFIZZ0"));
            playlistSongsList.add(new PlaylistSongs("B??ng Hoa ?????p Nh???t", "ZWDZCE80"));
            playlistSongsList.add(new PlaylistSongs("Kh??ng Y??u ?????ng G??y Th????ng Nh???", "ZWAEU7AW"));
            playlistSongsList.add(new PlaylistSongs("Y??u Nh???m B???n Th??n", "ZWBW8IIW"));
            playlistSongsList.add(new PlaylistSongs("Em G??i M??a", "ZW8IZECW"));
            playlistSongsList.add(new PlaylistSongs("Tri K???", "ZW7UIB89"));
            playlistSongsList.add(new PlaylistSongs("Sau 30", "ZO88W8FU"));
            playlistSongsList.add(new PlaylistSongs("Ta ???? Y??u Ch??a V???y", "ZW80C0BB"));
            playlistSongsList.add(new PlaylistSongs(" Ng?????i M??nh Y??u Ch??a Ch???c ???? Y??u M??nh", "ZWAD6BE7"));
            tvtPlaylistSinger.setText("Nh???c T??nh Y??u");
        }
        //37.Nh???c piano
        if (from.equalsIgnoreCase("Nh???c Piano")) {
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
            tvtPlaylistSinger.setText("Nh???c piano");
        }
        //38.Nh???c T??m Tr???ng
        if (from.equalsIgnoreCase("Nh???c T??m Tr???ng")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("V???n Lu??n Ch??? Em", "ZOFAZ906"));
            playlistSongsList.add(new PlaylistSongs("Anh V???n ??? ????y", "ZUB8CDE9"));
            playlistSongsList.add(new PlaylistSongs("B??? L??? M???t Ng?????i", "ZWCZDFCA"));
            playlistSongsList.add(new PlaylistSongs("Anh T???ng C??? G???ng", "ZO0AFICD"));
            playlistSongsList.add(new PlaylistSongs("C??? V???i V??ng", "ZWFC6O86"));
            playlistSongsList.add(new PlaylistSongs(" T??nh Y??u H??a ????", "ZU0DDD7U"));
            playlistSongsList.add(new PlaylistSongs("G???p Nh??ng Kh??ng ??? L???i", "ZOWE6F6E"));
            playlistSongsList.add(new PlaylistSongs("B?????c Vu Quy", "ZOWZF8DW"));
            playlistSongsList.add(new PlaylistSongs("Sai L???m C???a Anh", "ZWAFWIOZ"));
            playlistSongsList.add(new PlaylistSongs("Su???t ?????i Kh??ng X???ng", "ZO9AEOF6"));
            tvtPlaylistSinger.setText("Nh???c T??m Tr???ng");
        }
        //39.Nh???c H??n
        if (from.equalsIgnoreCase("Nh???c H??n")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Day And Night / Love All", "ZW6WWUZB"));
            playlistSongsList.add(new PlaylistSongs("DDU_DU DDU_DU", "ZW9C9AZ0"));
            playlistSongsList.add(new PlaylistSongs("Roly Poly", "ZWZC7D0C"));
            playlistSongsList.add(new PlaylistSongs("TTL (Time To Love)", "ZWZ9FC60"));
            playlistSongsList.add(new PlaylistSongs("We Were In Love", "ZWZEC687"));
            playlistSongsList.add(new PlaylistSongs("Cry Cry", "ZW6WWUZ9"));
            playlistSongsList.add(new PlaylistSongs("Round And Round", "ZW78F8ZF"));
            playlistSongsList.add(new PlaylistSongs("How You Like That", "ZWBU778A"));
            playlistSongsList.add(new PlaylistSongs("Lovey Dovey", "ZWZED7E9"));
            tvtPlaylistSinger.setText("Nh???c H??n");
        }
        //40.Nh???c Game
        if (from.equalsIgnoreCase("Nh???c Game")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Monody", "ZW9ABBFC"));
            playlistSongsList.add(new PlaylistSongs("Heroes Tonight", "ZW9ADOCO"));
            playlistSongsList.add(new PlaylistSongs("Believers", "ZOFUDFE8"));
            playlistSongsList.add(new PlaylistSongs("The Ocean", "ZW7UE78B"));
            playlistSongsList.add(new PlaylistSongs("Sign", "ZW9ABBFZ"));
            playlistSongsList.add(new PlaylistSongs("On My Way", "ZWABOA0F"));
            playlistSongsList.add(new PlaylistSongs("Follow Me (Shaun Frank Remix)", "ZWA7ZZWA"));
//            playlistSongsList.add(new PlaylistSongs("Let Me Love You (Zedd Remix)", "ZW78BAZE"));
            playlistSongsList.add(new PlaylistSongs("Lily", "ZWA0OA6F"));
            playlistSongsList.add(new PlaylistSongs("The River", "ZW9AE770"));
            tvtPlaylistSinger.setText("Nh???c Game");
        }
        //41.Nh???c Acoustic
        if (from.equalsIgnoreCase("Nh???c Acoustic")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Nothing Breaks Like a Heart (Acoustic Version)", "ZWAWZ9ZI"));
//            playlistSongsList.add(new PlaylistSongs("Diamonds (Acoustic)", "ZOFDUAZD"));
            playlistSongsList.add(new PlaylistSongs("Break Up Song (Acoustic Version)", "ZWBW8B8Z"));
            playlistSongsList.add(new PlaylistSongs("The Man Who Can't Be Moved (Acoustic)", "ZW9BDBU0"));
            playlistSongsList.add(new PlaylistSongs("Lullaby (Acoustic)", "ZW9BBO69"));
//            playlistSongsList.add(new PlaylistSongs("Slow Hands (Acoustic)", "ZW800IZ8"));
            playlistSongsList.add(new PlaylistSongs("I Learned My Lesson (acoustic)", "ZW9CO0E9"));
            playlistSongsList.add(new PlaylistSongs("Naked (Acoustic Version)", "ZW9FO789"));
            playlistSongsList.add(new PlaylistSongs("If You Wanna Love Somebody (Acoustic)", "ZW9CEAF8"));
            playlistSongsList.add(new PlaylistSongs("We Are Young (Acoustic)", "ZW6O80WW"));
            tvtPlaylistSinger.setText("Nh???c Acoustic");
        }
        //42.Nh???c Rap
        if (from.equalsIgnoreCase("Nh???c Rap")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Phi H??nh Gia", "ZU9CE99E"));
//            playlistSongsList.add(new PlaylistSongs("Nghe Nh?? T??nh Y??u", "ZUWW8WCA"));
            playlistSongsList.add(new PlaylistSongs("L???c", "ZW67D8W6"));
//            playlistSongsList.add(new PlaylistSongs("B???t Nh???c L??n (feat. Harmonie)", "ZU06CBOI"));
            playlistSongsList.add(new PlaylistSongs("BlackJack", "ZOW0OBU8"));
            playlistSongsList.add(new PlaylistSongs("Tay To", "ZUI7WC8C"));
            playlistSongsList.add(new PlaylistSongs("Ch??a T???ng V?? Nhau", "ZUU9I9F6"));
            playlistSongsList.add(new PlaylistSongs("Xin ?????ng Nh???c M??y", "ZOADOC9W"));
            playlistSongsList.add(new PlaylistSongs("Cho M??nh Em", "ZOAC7BUF"));
            playlistSongsList.add(new PlaylistSongs("Sao Em L???i T???t M??y?", "ZO0FW0Z8"));
            tvtPlaylistSinger.setText("Nh???c Rap");
        }
        //43.Nh???c Xu??n
        if (from.equalsIgnoreCase("Nh???c Xu??n")) {
            playlistSongsList = new ArrayList<>();
            playlistSongsList.add(new PlaylistSongs("Ch??o N??m Con Tr??u", "ZO8ZU9UD"));
            playlistSongsList.add(new PlaylistSongs("Nh?? Hoa M??a Xu??n", "ZW69EIBU"));
//            playlistSongsList.add(new PlaylistSongs("Th?? Th???m M??a Xu??n", "ZO77ADEA"));
            playlistSongsList.add(new PlaylistSongs("Xu??n ??i ??? L???i Ch??i", "ZW7O6EE6"));
            playlistSongsList.add(new PlaylistSongs("Hoa c??? m??a xu??n", "ZW789EFE"));
            playlistSongsList.add(new PlaylistSongs("??o???n Xu??n Ca", "ZO8AFA6I"));
            playlistSongsList.add(new PlaylistSongs("L???ng Nghe M??a Xu??n V???", "ZWB0ZUZI"));
            playlistSongsList.add(new PlaylistSongs("Con B?????m Xu??n", "ZW696EO9"));
            playlistSongsList.add(new PlaylistSongs("Xu??n Sum V???y", "ZW7O8A90"));
            playlistSongsList.add(new PlaylistSongs("Anh Cho Em M??a Xu??n (Remix)", "ZWB0O0I0"));
            tvtPlaylistSinger.setText("Nh???c Xu??n");
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
                            JSONObject data = response.getJSONObject("data");
                            JSONArray items = data.getJSONArray("artists");
                            JSONObject obOfItems = (JSONObject) items.get(0);
                            String id_artist = obOfItems.getString("id");
                            String name_artist = obOfItems.getString("name");
                            String thumbnail_0 = obOfItems.getString("thumbnail");
                            Log.e("thumb",thumbnail_0);
                            String thumbnail = null;
                            if (!thumbnail_0.contains("default")&&thumbnail_0.contains("jpeg")){
                                thumbnail = thumbnail_0.substring(0, 33) + ((thumbnail_0.substring(48, thumbnail_0.length())));
                            }
                            if (!thumbnail_0.contains("default")&&thumbnail_0.contains("png")){
                                thumbnail = thumbnail_0.substring(0, 34) + ((thumbnail_0.substring(48, thumbnail_0.length())));
                            }
                            if (thumbnail_0.contains("default")){
                                thumbnail = "https://photo-resize-zmp3.zadn.vn/avatars/7/3/73688444a73a76169d03b689a7e785cf_1404904575.jpg";
                            }
                            if (stringList.size()<5){
                                stringList.add(thumbnail);
                                sliderAdapter.notifyDataSetChanged();
                            }
                            String name = playlistSongsList.getPlName();
                            String id = playlistSongsList.getPlID();

                            Song song = new Song(id, name, new Singer(id_artist, name_artist), thumbnail, 320);
                            songPList.add(song);
                            playListSongAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
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