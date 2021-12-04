package com.example.fmmusic.Model;

import com.example.fmmusic.Model.Songs.Song;

public class PLLSong {
    private int idPLLSong;
    private int idPll;
    private Song song;

    public PLLSong(int idPLLSong, int idPll, Song song) {
        this.idPLLSong = idPLLSong;
        this.idPll = idPll;
        this.song = song;
    }

    public PLLSong() {
    }

    public int getIdPLLSong() {
        return idPLLSong;
    }

    public void setIdPLLSong(int idPLLSong) {
        this.idPLLSong = idPLLSong;
    }

    public int getIdPll() {
        return idPll;
    }

    public void setIdPll(int idPll) {
        this.idPll = idPll;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
