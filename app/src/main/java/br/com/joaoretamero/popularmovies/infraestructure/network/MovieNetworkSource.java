package br.com.joaoretamero.popularmovies.infraestructure.network;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.DomainMovie;
import br.com.joaoretamero.popularmovies.infraestructure.MovieDataSource;
import br.com.joaoretamero.popularmovies.infraestructure.network.converter.MovieJsonConverter;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.MovieJson;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.MovieJsonResponse;
import br.com.joaoretamero.popularmovies.infraestructure.network.service.TheMovieDbService;
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
                    DomainMovie domainMovie = movieJsonConverter.convertToDomainMovie(response.body());
                    findOneCallback.onSuccess(domainMovie);
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
                    List<DomainMovie> domainMovies = movieJsonConverter.convertToDomainMovies(movieJsonResponse.results);
                    findAllCallback.onSuccess(domainMovies);
                }
            }

            @Override
            public void onFailure(Call<MovieJsonResponse> call, Throwable t) {
                findAllCallback.onError();
            }
        });
    }

    @Override
    public void saveOne(DomainMovie movie) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveMany(List<DomainMovie> movies) {
        throw new UnsupportedOperationException();
    }
}
