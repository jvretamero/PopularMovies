package br.com.joaoretamero.popularmovies.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.domain.json.MovieJson;
import br.com.joaoretamero.popularmovies.domain.local.Movie;

public class MovieJsonToLocalMapper {

    public List<Movie> mapJsonListToLocalList(List<MovieJson> movieJsonList) {
        List<Movie> movieList = new ArrayList<Movie>();

        for (MovieJson movieJson : movieJsonList) {
            movieList.add(mapJsonToLocal(movieJson));
        }

        return movieList;
    }

    private Movie mapJsonToLocal(MovieJson movieJson) {
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
