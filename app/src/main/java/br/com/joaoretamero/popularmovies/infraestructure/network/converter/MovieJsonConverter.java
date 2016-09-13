package br.com.joaoretamero.popularmovies.infraestructure.network.converter;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.DomainMovie;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.LocalMovie;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.MovieJson;

public class MovieJsonConverter {

    public List<DomainMovie> convertToDomainMovies(List<MovieJson> movieJsons) {
        List<DomainMovie> domainMovies = new ArrayList<>(movieJsons.size());
        for (MovieJson movieJson : movieJsons) {
            domainMovies.add(convertToDomainMovie(movieJson));
        }
        return domainMovies;
    }

    public DomainMovie convertToDomainMovie(MovieJson movieJson) {
        DomainMovie domainMovie = new DomainMovie(movieJson.id);
        domainMovie.setVoteAverage(movieJson.voteAverage);
        domainMovie.setTitle(movieJson.title);
        domainMovie.setPoster(movieJson.poster);
        domainMovie.setPopularity(movieJson.popularity);
        domainMovie.setBackdrop(movieJson.backdrop);
        domainMovie.setDurationInMinutes(movieJson.runtime);
        domainMovie.setOverview(movieJson.overview);
        return domainMovie;
    }

    public List<LocalMovie> convertListToStorageModel(List<MovieJson> movieJsonList) {
        List<LocalMovie> movieList = new ArrayList<LocalMovie>(movieJsonList.size());
        for (MovieJson movieJson : movieJsonList) {
            movieList.add(convertToStorageModel(movieJson));
        }
        return movieList;
    }

    public LocalMovie convertToStorageModel(MovieJson movieJson) {
        LocalMovie movie = new LocalMovie();
        movie.movieId = movieJson.id;
        movie.backdrop = movieJson.backdrop;
        movie.durationInMinutes = movieJson.runtime;
        movie.overview = movieJson.overview;
        movie.popularity = movieJson.popularity;
        movie.poster = movieJson.poster;
        movie.title = movieJson.title;
        movie.voteAverage = movieJson.voteAverage;
        return movie;
    }
}
