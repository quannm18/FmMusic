package com.example.fmmusic.Model.Songs;

import com.example.fmmusic.Model.SingerModel.Singer;

public class Song {
    private String id;
    private String name;
    private Singer singer;
    private String thumbnail;
    private int duration;

    public Song(String id, String name, Singer singer, String thumbnail, int duration) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.thumbnail = thumbnail;
        this.duration = duration;
    }

    public Song() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
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

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", singer=" + singer +
                ", thumbnail='" + thumbnail + '\'' +
                ", duration=" + duration +
                '}';
    }
}
