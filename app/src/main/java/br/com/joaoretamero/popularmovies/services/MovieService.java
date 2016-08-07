package br.com.joaoretamero.popularmovies.services;

import br.com.joaoretamero.popularmovies.modelo.Movie;
import io.realm.RealmResults;
import io.realm.Sort;

public class MovieService extends BaseRealmService {

    public RealmResults<Movie> getAllMovies() {
        return this.realm.where(Movie.class).findAll().sort("voteAverage", Sort.DESCENDING);
    }
}
