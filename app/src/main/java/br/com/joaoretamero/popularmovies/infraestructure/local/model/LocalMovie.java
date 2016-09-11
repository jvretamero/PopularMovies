package br.com.joaoretamero.popularmovies.infraestructure.local.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "movie", id = "_id")
public class LocalMovie extends Model {

    public static final String VOTE_AVERAGE_FIELD = "vote_average";
    public static final String POPULARITY_FIELD = "popularity";
    public static final String DEFAULT_ORDER = VOTE_AVERAGE_FIELD;

    @Column(name = "movie_id")
    public int movieId;

    @Column(name = "poster")
    public String poster;

    @Column(name = "backdrop")
    public String backdrop;

    @Column(name = "title")
    public String title;

    @Column(name = "overview")
    public String overview;

    @Column(name = "durationInMinutes")
    public int durationInMinutes;

    @Column(name = VOTE_AVERAGE_FIELD)
    public float voteAverage;

    @Column(name = POPULARITY_FIELD)
    public float popularity;

    public LocalMovie() {
        super();
    }

    public static LocalMovie findByMovieId(int movieId) {
        return new Select()
                .from(LocalMovie.class)
                .where("movie_id = ?", movieId)
                .executeSingle();
    }

    public static List<LocalMovie> findAllSortedBy(String sortBy) {
        return new Select()
                .from(LocalMovie.class)
                .orderBy(sortBy)
                .execute();
    }

    public static void clearAll() {
        new Delete().from(LocalMovie.class).execute();
    }

    public static void bulkInsert(List<LocalMovie> movieList) {
        ActiveAndroid.beginTransaction();
        try {
            for (LocalMovie movie : movieList) {
                movie.save();
            }

            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
