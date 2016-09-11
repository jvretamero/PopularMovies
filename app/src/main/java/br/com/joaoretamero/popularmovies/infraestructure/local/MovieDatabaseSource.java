package br.com.joaoretamero.popularmovies.infraestructure.local;

import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.MovieDataSource;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.Movie;

public class MovieDatabaseSource implements MovieDataSource {

    @Override
    public void findOne(int movieId, FindOneCallback findOneCallback) {
        if (findOneCallback != null) {
            findOneCallback.onSuccess(Movie.findByMovieId(movieId));
        }
    }

    @Override
    public void findAll(String sortOrder, FindAllCallback findAllCallback) {
        if (findAllCallback != null) {
            findAllCallback.onSuccess(Movie.findAllSortedBy(sortOrder));
        }
    }

    @Override
    public void saveOne(Movie movie) {
    }

    @Override
    public void saveMany(List<Movie> movies) {
    }
}
