package br.com.joaoretamero.popularmovies.movies;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.local.Movie;

public interface MoviesView {
    void showMovieDetail(int movieId);

    void showSortingOptions();

    void showConfigurationScreen();

    void showErrorLoadingMovies();

    void showRefreshIndicator(boolean showRefreshIndicator);

    void showMovies(List<Movie> movies);
}
