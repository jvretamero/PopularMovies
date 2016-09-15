package br.com.joaoretamero.popularmovies.infraestructure.repository.impl;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.infraestructure.MovieDataSource;
import br.com.joaoretamero.popularmovies.infraestructure.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();

    private MovieDataSource networkDataSource;

    public MovieRepositoryImpl(MovieDataSource networkDataSource) {
        this.networkDataSource = networkDataSource;
    }

    @Override
    public void findOne(final int movieId, final MovieRepository.FindOneCallback findOneCallback) {
        if (findOneCallback == null) return;

        networkDataSource.findOne(movieId, new MovieDataSource.FindOneCallback() {
            @Override
            public void onSuccess(Movie response) {
                findOneCallback.onSuccess(response);
            }

            @Override
            public void onError() {
                findOneCallback.onError();
            }
        });
    }

    @Override
    public void findAll(String sortOrder, final MovieRepository.FindAllCallback findAllCallback) {
        if (findAllCallback == null) return;

        networkDataSource.findAll(sortOrder, new MovieDataSource.FindAllCallback() {
            @Override
            public void onSuccess(List<Movie> response) {
                findAllCallback.onSuccess(response);
            }

            @Override
            public void onError() {
                findAllCallback.onError();
            }
        });
    }
}
