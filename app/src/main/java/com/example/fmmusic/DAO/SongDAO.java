package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.Genres.Genres;
import com.example.fmmusic.Model.Songs.Song;
import com.example.fmmusic.Model.Songs2;

import java.util.ArrayList;
import java.util.List;

public class SongDAO {
    FMMusicDatabase fmMusicDatabase;

    public SongDAO(Context context) {
        fmMusicDatabase = new FMMusicDatabase(context);
    }
    public List<Songs2> getallSong(){
        List<Songs2> songList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataUser = "SELECT * FROM SONG";
        Cursor cursor = database.rawQuery(dataUser,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Songs2 singer = new Songs2();
            singer.setIdSong(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSong"))));
            singer.setNameSong(String.valueOf(cursor.getString(cursor.getColumnIndex("SongName"))));
            singer.setIdSinger(String.valueOf(cursor.getString(cursor.getColumnIndex("IDSinger"))));
            singer.setIdGenres(String.valueOf(cursor.getString(cursor.getColumnIndex("IDGenres"))));
            singer.setIdCountry(String.valueOf(cursor.getString(cursor.getColumnIndex("IDCountry"))));
            singer.setThumbnail(String.valueOf(cursor.getString(cursor.getColumnIndex("Thumbnail"))));
            singer.setDuration(Integer.parseInt((cursor.getString(cursor.getColumnIndex("Duration")))));

            songList.add(singer);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return songList;
    }
    public long insertSongs2(Songs2 singer){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDSong",singer.getIdSong());
        contentValues.put("SongName",singer.getNameSong());
        contentValues.put("IDGenres",singer.getIdGenres());
        contentValues.put("IDSinger",singer.getIdSinger());
        contentValues.put("IDCountry",singer.getIdCountry());
        contentValues.put("Duration",singer.getDuration());
        contentValues.put("Thumbnail",singer.getThumbnail());
        long row = sqLiteDatabase.insert("SONG",null,contentValues);
        return row;
    }
    public long updatetSong(Songs2 singer){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDSong",singer.getIdSong());
        contentValues.put("SongName",singer.getNameSong());
        contentValues.put("IDGenres",singer.getIdGenres());
        contentValues.put("IDSinger",singer.getIdSinger());
        contentValues.put("IDCountry",singer.getIdCountry());
        contentValues.put("Duration",singer.getDuration());
        contentValues.put("Thumbnail",singer.getThumbnail());
        long row = sqLiteDatabase.update("SONG",contentValues,"IDSong=?",new String[]{String.valueOf(singer.getIdSong())});
        return row;
    }
}
