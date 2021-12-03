package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.PLL;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.Model.Songs.Song;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {
    FMMusicDatabase fmMusicDatabase;
    public FavoriteDAO(Context context) {
        fmMusicDatabase = new FMMusicDatabase(context);
    }
    public List<Favorite> getAllFVR(){
        List<Favorite> pllList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataPLL = "SELECT * FROM FAVORITE";
        Cursor cursor = database.rawQuery(dataPLL,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Favorite fvr = new Favorite();
            Song song  = new Song();
            Singer singer = new Singer();
            fvr.setIdfv(Integer.valueOf(cursor.getString(cursor.getColumnIndex("IDFavorite"))));
            song.setId(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSong"))));
            song.setName(String.valueOf(cursor.getString(cursor.getColumnIndex("SongName"))));
            song.setThumbnail(String.valueOf(cursor.getString(cursor.getColumnIndex("Thumbnail"))));
            song.setDuration(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Duration"))));

            singer.setId(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSinger"))));
            singer.setName(String.valueOf(cursor.getString(cursor.getColumnIndex("SingerName"))));

            song.setSinger(singer);
            fvr.setSong(song);
            fvr.setUseName(String.valueOf(cursor.getString(cursor.getColumnIndex("SingerName"))));

            pllList.add(fvr);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return pllList;
    }
    public long insertFV(Favorite fvr){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("IDSong",fvr.getSong().getId());
        contentValues.put("SongName",fvr.getSong().getName());
        contentValues.put("Thumbnail",fvr.getSong().getThumbnail());
        contentValues.put("Duration",fvr.getSong().getDuration());
        contentValues.put("IDSinger",fvr.getSong().getSinger().getId());
        contentValues.put("SingerName",fvr.getSong().getSinger().getName());

        long row = sqLiteDatabase.insert("FAVORITE",null,contentValues);
        return row;
    }

    public int deleteFV(Favorite fvr){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        int row = sqLiteDatabase.delete("FAVORITE","IDFavorite=?",new String[]{String.valueOf(fvr.getIdfv())});
        return row;
    }
}
