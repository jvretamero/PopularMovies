package br.com.joaoretamero.popularmovies.domain.local;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "genre", id = "_id")
public class Genre extends Model {

    @Column(name = "name")
    public String name;

    public Genre() {
        super();
    }

    private static From joinMovie(From from, Long movieId) {
        return from
                .as("target_model")
                .innerJoin(MovieGenre.class)
                .as("join_model")
                .on("join_model.genre = target_model._id")
                .where("join_model.movie = ?", movieId);
    }

    public static List<Genre> findAllFromMovie(Long movieId) {
        return joinMovie(new Select().from(Genre.class), movieId)
                .orderBy("target_model.name")
                .execute();
    }

    public static void clearAllFromMovie(Long movieId) {
        joinMovie(new Delete().from(Genre.class), movieId).execute();
    }

    public static void bulkInsert(List<Genre> genreList) {
        ActiveAndroid.beginTransaction();
        try {
            for (Genre genre : genreList) {
                genre.save();
            }

            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
