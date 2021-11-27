package com.example.fmmusic.Model.Genres;

public class Genres {
    private String id;
    private String name;

    public Genres(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genres() {
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
        return "Genres{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
