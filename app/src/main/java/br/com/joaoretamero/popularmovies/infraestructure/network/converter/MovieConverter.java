package br.com.joaoretamero.popularmovies.infraestructure.network.converter;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.network.model.MovieJson;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.Movie;

public class MovieConverter {

    public List<Movie> convertListToStorageModel(List<MovieJson> movieJsonList) {
        List<Movie> movieList = new ArrayList<Movie>(movieJsonList.size());

        for (MovieJson movieJson : movieJsonList) {
            movieList.add(convertToStorageModel(movieJson));
        }

        return movieList;
    }

    public Movie convertToStorageModel(MovieJson movieJson) {
        Movie movie = new Movie();
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
