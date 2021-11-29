package com.example.fmmusic.Model.Songs;

public class Playlist {
    private String Img, Text;

    public Playlist(String img, String text) {
        Img = img;
        Text = text;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
