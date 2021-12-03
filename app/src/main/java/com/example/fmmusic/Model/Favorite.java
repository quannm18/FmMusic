package com.example.fmmusic.Model;

import com.example.fmmusic.Model.Songs.Song;

public class Favorite {
    private int idfv;
    private Song song;
    private String useName;

    public Favorite(int idfv, Song song, String useName) {
        this.idfv = idfv;
        this.song = song;
        this.useName = useName;
    }

    public Favorite() {
    }

    public int getIdfv() {
        return idfv;
    }

    public void setIdfv(int idfv) {
        this.idfv = idfv;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }
}
