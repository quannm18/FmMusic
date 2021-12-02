package com.example.fmmusic.Model;

public class Favorite {
    private String idfv;
    private String namefv;

    public Favorite() {
    }

    public Favorite(String idfv, String namefv) {
        this.idfv = idfv;
        this.namefv = namefv;
    }

    public String getIdfv() {
        return idfv;
    }

    public void setIdfv(String idfv) {
        this.idfv = idfv;
    }

    public String getNamefv() {
        return namefv;
    }

    public void setNamefv(String namefv) {
        this.namefv = namefv;
    }
}
