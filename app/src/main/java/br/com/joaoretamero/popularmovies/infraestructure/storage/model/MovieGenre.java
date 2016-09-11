package br.com.joaoretamero.popularmovies.infraestructure.storage.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;

import java.util.List;

@Table(name = "movie_genre", id = "_id")
public class MovieGenre extends Model {

    @Column(name = "movie")
    public Movie movie;

    @Column(name = "genre")
    public Genre genre;

    public MovieGenre() {
        super();
    }

    public static void clearAll() {
        new Delete()
                .from(MovieGenre.class)
                .execute();
    }

    public static void clearAllFromMovie(Long movieId) {
        new Delete()
                .from(MovieGenre.class)
                .where("movie = ?", movieId)
                .execute();
    }

    public static void bulkInsert(Movie movie, List<Genre> genreList) {
        ActiveAndroid.beginTransaction();
        try {
            MovieGenre movieGenre;
            for (Genre genre : genreList) {
                movieGenre = new MovieGenre();
                movieGenre.movie = movie;
                movieGenre.genre = genre;
                movieGenre.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
