package br.com.joaoretamero.popularmovies.presentation.presenter.impl;

import android.net.ConnectivityManager;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.local.Movie;
import br.com.joaoretamero.popularmovies.domain.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.presentation.presenter.MoviesPresenter;

public class MoviesPresenterImpl implements MoviesPresenter {

    private final static String TAG = MoviesPresenterImpl.class.getSimpleName();
    private MoviesPresenter.View view;
    private MovieRepository movieRepository;

    public MoviesPresenterImpl(MoviesPresenter.View view, ConnectivityManager connectivityManager) {
        this.view = view;
        this.movieRepository = new MovieRepository(connectivityManager);
    }

    @Override
    public void start(String sortOrder) {
        view.showRefreshIndicator(true);
        listMovies(sortOrder);
    }

    @Override
    public void onItemClick(int movieId) {
        view.showMovieDetail(movieId);
    }

    @Override
    public void onRefresh(String sortOrder) {
        listMovies(sortOrder);
    }

    @Override
    public void onSortMenuClick() {
        view.showSortingOptions();
    }

    @Override
    public void onConfigurationMenuClick() {
        view.showConfigurationScreen();
    }

    private void listMovies(String sortOrder) {
        movieRepository.findAll(sortOrder, new MovieRepository.FindAllCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
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
