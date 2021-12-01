package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.Genres.Country;
import com.example.fmmusic.Model.SingerModel.Singer;

import java.util.ArrayList;
import java.util.List;

public class CountryDAO {
    FMMusicDatabase fmMusicDatabase;

    public CountryDAO(Context context) {
        fmMusicDatabase = new FMMusicDatabase(context);
    }
    public List<Country> getAllCountry(){
        List<Country> countries = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataUser = "SELECT * FROM COUNTRY";
        Cursor cursor = database.rawQuery(dataUser,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Country singer = new Country();
            singer.setId(String.valueOf(cursor.getString(cursor.getColumnIndex("IDCountry"))));
            singer.setName(String.valueOf(cursor.getString(cursor.getColumnIndex("CountryName"))));
            countries.add(singer);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return countries;
    }
    public long insertCountry(Country singer){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDCountry",singer.getId());
        contentValues.put("CountryName",singer.getName());
        long row = sqLiteDatabase.insert("COUNTRY",null,contentValues);
        return row;
    }
    public long updatetCountry(Singer singer){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDCountry",singer.getId());
        contentValues.put("CountryName",singer.getName());
        long row = sqLiteDatabase.update("COUNTRY",contentValues,"IDCountry=?",new String[]{String.valueOf(singer.getId())});
        return row;
    }
}
