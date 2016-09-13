package br.com.joaoretamero.popularmovies.domain.usecase;

import br.com.joaoretamero.popularmovies.domain.model.DomainMovie;
import br.com.joaoretamero.popularmovies.infraestructure.repository.MovieRepository;

public class GetMovieUseCase extends UseCase<Integer, DomainMovie> {

    private MovieRepository movieRepository;

    public GetMovieUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void execute(Integer movieId) {
        movieRepository.findOne(movieId, new MovieRepository.FindOneCallback() {
            @Override
            public void onSuccess(DomainMovie response) {
                notifySuccess(response);
            }

            @Override
            public void onError() {
                notifyError(null);
            }
        });
    }
}
