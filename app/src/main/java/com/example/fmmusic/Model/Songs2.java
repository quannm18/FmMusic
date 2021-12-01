package com.example.fmmusic.Model;
public class Songs2 {
    private String idSong;
    private String nameSong;
    private String idCountry;
    private String idGenres;
    private  String idSinger;
    private String thumbnail;
    private int duration;

    public Songs2() {
    }

    public Songs2(String idSong, String nameSong, String idCountry, String idGenres, String idSinger, String thumbnail, int duration) {
        this.idSong = idSong;
        this.nameSong = nameSong;
        this.idCountry = idCountry;
        this.idGenres = idGenres;
        this.idSinger = idSinger;
        this.thumbnail = thumbnail;
        this.duration = duration;
    }

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }

    public String getIdGenres() {
        return idGenres;
    }

    public void setIdGenres(String idGenres) {
        this.idGenres = idGenres;
    }

    public String getIdSinger() {
        return idSinger;
    }

    public void setIdSinger(String idSinger) {
        this.idSinger = idSinger;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
