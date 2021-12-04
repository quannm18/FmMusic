package com.example.fmmusic.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FMMusicDatabase extends SQLiteOpenHelper {
    public FMMusicDatabase(@Nullable Context context) {
        super(context, "fm_music.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // table user
        String TABLE_USER = "CREATE TABLE USER( UserName TEXT PRIMARY KEY, FullName TEXT NOT NULL, Password TEXT NOT NULL)";
        db.execSQL(TABLE_USER);
        db.execSQL("INSERT INTO USER VALUES(\"doquochuy\",\"Do Quoc Huy\",\"123456\")");
        db.execSQL("INSERT INTO USER VALUES(\"tho2002\",\"ThoPham\",\"123456\")");

        // table Playlist
        String TABLE_PLL = "CREATE TABLE PLL(IDPLL INTEGER PRIMARY KEY AUTOINCREMENT, NamePLL TEXT NOT NULL," +
                " IDUser TEXT REFERENCES USER(IDUser))";
        db.execSQL(TABLE_PLL);
        // table PLL_Song
        String TABLE_PLL_SONG = "CREATE TABLE PLLSONG(IDPLLSong INTEGER PRIMARY KEY AUTOINCREMENT," +
                " IDPLL TEXT  REFERENCES PLL(IDPLL)," +
                "IDSong TEXT  REFERENCES SONG(IDSong), SongName TEXT,Thumbnail TEXT,Duration INTEGER, IDSinger TEXT, SingerName TEXT)";
                db.execSQL(TABLE_PLL_SONG);

        // table Singer
        String TABLE_SINGER = "CREATE TABLE SINGER(IDSinger TEXT PRIMARY KEY, SingerName TEXT)";
        db.execSQL(TABLE_SINGER);

        // table genres
        String TABLE_GENRES = "CREATE TABLE GENRES(IDGenres TEXT PRIMARY KEY, GenresName TEXT)";
        db.execSQL(TABLE_GENRES);

        // table Song
        String TABLE_SONG = "CREATE TABLE SONG(IDSong TEXT PRIMARY KEY, SongName TEXT,Thumbnail TEXT,Duration INTEGER," +
                " IDGenres TEXT REFERENCES GENRES(IDGenres)," +
                " IDSinger TEXT REFERENCES SINGER(IDSinger))";
        db.execSQL(TABLE_SONG);

        // table favorite
        String TABLE_FAVORITE = "CREATE TABLE FAVORITE(IDFavorite INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "IDSong TEXT UNIQUE REFERENCES SONG(IDSong), SongName TEXT,Thumbnail TEXT,Duration INTEGER, IDSinger TEXT, SingerName TEXT,"+
                "UserName TEXT REFERENCES USER(UserName))";
        db.execSQL(TABLE_FAVORITE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
