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
    public List<PLL> getDataUser(String ...IDUser){
        List<PLL> pllList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataPLL = "SELECT * FROM PLL WHERE IDUser=?";
        Cursor cursor = fmMusicDatabase.getReadableDatabase().rawQuery(dataPLL,IDUser);
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
    public boolean deletePLL(int IDUser){
        SQLiteDatabase database = fmMusicDatabase.getWritableDatabase();
        int row = database.delete("PLL","IDPLL=?",new String[]{String.valueOf(IDUser)});
        return row>0;
    }
    public List<String> getAllNamePLL(){
        List<String> pllModellist = new ArrayList<>();
        String query = "SELECT * FROM PLL";
        SQLiteDatabase sql = fmMusicDatabase.getReadableDatabase();
        sql.rawQuery(query, null);
        Cursor cursor = sql.rawQuery(query,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                String NameUser = cursor.getString(cursor.getColumnIndex("IDUser"));
                String IDPll = cursor.getString(cursor.getColumnIndex("IDPLL"));
                String NamePLL = cursor.getString(cursor.getColumnIndex("NamePLL"));
                pllModellist.add(NamePLL);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return pllModellist;
    }
    public List<PLL> getDataUser(String IDUser){
        List<PLL> pllList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataPLL = "SELECT * FROM PLL WHERE IDUser=?";
        Cursor cursor = fmMusicDatabase.getReadableDatabase().rawQuery(dataPLL, new String[]{IDUser});
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



}
