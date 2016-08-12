package br.com.joaoretamero.popularmovies.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "movie", id = "_id")
public class Movie extends Model {

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

    public Movie() {
        super();
    }

    public static Movie findByMovieId(int movieId) {
        return new Select()
                .from(Movie.class)
                .where("movie_id = ?", movieId)
                .executeSingle();
    }

    public static List<Movie> findAllSortedBy(String sortBy) {
        return new Select()
                .from(Movie.class)
                .orderBy(sortBy)
                .execute();
    }

    public List<Genre> getGenres() {
        return new Select()
                .from(Genre.class)
                .as("target_model")
                .innerJoin(MovieGenre.class)
                .as("join_model")
                .on("join_model.genre = target_model._id")
                .where("join_model.movie = ?", this.getId())
                .orderBy("target_model.name")
                .execute();
    }

    public List<ProductionCompany> getProductionCompanies() {
        return new Select()
                .from(ProductionCompany.class)
                .as("target_model")
                .innerJoin(MovieProductionCompany.class)
                .as("join_model")
                .on("join_model.production_company = target_model._id")
                .where("join_model.movie = ?", this.getId())
                .orderBy("target_model.name")
                .execute();
    }

    public List<Video> getVideos() {
        return new Select()
                .from(Video.class)
                .where("movie = ?", this.getId())
                .orderBy("name")
                .execute();
    }
}
