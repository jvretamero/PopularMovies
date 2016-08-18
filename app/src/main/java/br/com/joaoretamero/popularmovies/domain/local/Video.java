package br.com.joaoretamero.popularmovies.domain.local;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "video", id = "_id")
public class Video extends Model {

    @Column(name = "movie")
    public Movie movie;
    
    @Column(name = "name")
    public String name;

    @Column(name = "youtube_id")
    public String youtubeId;

    public Video() {
        super();
    }
}
