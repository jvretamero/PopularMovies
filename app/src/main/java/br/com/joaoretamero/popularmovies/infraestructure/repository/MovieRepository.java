package br.com.joaoretamero.popularmovies.infraestructure.repository;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.infraestructure.BaseCallback;

public interface MovieRepository {

    void findOne(int movieId, MovieRepository.FindOneCallback findOneCallback);

    void findAll(String sortOrder, MovieRepository.FindAllCallback findAllCallback);

    interface FindAllCallback extends BaseCallback<List<Movie>> {
    }

    interface FindOneCallback extends BaseCallback<Movie> {
    }
}
