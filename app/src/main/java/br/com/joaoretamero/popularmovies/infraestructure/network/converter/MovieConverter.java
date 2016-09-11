package br.com.joaoretamero.popularmovies.infraestructure.network.converter;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.network.model.MovieJson;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.LocalMovie;

public class MovieConverter {

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
