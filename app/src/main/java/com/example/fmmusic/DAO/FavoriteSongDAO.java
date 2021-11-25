package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.FavoriteSong;

import java.util.ArrayList;
import java.util.List;

public class FavoriteSongDAO {
    FMMusicDatabase fmMusicDatabase;
    public FavoriteSongDAO(Context context) {
        fmMusicDatabase = new FMMusicDatabase(context);
    }
    public List<FavoriteSong> getAllFVSong(){
        List<FavoriteSong> pllList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataPLL = "SELECT * FROM FAVORITESONG";
        Cursor cursor = database.rawQuery(dataPLL,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            FavoriteSong pll = new FavoriteSong();
            pll.setIdSong(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSong"))));
            pll.setIdfvSong(String.valueOf(cursor.getString(cursor.getColumnIndex("IDFavoriteSong"))));
            pllList.add(pll);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return pllList;
    }
    public long insertFVSong(FavoriteSong pll){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDFavoriteSong",pll.getIdfvSong());
        contentValues.put("IDSong",pll.getIdSong());
        long row = sqLiteDatabase.insert("FAVORITESONG",null,contentValues);
        return row;
    }
    public long updatetFVSong(FavoriteSong pll){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDSong",pll.getIdSong());
        contentValues.put("IDFavoriteSong",pll.getIdfvSong());
        long row = sqLiteDatabase.update("FAVORITESONG",contentValues,"IDSong=?",new String[]{String.valueOf(pll.getIdfvSong())});
        return row;
    }
}
