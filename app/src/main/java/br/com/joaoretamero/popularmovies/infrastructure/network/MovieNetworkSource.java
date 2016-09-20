package br.com.joaoretamero.popularmovies.infrastructure.network;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.infrastructure.MovieDataSource;
import br.com.joaoretamero.popularmovies.infrastructure.network.converter.MovieJsonConverter;
import br.com.joaoretamero.popularmovies.infrastructure.network.model.MovieJson;
import br.com.joaoretamero.popularmovies.infrastructure.network.model.MovieJsonResponse;
import br.com.joaoretamero.popularmovies.infrastructure.network.service.TheMovieDbService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieNetworkSource implements MovieDataSource {

    private TheMovieDbService movieDbService;
    private MovieJsonConverter movieJsonConverter;

    public MovieNetworkSource(TheMovieDbService movieDbService, MovieJsonConverter movieJsonConverter) {
        this.movieDbService = movieDbService;
        this.movieJsonConverter = movieJsonConverter;
    }

    @Override
    public void findOne(int movieId, final FindOneCallback findOneCallback) {
        if (findOneCallback == null) return;

        movieDbService.getMovie(movieId).enqueue(new Callback<MovieJson>() {
            @Override
            public void onResponse(Call<MovieJson> call, Response<MovieJson> response) {
                MovieJson movieJson = response.body();
                if (movieJson != null) {
                    Movie movie = movieJsonConverter.convertMovieToDomain(response.body());
                    findOneCallback.onSuccess(movie);
                } else {
                    findOneCallback.onError();
                }
            }

            @Override
            public void onFailure(Call<MovieJson> call, Throwable t) {
                findOneCallback.onError();
            }
        });
    }

    @Override
    public void findAll(String sortOrder, final FindAllCallback findAllCallback) {
        if (findAllCallback == null) return;

        movieDbService.getMovies().enqueue(new Callback<MovieJsonResponse>() {
            @Override
            public void onResponse(Call<MovieJsonResponse> call, Response<MovieJsonResponse> response) {
                MovieJsonResponse movieJsonResponse = response.body();
                if (movieJsonResponse != null) {
                    List<Movie> movies = movieJsonConverter.convertMoviesToDomain(movieJsonResponse.results);
                    findAllCallback.onSuccess(movies);
                }
            }

            @Override
            public void onFailure(Call<MovieJsonResponse> call, Throwable t) {
                findAllCallback.onError();
            }
        });
    }

    @Override
    public void saveOne(Movie movie) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveMany(List<Movie> movies) {
        throw new UnsupportedOperationException();
    }
}
