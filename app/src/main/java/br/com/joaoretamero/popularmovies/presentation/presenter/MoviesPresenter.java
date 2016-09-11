package br.com.joaoretamero.popularmovies.presentation.presenter;

import android.net.ConnectivityManager;

import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.infraestructure.repository.impl.MovieRepositoryImpl;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.Movie;
import br.com.joaoretamero.popularmovies.presentation.contract.MoviesContract;

public class MoviesPresenter implements MoviesContract {

    private final static String TAG = MoviesPresenter.class.getSimpleName();
    private MoviesContract.View view;
    private MovieRepository movieRepository;
    private boolean firstLoad;

    public MoviesPresenter(MoviesContract.View view, ConnectivityManager connectivityManager) {
        this.view = view;
        this.movieRepository = new MovieRepositoryImpl(connectivityManager);
        this.firstLoad = true;
    }

    public void loadMovies(String sortOrder) {
        view.showRefreshIndicator(true);
        listMovies(sortOrder);
        firstLoad = false;
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
                view.showMovies(movies);
                view.showRefreshIndicator(false);
            }

            @Override
            public void onError() {
                view.showErrorLoadingMovies();
                view.showRefreshIndicator(false);
            }
        });
    }
}
