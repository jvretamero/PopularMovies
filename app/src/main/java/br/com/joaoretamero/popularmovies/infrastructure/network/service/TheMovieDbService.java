package br.com.joaoretamero.popularmovies.infrastructure.network.service;

import br.com.joaoretamero.popularmovies.infrastructure.network.model.MovieJson;
import br.com.joaoretamero.popularmovies.infrastructure.network.model.MovieJsonResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TheMovieDbService {

    @GET("discover/movie?page=1&sort_by=popularity.desc")
    Call<MovieJsonResponse> getMovies();

    @GET("movie/{movie_id}?append_to_response=reviews,videos,keywords")
    Call<MovieJson> getMovie(@Path("movie_id") int movieId);
}
