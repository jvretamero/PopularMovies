package br.com.joaoretamero.popularmovies.domain.repository;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.json.MovieJson;
import br.com.joaoretamero.popularmovies.domain.local.Movie;
import br.com.joaoretamero.popularmovies.domain.mapper.MovieJsonToLocalMapper;
import br.com.joaoretamero.popularmovies.network.Network;
import br.com.joaoretamero.popularmovies.network.TheMovieDbService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private ConnectivityManager connectivityManager;
    private TheMovieDbService theMovieDbService;

    public MovieRepository(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
        this.theMovieDbService = Network.createTheMovieDbService();
    }

    public void findAll(final String sortOrder, final FindAllCallback findAllCallback) {
        if (isNetworkAvailable()) {
            theMovieDbService.getMovies().enqueue(new Callback<List<MovieJson>>() {
                @Override
                public void onResponse(Call<List<MovieJson>> call, Response<List<MovieJson>> response) {
                    //TODO map from JSON to local database
                    mapMovieJsonListToMovieLocalList(response.body());
                    findAllFromLocal(sortOrder, findAllCallback);
                }

                @Override
                public void onFailure(Call<List<MovieJson>> call, Throwable t) {
                    if (findAllCallback != null) {
                        findAllCallback.onFail();
                    }
                }
            });
        } else {
            findAllFromLocal(sortOrder, findAllCallback);
        }
    }

    private void mapMovieJsonListToMovieLocalList(List<MovieJson> movieJsonList) {
        if (movieJsonList == null) {
            return;
        }

        MovieJsonToLocalMapper mapper = new MovieJsonToLocalMapper();
        List<Movie> movieList = mapper.mapJsonListToLocalList(movieJsonList);
        bulkInsert(movieList);
    }

    private void findAllFromLocal(String sortOrder, FindAllCallback findAllCallback) {
        if (findAllCallback != null) {
            findAllCallback.onSuccess(Movie.findAllSortedBy(sortOrder));
        }
    }

    public void bulkInsert(List<Movie> movieList) {
        Movie.bulkInsert(movieList);
    }

    private boolean isNetworkAvailable() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isAvailable();
    }

    public interface FindAllCallback {
        void onSuccess(List<Movie> movies);

        void onFail();
    }
}
