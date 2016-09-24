package br.com.joaoretamero.popularmovies.domain.usecase;

import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.infrastructure.repository.MovieRepository;

public class GetMovieUseCase extends UseCase<Integer, Movie> {

    private MovieRepository movieRepository;

    public GetMovieUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void execute(Integer movieId) {
        movieRepository.findOne(movieId, new MovieRepository.FindOneCallback() {
            @Override
            public void onSuccess(Movie response) {
                notifySuccess(response);
            }

            @Override
            public void onError() {
                notifyError(null);
            }
        });
    }
}
