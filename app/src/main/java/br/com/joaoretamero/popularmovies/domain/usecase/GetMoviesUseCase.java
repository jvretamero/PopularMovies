package br.com.joaoretamero.popularmovies.domain.usecase;

import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.Movie;

public class GetMoviesUseCase extends UseCase<Object, List<Movie>> {

    private MovieRepository movieRepository;

    @Override
    public void execute(Object requestValue) {
    }
}
