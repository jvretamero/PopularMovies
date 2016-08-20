package br.com.joaoretamero.popularmovies.domain.repository;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.domain.json.MovieJsonResponse;
import br.com.joaoretamero.popularmovies.domain.local.Movie;
import br.com.joaoretamero.popularmovies.domain.mapper.MovieJsonToLocalMapper;
import br.com.joaoretamero.popularmovies.network.Network;
import br.com.joaoretamero.popularmovies.network.TheMovieDbService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();

    private ConnectivityManager connectivityManager;
    private TheMovieDbService theMovieDbService;

    public MovieRepository(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
        this.theMovieDbService = Network.createTheMovieDbService();
    }

    public void findAll(final String sortOrder, final FindAllCallback findAllCallback) {
        Log.d(TAG, "findAll");
        if (isNetworkAvailable()) {
            Log.d(TAG, "newtork available");
            theMovieDbService.getMovies().enqueue(new Callback<MovieJsonResponse>() {
                @Override
                public void onResponse(Call<MovieJsonResponse> call, Response<MovieJsonResponse> response) {
                    Log.d(TAG, "request has response");
                    Log.d(TAG, "url: " + call.request().url().toString());
                    Log.d(TAG, response.raw().toString());

                    if (response.isSuccessful()) {
                        Log.d(TAG, "response was successfull");
                        mapCleanAndInsert(response.body());
                        findAllFromLocal(sortOrder, findAllCallback);
                    } else {
                        Log.d(TAG, "response wasnt successfull");
                        if (findAllCallback != null) {
                            findAllCallback.onFail();
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieJsonResponse> call, Throwable t) {
                    Log.d(TAG, "request has failed");
                    if (findAllCallback != null) {
                        findAllCallback.onFail();
                    }
                }
            });
        } else {
            Log.d(TAG, "network unavailable");
            findAllFromLocal(sortOrder, findAllCallback);
        }
    }

    private void mapCleanAndInsert(MovieJsonResponse movieJsonResponse) {
        List<Movie> movieList = mapMovieJsonListToMovieLocalList(movieJsonResponse);
        Log.d(TAG, "movies mapped: " + movieList.size());
        clearAll();
        bulkInsert(movieList);
    }

    private List<Movie> mapMovieJsonListToMovieLocalList(MovieJsonResponse movieJsonResponse) {
        if (movieJsonResponse == null || movieJsonResponse.results == null) {
            Log.d(TAG, "sorry, empty response");
            return new ArrayList<Movie>();
        }

        Log.d(TAG, "mapping json to local: " + movieJsonResponse.results.size());

        MovieJsonToLocalMapper mapper = new MovieJsonToLocalMapper();
        return mapper.mapJsonListToLocalList(movieJsonResponse.results);
    }

    private void findAllFromLocal(String sortOrder, FindAllCallback findAllCallback) {
        Log.d(TAG, "findAllFromLocal");
        if (findAllCallback != null) {
            Log.d(TAG, "calling onSuccess");
            findAllCallback.onSuccess(Movie.findAllSortedBy(sortOrder));
        }
    }

    public void clearAll() {
        Movie.clearAll();
    }

    public void bulkInsert(List<Movie> movieList) {
        Log.d(TAG, "bulkInsert");
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
