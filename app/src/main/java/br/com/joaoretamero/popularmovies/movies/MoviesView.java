package br.com.joaoretamero.popularmovies.movies;

import java.util.List;

import br.com.joaoretamero.popularmovies.model.Movie;

public interface MoviesView {
    void showMovieDetail(int movieId);

    void showSortingOptions();

    void showConfigurationScreen();

    void showRefreshIndicator(boolean showRefreshIndicator);

    void showMovies(List<Movie> movies);
}
