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

        // table Playlist
        String TABLE_PLL = "CREATE TABLE PLL(IDPLL TEXT PRIMARY KEY, NamePLL TEXT," +
                " IDUser TEXT REFERENCES USER(IDUser))";
        db.execSQL(TABLE_PLL);

        // table PLL_Song
        String TABLE_PLL_SONG = "CREATE TABLE PLLSONG(IDPLLSong TEXT PRIMARY KEY," +
                " IDPLL TEXT REFERENCES PLL(IDPLL)," +
                "IDSong TEXT REFERENCES SONG(IDSong))";
        db.execSQL(TABLE_PLL_SONG);

        // table Singer
        String TABLE_SINGER = "CREATE TABLE SINGER(IDSinger TEXT PRIMARY KEY, SingerName TEXT)";
        db.execSQL(TABLE_SINGER);

        // table Country
        String TABLE_COUNTRY = "CREATE TABLE COUNTRY(IDCountry TEXT PRIMARY KEY, CountryName TEXT)";
        db.execSQL(TABLE_COUNTRY);

        // table genres
        String TABLE_GENRES = "CREATE TABLE GENRES(IDGenres TEXT PRIMARY KEY, GenresName TEXT)";
        db.execSQL(TABLE_GENRES);

        // table Song
        String TABLE_SONG = "CREATE TABLE SONG(IDSong TEXT PRIMARY KEY, SongName TEXT,Thumbnail TEXT,Duration INTEGER," +
                " IDCountry TEXT REFERENCES COUNTRY(IDCountry)," +
                " IDGenres TEXT REFERENCES GENRES(IDGenres)," +
                " IDSinger TEXT REFERENCES SINGER(IDSinger))";
        db.execSQL(TABLE_SONG);

        // table favorite
        String TABLE_FAVORITE = "CREATE TABLE FAVORITE(IDFavorite TEXT PRIMARY KEY, " +
                "IDUser TEXT REFERENCES USER(IDUser))";
        db.execSQL(TABLE_FAVORITE);
        // table favorite-song
        String TABLE_FAVORITE_SONG = "CREATE TABLE FAVORITESONG(IDFavoriteSong TEXT PRIMARY KEY," +
                "IDSong TEXT REFERENCES SONG(IDSong))";
        db.execSQL(TABLE_FAVORITE_SONG);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
