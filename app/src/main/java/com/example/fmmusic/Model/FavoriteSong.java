package com.example.fmmusic.Model;

public class FavoriteSong {
    private String idfvSong;
    private String idSong;

    public FavoriteSong() {
    }

    public FavoriteSong(String idfvSong, String idSong) {
        this.idfvSong = idfvSong;
        this.idSong = idSong;
    }

    public String getIdfvSong() {
        return idfvSong;
    }

    public void setIdfvSong(String idfvSong) {
        this.idfvSong = idfvSong;
    }

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }
}
