package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.PLL;
import com.example.fmmusic.Model.PLLSong;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.Model.Users;

import java.util.ArrayList;
import java.util.List;

public class PLLSongDAO {
    FMMusicDatabase fmMusicDatabase;

    public PLLSongDAO(Context context) {
        fmMusicDatabase = new FMMusicDatabase(context);
    }
    public List<PLLSong> getAllPLL(){
        List<PLLSong> pllSongs = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataUser = "SELECT * FROM PLLSONG";
        Cursor cursor = database.rawQuery(dataUser,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            PLLSong pllSong = new PLLSong();
            Song song = new Song();
            Singer singer = new Singer();
            pllSong.setIdPLLSong(Integer.valueOf(cursor.getString(cursor.getColumnIndex("IDPLLSong"))));
            song.setId(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSong"))));
            song.setName(String.valueOf(cursor.getString(cursor.getColumnIndex("SongName"))));
            song.setThumbnail(String.valueOf(cursor.getString(cursor.getColumnIndex("Thumbnail"))));
            song.setDuration(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Duration"))));
            singer.setId(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSinger"))));
            singer.setName(String.valueOf(cursor.getString(cursor.getColumnIndex("SingerName"))));
            song.setSinger(singer);
            pllSong.setIdPll(Integer.valueOf(cursor.getString(cursor.getColumnIndex("IDPLL"))));
            pllSong.setSong(song);
            pllSongs.add(pllSong);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return pllSongs;
    }


    public List<PLLSong> getSongFromPLL(int IDPLL){
        List<PLLSong> pllSongList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataPLL = "SELECT * FROM PLLSONG WHERE IDPLL=?";
        Cursor cursor = database.rawQuery(dataPLL,new String[]{String.valueOf(IDPLL)});
        if (cursor.getCount() >0){
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false){
                PLLSong pllSong = new PLLSong();
                Song song = new Song();
                Singer singer = new Singer();

                pllSong.setIdPLLSong(Integer.valueOf(cursor.getString(cursor.getColumnIndex("IDPLLSong"))));
                song.setId(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSong"))));
                song.setName(String.valueOf(cursor.getString(cursor.getColumnIndex("SongName"))));
                song.setThumbnail(String.valueOf(cursor.getString(cursor.getColumnIndex("Thumbnail"))));
                song.setDuration(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Duration"))));
                singer.setId(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSinger"))));
                singer.setName(String.valueOf(cursor.getString(cursor.getColumnIndex("SingerName"))));
                song.setSinger(singer);
                pllSong.setIdPll(Integer.valueOf(cursor.getString(cursor.getColumnIndex("IDPLL"))));
                pllSong.setSong(song);

                pllSongList.add(pllSong);
                cursor.moveToNext();
            }
        }
        cursor.close();
        database.close();
        return pllSongList;
    }
    private List<Users> getData(String sql, String...selectionArgs){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            Users users = new Users();
            // users.setIdUser(Integer.parseInt(cursor.getString(cursor.getColumnIndex("IDUser"))));
            users.setUserName(String.valueOf(cursor.getString(cursor.getColumnIndex("UserName"))));
            users.setFullName(String.valueOf(cursor.getString(cursor.getColumnIndex("FullName"))));
            users.setPassWord(String.valueOf(cursor.getString(cursor.getColumnIndex("Password"))));
            list.clear();
            list.add(users);
        }
        return list;
    }



    public long insertPllSong(PLLSong pllSong){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDPLL",pllSong.getIdPll());
        contentValues.put("IDSong",pllSong.getSong().getId());
        contentValues.put("SongName",pllSong.getSong().getName());
        contentValues.put("Thumbnail",pllSong.getSong().getThumbnail());
        contentValues.put("Duration",pllSong.getSong().getDuration());
        contentValues.put("IDSinger",pllSong.getSong().getSinger().getId());
        contentValues.put("SingerName",pllSong.getSong().getSinger().getName());



        long row = sqLiteDatabase.insert("PLLSONG",null,contentValues);
        return row;
    }
    public int deletePLLSong(PLLSong pllSong){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();

        int row = sqLiteDatabase.delete("PLLSONG","IDPLLSong=?",new String[]{String.valueOf(pllSong.getIdPLLSong())});
        return row;
    }
}
