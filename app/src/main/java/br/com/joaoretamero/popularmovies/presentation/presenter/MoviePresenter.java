package br.com.joaoretamero.popularmovies.presentation.presenter;

import android.net.ConnectivityManager;

import br.com.joaoretamero.popularmovies.infraestructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.infraestructure.repository.impl.MovieRepositoryImpl;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.Genre;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.LocalMovie;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.Video;
import br.com.joaoretamero.popularmovies.presentation.contract.MovieContract;

public class MoviePresenter {

    private static final String TAG = MoviePresenter.class.getSimpleName();
    private MovieContract.View view;
    private MovieRepository movieRepository;

    public MoviePresenter(MovieContract.View view, ConnectivityManager connectivityManager) {
        this.view = view;
        this.movieRepository = new MovieRepositoryImpl(connectivityManager);
    }

    public void start(int movieId) {
        movieRepository.findOne(movieId, new MovieRepository.FindOneCallback() {
            @Override
            public void onSuccess(LocalMovie movie) {
                view.setMovie(movie);
                view.setGenreList(Genre.findAllFromMovie(movie.getId()));
                view.setProductionCompaniesList(ProductionCompany.findAllFromMovie(movie.getId()));
                view.setVideoList(Video.findAllFromMovie(movie.getId()));
            }

            @Override
            public void onError() {
                view.showErrorMessage();
            }
        });
    }
}
