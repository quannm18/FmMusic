package com.example.fmmusic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.Users;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    FMMusicDatabase fmMusicDatabase;

    public UserDAO(Context context) {
            fmMusicDatabase = new FMMusicDatabase(context);
    }
    public List<Users> getAllUser(){
        List<Users> usersList = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        String dataUser = "SELECT * FROM USER";
        Cursor cursor = database.rawQuery(dataUser,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Users users = new Users();
          //  users.setIdUser(Integer.parseInt(cursor.getString(cursor.getColumnIndex("IDUser"))));
            users.setUserName(String.valueOf(cursor.getString(cursor.getColumnIndex("UserName"))));
            users.setFullName(String.valueOf(cursor.getString(cursor.getColumnIndex("FullName"))));
            users.setPassWord(String.valueOf(cursor.getString(cursor.getColumnIndex("Password"))));
            usersList.add(users);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return usersList;
    }
    public long insertUser(Users users){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put("IDUser",users.getIdUser());
        contentValues.put("UserName",users.getUserName());
        contentValues.put("FullName",users.getFullName());
        contentValues.put("Password",users.getPassWord());

        long row = sqLiteDatabase.insert("USER",null,contentValues);
        return row;
    }
    public long updatetUser(Users users){
        SQLiteDatabase sqLiteDatabase = fmMusicDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put("IDUser",users.getIdUser());
        contentValues.put("UserName",users.getUserName());
        contentValues.put("FullName",users.getFullName());
        contentValues.put("Password",users.getPassWord());
        long row = sqLiteDatabase.update("USER",contentValues,"UserName=?",new String[]{String.valueOf(users.getIdUser())});
        return row;
    }
    private List<Users> getData(String sql, String...selectionArgs){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase database = fmMusicDatabase.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            Users users = new Users();
           // users.setIdUser(Integer.parseInt(cursor.getString(cursor.getColumnIndex("IDUser"))));
            users.setUserName(String.valueOf(cursor.getString(cursor.getColumnIndex("UserName"))));
            users.setFullName(String.valueOf(cursor.getString(cursor.getColumnIndex("FullName"))));
            users.setPassWord(String.valueOf(cursor.getString(cursor.getColumnIndex("Password"))));
            list.clear();
            list.add(users);
        }
        return list;
    }

    public  int checkSigin(String taiKhoan,String matKhau){
        String sql = "SELECT * FROM USER WHERE UserName=? AND Password=?";
        List<Users> thuThuList = getData(sql,taiKhoan,matKhau);
        if (thuThuList.size()==0){
            return -1;
        }
        return 1;
    }

}
