package com.example.fmmusic.Model.Songs;

public class PlaylistSongs {
    private String plName;
    private String plID;

    public PlaylistSongs() {
    }

    public PlaylistSongs(String plName, String plID) {
        this.plName = plName;
        this.plID = plID;
    }

    public String getPlName() {
        return plName;
    }

    public void setPlName(String plName) {
        this.plName = plName;
    }

    public String getPlID() {
        return plID;
    }

    public void setPlID(String plID) {
        this.plID = plID;
    }

    @Override
    public String toString() {
        return "PlaylistSongs{" +
                "plName='" + plName + '\'' +
                ", plID='" + plID + '\'' +
                '}';
    }
}
