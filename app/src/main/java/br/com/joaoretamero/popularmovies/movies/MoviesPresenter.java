package br.com.joaoretamero.popularmovies.movies;

import android.util.Log;

import br.com.joaoretamero.popularmovies.model.Movie;

public class MoviesPresenter {

    private final static String TAG = MoviesPresenter.class.getSimpleName();
    private MoviesView view;

    public MoviesPresenter(MoviesView view) {
        Log.d(TAG, "constructor");
        this.view = view;
    }

    public void start() {
        Log.d(TAG, "start");
        view.showRefreshIndicator(true);
        listMovies();
    }

    public void onItemClick(int movieId) {
        Log.d(TAG, "onitemclick > movieid: " + movieId);
        view.showMovieDetail(movieId);
    }

    public void onRefresh() {
        Log.d(TAG, "onrefresh");
        listMovies();
    }

    public void onSortMenuClick() {
        view.showSortingOptions();
    }

    public void onConfigurationMenuClick() {
        view.showConfigurationScreen();
    }

    private void listMovies() {
        Log.d(TAG, "listmovies");
        view.showMovies(Movie.allSortedByVote());
        view.showRefreshIndicator(false);
    }
}
