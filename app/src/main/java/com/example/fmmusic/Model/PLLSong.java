package com.example.fmmusic.Model;

public class PLLSong {
    private String idPLLSong;
    private String idPll;
    private String idSong;

    public PLLSong() {
    }

    public PLLSong(String idPLLSong, String idPll, String idSong) {
        this.idPLLSong = idPLLSong;
        this.idPll = idPll;
        this.idSong = idSong;
    }

    public String getIdPLLSong() {
        return idPLLSong;
    }

    public void setIdPLLSong(String idPLLSong) {
        this.idPLLSong = idPLLSong;
    }

    public String getIdPll() {
        return idPll;
    }

    public void setIdPll(String idPll) {
        this.idPll = idPll;
    }

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }
}
