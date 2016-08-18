package br.com.joaoretamero.popularmovies.domain.local;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "movie_genre", id = "_id")
public class MovieGenre extends Model {

    @Column(name = "movie")
    public Movie movie;

    @Column(name = "genre")
    public Genre genre;

    public MovieGenre() {
        super();
    }
}
