package br.com.joaoretamero.popularmovies.movies;

import android.net.ConnectivityManager;
import android.util.Log;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.local.Movie;
import br.com.joaoretamero.popularmovies.domain.repository.MovieRepository;

public class MoviesPresenter {

    private final static String TAG = MoviesPresenter.class.getSimpleName();
    private MoviesView view;
    private MovieRepository movieRepository;

    public MoviesPresenter(MoviesView view, ConnectivityManager connectivityManager) {
        this.view = view;
        this.movieRepository = new MovieRepository(connectivityManager);
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
        movieRepository.findAll(sortOrder, new MovieRepository.FindAllCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                Log.d(TAG, "onsuccess");
                Log.d(TAG, "movies count: " + movies.size());

                view.showMovies(movies);
                view.showRefreshIndicator(false);
            }

            @Override
            public void onFail() {
                view.showErrorLoadingMovies();
                view.showRefreshIndicator(false);
            }
        });

    }
}
