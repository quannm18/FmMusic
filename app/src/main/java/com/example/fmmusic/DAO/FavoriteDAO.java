package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.Favorite;
import com.example.fmmusic.Model.PLL;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {
    FMMusicDatabase fmMusicDatabase;
    public FavoriteDAO(Context context) {
        fmMusicDatabase = new FMMusicDatabase(context);
    }
    public List<Favorite> getAllPll(){
        List<Favorite> pllList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataPLL = "SELECT * FROM FAVORITE";
        Cursor cursor = database.rawQuery(dataPLL,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Favorite pll = new Favorite();
            pll.setIdfv(String.valueOf(cursor.getString(cursor.getColumnIndex("IDFavorite"))));
            pll.setNamefv(String.valueOf(cursor.getString(cursor.getColumnIndex("IDUser"))));
            pllList.add(pll);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return pllList;
    }
    public long insertFV(Favorite pll){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDFavorite",pll.getIdfv());
        contentValues.put("IDUser",pll.getNamefv());
        long row = sqLiteDatabase.insert("FAVORITE",null,contentValues);
        return row;
    }
    public long updatetFV(Favorite pll){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDFavorite",pll.getIdfv());
        contentValues.put("IDUser",pll.getNamefv());
        long row = sqLiteDatabase.update("FAVORITE",contentValues,"IDFavorite=?",new String[]{String.valueOf(pll.getIdfv())});
        return row;
    }
}
