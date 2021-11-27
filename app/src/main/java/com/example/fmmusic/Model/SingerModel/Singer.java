package com.example.fmmusic.Model.SingerModel;

public class Singer {
    private String id;
    private String name;

    public Singer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Singer() {
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

    @Override
    public String toString() {
        return "Singer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
