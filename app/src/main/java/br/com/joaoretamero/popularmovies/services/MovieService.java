package br.com.joaoretamero.popularmovies.services;

import br.com.joaoretamero.popularmovies.modelo.Movie;
import io.realm.RealmResults;
import io.realm.Sort;

public class MovieService extends BaseRealmService<Movie> {

    public MovieService() {
        super(Movie.class);
    }

    public RealmResults<Movie> findAllSortByVote() {
        return this.findAll().sort("voteAverage", Sort.DESCENDING);
    }
}
