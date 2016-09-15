package br.com.joaoretamero.popularmovies.domain.model;

public class Video {

    private String name;

    private String youtubeId;

    public Video(String name, String youtubeId) {
        this.name = name;
        this.youtubeId = youtubeId;
    }

    public String getName() {
        return name;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    @Override
    public String toString() {
        return "Video{" +
                "name='" + name + '\'' +
                ", youtubeId='" + youtubeId + '\'' +
                '}';
    }
}
