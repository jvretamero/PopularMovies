package br.com.joaoretamero.popularmovies.movies;

import br.com.joaoretamero.popularmovies.model.Movie;
import io.realm.RealmResults;

public interface MoviesView {
    void showMovieDetail();

    void showSortingOptions();

    void showConfigurationScreen();

    void showRefreshIndicator(boolean showRefreshIndicator);

    void showMovies(RealmResults<Movie> movies);
}
