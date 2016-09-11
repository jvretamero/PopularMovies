package br.com.joaoretamero.popularmovies.infraestructure.local;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.DomainMovie;
import br.com.joaoretamero.popularmovies.infraestructure.MovieDataSource;
import br.com.joaoretamero.popularmovies.infraestructure.local.converter.LocalMovieConverter;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.LocalMovie;

public class MovieDatabaseSource implements MovieDataSource {

    LocalMovieConverter converter;

    public MovieDatabaseSource(LocalMovieConverter converter) {
        this.converter = converter;
    }

    @Override
    public void findOne(int movieId, FindOneCallback findOneCallback) {
        if (findOneCallback != null) {
            LocalMovie localMovie = LocalMovie.findByMovieId(movieId);
            DomainMovie domainMovie = converter.convertToDomainMovie(localMovie);
            findOneCallback.onSuccess(domainMovie);
        }
    }

    @Override
    public void findAll(String sortOrder, FindAllCallback findAllCallback) {
        if (findAllCallback != null) {
            List<DomainMovie> domainMovies = new ArrayList<>();
            List<LocalMovie> localMovies = LocalMovie.findAllSortedBy(sortOrder);

            for (LocalMovie localMovie : localMovies) {
                domainMovies.add(converter.convertToDomainMovie(localMovie));
            }

            findAllCallback.onSuccess(domainMovies);
        }
    }

    @Override
    public void saveOne(DomainMovie movie) {
        LocalMovie localMovie = converter.fromDomainMovie(movie);
        localMovie.save();
    }

    @Override
    public void saveMany(List<DomainMovie> movies) {
        ActiveAndroid.beginTransaction();
        try {
            LocalMovie localMovie;
            for (DomainMovie domainMovie : movies) {
                localMovie = converter.fromDomainMovie(domainMovie);
                localMovie.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}