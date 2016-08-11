package br.com.joaoretamero.popularmovies.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "video", id = "id")
public class Video extends Model {

    @Column(name = "id")
    public String id;

    @Column(name = "name")
    public String name;

    @Column(name = "youtube_id")
    public String youtubeId;

    @Column(name = "movie")
    public Movie movie;

    public Video() {
        super();
    }
}
