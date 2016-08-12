package br.com.joaoretamero.popularmovies.movies;

import br.com.joaoretamero.popularmovies.model.Movie;

public class MoviesPresenter {

    private final static String TAG = MoviesPresenter.class.getSimpleName();
    private MoviesView view;

    public MoviesPresenter(MoviesView view) {
        this.view = view;
    }

    public void start() {
        view.showRefreshIndicator(true);
        listMovies();
    }

    public void onItemClick(int movieId) {
        view.showMovieDetail(movieId);
    }

    public void onRefresh() {
        listMovies();
    }

    public void onSortMenuClick() {
        view.showSortingOptions();
    }

    public void onConfigurationMenuClick() {
        view.showConfigurationScreen();
    }

    private void listMovies() {
        view.showMovies(Movie.findAllSortedByVote());
        view.showRefreshIndicator(false);
    }
}
