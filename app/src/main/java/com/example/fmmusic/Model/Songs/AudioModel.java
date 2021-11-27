package com.example.fmmusic.Model.Songs;

public class AudioModel {
    private String name;
    private String artist;
    private String id;
    private String path;
    private String duration;
    private String imgPath;

    public AudioModel(String name, String artist, String id, String path, String duration, String imgPath) {
        this.name = name;
        this.artist = artist;
        this.id = id;
        this.path = path;
        this.duration = duration;
        this.imgPath = imgPath;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public AudioModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AudioModel{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", duration='" + duration + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
