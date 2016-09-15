package br.com.joaoretamero.popularmovies.domain.usecase;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.infraestructure.repository.MovieRepository;

public class GetMoviesUseCase extends UseCase<String, List<Movie>> {

    private MovieRepository movieRepository;

    public GetMoviesUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void execute(String sortOrder) {
        movieRepository.findAll(sortOrder, new MovieRepository.FindAllCallback() {
            @Override
            public void onSuccess(List<Movie> response) {
                notifySuccess(response);
            }

            @Override
            public void onError() {
                notifyError(null);
            }
        });
    }
}
