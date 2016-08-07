package br.com.joaoretamero.popularmovies.movies;

import br.com.joaoretamero.popularmovies.services.MovieService;

public class MoviesPresenter {

    private MoviesView view;
    private MovieService movieService;

    public MoviesPresenter(MoviesView view) {
        this.view = view;
        this.movieService = new MovieService();
    }

    public void start() {
        view.showRefreshIndicator(true);
        listMovies();
    }

    public void destroy() {
        this.movieService.closeRealm();
    }

    public void onItemClick() {
        view.showMovieDetail();
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
        view.showMovies(movieService.getAllMovies());
        view.showRefreshIndicator(false);
    }
}
