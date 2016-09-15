package br.com.joaoretamero.popularmovies.infraestructure.network.converter;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.MovieJson;

public class MovieJsonConverter {

    public List<Movie> convertToDomainMovies(List<MovieJson> movieJsons) {
        List<Movie> movies = new ArrayList<>(movieJsons.size());
        for (MovieJson movieJson : movieJsons) {
            movies.add(convertToDomainMovie(movieJson));
        }
        return movies;
    }

    public Movie convertToDomainMovie(MovieJson movieJson) {
        Movie movie = new Movie(movieJson.id);
        movie.setVoteAverage(movieJson.voteAverage);
        movie.setTitle(movieJson.title);
        movie.setPoster(movieJson.poster);
        movie.setPopularity(movieJson.popularity);
        movie.setBackdrop(movieJson.backdrop);
        movie.setDurationInMinutes(movieJson.runtime);
        movie.setOverview(movieJson.overview);
        return movie;
    }
}
