package br.com.joaoretamero.popularmovies.infraestructure;

import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.local.model.Movie;

public interface MovieDataSource {

    void findOne(int movieId, MovieDataSource.FindOneCallback findOneCallback);

    void findAll(String sortOrder, MovieDataSource.FindAllCallback findAllCallback);

    void saveOne(Movie movie);

    void saveMany(List<Movie> movies);

    interface FindAllCallback extends BaseCallback<List<Movie>> {
    }

    interface FindOneCallback extends BaseCallback<Movie> {
    }
}
