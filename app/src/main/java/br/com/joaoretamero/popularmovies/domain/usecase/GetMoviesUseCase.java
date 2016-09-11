package br.com.joaoretamero.popularmovies.domain.usecase;

import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.LocalMovie;

public class GetMoviesUseCase extends UseCase<Object, List<LocalMovie>> {

    private MovieRepository movieRepository;

    @Override
    public void execute(Object requestValue) {
    }
}
