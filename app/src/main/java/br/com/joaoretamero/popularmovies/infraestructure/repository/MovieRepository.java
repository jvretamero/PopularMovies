package br.com.joaoretamero.popularmovies.infraestructure.repository;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.DomainMovie;
import br.com.joaoretamero.popularmovies.infraestructure.BaseCallback;

public interface MovieRepository {

    void findOne(int movieId, MovieRepository.FindOneCallback findOneCallback);

    void findAll(String sortOrder, MovieRepository.FindAllCallback findAllCallback);

    interface FindAllCallback extends BaseCallback<List<DomainMovie>> {
    }

    interface FindOneCallback extends BaseCallback<DomainMovie> {
    }
}
