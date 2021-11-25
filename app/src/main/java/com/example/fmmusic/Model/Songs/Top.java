package com.example.fmmusic.Model.Songs;

import com.example.fmmusic.Model.SingerModel.Singer;

public class Top {
    private String id;
    private String name;
    private String code;
    private Singer singer;
    private String artists_names;
    private String performer;
    private String link;
    private String thumbnail;
    private int duration;
    private int total;
    private int position;
    private String rankStatus;


    public Top(String id, String name, String code, Singer singer, String artists_names, String performer, String link, String thumbnail, int duration, int total, int position, String rankStatus) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.singer = singer;
        this.artists_names = artists_names;
        this.performer = performer;
        this.link = link;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.total = total;
        this.position = position;
        this.rankStatus = rankStatus;
    }

    public Top() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getRankStatus() {
        return rankStatus;
    }

    public void setRankStatus(String rankStatus) {
        this.rankStatus = rankStatus;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public String getArtists_names() {
        return artists_names;
    }

    public void setArtists_names(String artists_names) {
        this.artists_names = artists_names;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
        return "Top{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", singer=" + singer +
                ", artists_names='" + artists_names + '\'' +
                ", performer='" + performer + '\'' +
                ", link='" + link + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", duration=" + duration +
                ", total=" + total +
                ", position=" + position +
                ", rankStatus='" + rankStatus + '\'' +
                '}';
    }
}
