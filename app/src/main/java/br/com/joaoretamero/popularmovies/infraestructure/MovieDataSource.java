package br.com.joaoretamero.popularmovies.infraestructure;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.DomainMovie;

public interface MovieDataSource {

    void findOne(int movieId, MovieDataSource.FindOneCallback findOneCallback);

    void findAll(String sortOrder, MovieDataSource.FindAllCallback findAllCallback);

    void saveOne(DomainMovie movie);

    void saveMany(List<DomainMovie> movies);

    interface FindAllCallback extends BaseCallback<List<DomainMovie>> {
    }

    interface FindOneCallback extends BaseCallback<DomainMovie> {
    }
}
