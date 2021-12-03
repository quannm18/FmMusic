package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.PLL;
import com.example.fmmusic.Model.Users;

import java.util.ArrayList;
import java.util.List;

public class PLLDAO {
    FMMusicDatabase fmMusicDatabase;
    public PLLDAO(Context context) {
        fmMusicDatabase = new FMMusicDatabase(context);
    }
    public List<PLL> getAllPll(){
        List<PLL> pllList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataPLL = "SELECT * FROM PLL";
        Cursor cursor = database.rawQuery(dataPLL,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            PLL pll = new PLL();
            pll.setIdUser(String.valueOf(cursor.getString(cursor.getColumnIndex("IDUser"))));
            pll.setIdPLL(Integer.parseInt(String.valueOf(cursor.getString(cursor.getColumnIndex("IDPLL")))));
            pll.setNamePll(String.valueOf(cursor.getString(cursor.getColumnIndex("NamePLL"))));

            pllList.add(pll);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return pllList;
    }
    public long insertPLL(PLL pll){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDUser",pll.getIdUser());
        contentValues.put("NamePLL",pll.getNamePll());
        long row = sqLiteDatabase.insert("PLL",null,contentValues);
        return row;
    }
    public long updatetPLL(PLL pll){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDUser",pll.getIdUser());
        contentValues.put("NamePLL",pll.getNamePll());
        long row = sqLiteDatabase.update("PLL",contentValues,"IDPLL=?",new String[]{String.valueOf(pll.getIdPLL())});
        return row;
    }
}
