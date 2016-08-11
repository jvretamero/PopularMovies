package br.com.joaoretamero.popularmovies.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "movie", id = "_id")
public class Movie extends Model {

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

    @Column(name = "runtime")
    public int runtime;

    @Column(name = "vote_average")
    public float voteAverage;

    public Movie() {
        super();
    }

    public static Movie byMovieId(int movieId) {
        return new Select()
                .from(Movie.class)
                .where("movie_id = ?", movieId)
                .executeSingle();
    }

    public static List<Movie> allSortedByVote() {
        return new Select()
                .from(Movie.class)
                .orderBy("vote_average")
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
                .execute();
    }
}
