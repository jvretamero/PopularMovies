package br.com.joaoretamero.popularmovies.domain.repository;

import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.storage.model.Movie;

public interface MovieRepository {

    void findOne(int movieId, MovieRepository.FindOneCallback findOneCallback);

    void findAll(String sortOrder, MovieRepository.FindAllCallback findAllCallback);

    interface FindAllCallback extends BaseRepositoryCallback<List<Movie>> {
    }

    interface FindOneCallback extends BaseRepositoryCallback<Movie> {
    }
}
