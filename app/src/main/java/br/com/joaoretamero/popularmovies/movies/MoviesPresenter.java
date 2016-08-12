package br.com.joaoretamero.popularmovies.movies;

import br.com.joaoretamero.popularmovies.model.Movie;

public class MoviesPresenter {

    private final static String TAG = MoviesPresenter.class.getSimpleName();
    private MoviesView view;

    public MoviesPresenter(MoviesView view) {
        this.view = view;
    }

    public void start(String sortOrder) {
        view.showRefreshIndicator(true);
        listMovies(sortOrder);
    }

    public void onItemClick(int movieId) {
        view.showMovieDetail(movieId);
    }

    public void onRefresh(String sortOrder) {
        listMovies(sortOrder);
    }

    public void onSortMenuClick() {
        view.showSortingOptions();
    }

    public void onConfigurationMenuClick() {
        view.showConfigurationScreen();
    }

    private void listMovies(String sortOrder) {
        view.showMovies(Movie.findAllSortedBy(sortOrder));
        view.showRefreshIndicator(false);
    }
}
