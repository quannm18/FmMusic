package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.Genres.Genres;

import java.util.ArrayList;
import java.util.List;

public class GenresDAO {
    FMMusicDatabase fmMusicDatabase;

    public GenresDAO(Context context) {
        fmMusicDatabase = new FMMusicDatabase(context);
    }
    public List<Genres> getallGenres(){
        List<Genres> genresList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataUser = "SELECT * FROM GENRES";
        Cursor cursor = database.rawQuery(dataUser,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Genres singer = new Genres();
            singer.setId(String.valueOf(cursor.getString(cursor.getColumnIndex("IDGenres"))));
            singer.setName(String.valueOf(cursor.getString(cursor.getColumnIndex("GenresName"))));
            genresList.add(singer);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return genresList;
    }
    public long insertGenres(Genres singer){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDGenres",singer.getId());
        contentValues.put("GenresName",singer.getName());
        long row = sqLiteDatabase.insert("GENRES",null,contentValues);
        return row;
    }
    public long updatetGenres(Genres singer){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDGenres",singer.getId());
        contentValues.put("GenresName",singer.getName());;
        long row = sqLiteDatabase.update("GENRES",contentValues,"IDGenres=?",new String[]{String.valueOf(singer.getId())});
        return row;
    }

}
