package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.PLLSong;
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
            pllSong.setIdPLLSong(String.valueOf(cursor.getString(cursor.getColumnIndex("IDPLLSong"))));
            pllSong.setIdSong(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSong"))));
            pllSong.setIdPll(String.valueOf(cursor.getString(cursor.getColumnIndex("IDPLL"))));
            pllSongs.add(pllSong);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return pllSongs;
    }
    public long insertPllSong(PLLSong pllSong){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDPLLSong",pllSong.getIdPLLSong());
        contentValues.put("IDPLL",pllSong.getIdPll());
        contentValues.put("IDSong",pllSong.getIdSong());

        long row = sqLiteDatabase.insert("PLLSONG",null,contentValues);
        return row;
    }
    public long updatetPllSong(PLLSong pllSong){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDPLLSong",pllSong.getIdPLLSong());
        contentValues.put("IDPLL",pllSong.getIdPll());
        contentValues.put("IDSong",pllSong.getIdSong());
        long row = sqLiteDatabase.update("PLLSONG",contentValues,"IDPLLSong=?",new String[]{String.valueOf(pllSong.getIdPLLSong())});
        return row;
    }
}
