package br.com.joaoretamero.popularmovies.repository;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.util.List;

import br.com.joaoretamero.popularmovies.model.Movie;

public class MovieRepository {

    public Movie findById(int id) {
        return Model.load(Movie.class, id);
    }

    public List<Movie> findAllSortByVote() {
        return new Select()
                .from(Movie.class)
                .orderBy("vote_average")
                .execute();
    }
}
