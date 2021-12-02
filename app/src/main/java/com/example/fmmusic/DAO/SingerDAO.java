package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.PLLSong;
import com.example.fmmusic.Model.SingerModel.Singer;

import java.util.ArrayList;
import java.util.List;

public class SingerDAO {
    FMMusicDatabase fmMusicDatabase;

    public SingerDAO(Context context) {
        fmMusicDatabase = new FMMusicDatabase(context);
    }
    public List<Singer> getAllSinger(){
        List<Singer> singerList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataUser = "SELECT * FROM SINGER";
        Cursor cursor = database.rawQuery(dataUser,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Singer singer = new Singer();
            singer.setId(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSinger"))));
            singer.setName(String.valueOf(cursor.getString(cursor.getColumnIndex("SingerName"))));
            singerList.add(singer);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return singerList;
    }
    public long insertSinger(Singer singer){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDSinger",singer.getId());
        contentValues.put("SingerName",singer.getName());
        long row = sqLiteDatabase.insert("SINGER",null,contentValues);
        return row;
    }
    public long updatetSinger(Singer singer){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDPLLSong",singer.getId());
        contentValues.put("IDPLL",singer.getName());
        long row = sqLiteDatabase.update("SINGER",contentValues,"IDSinger=?",new String[]{String.valueOf(singer.getId())});
        return row;
    }
}
