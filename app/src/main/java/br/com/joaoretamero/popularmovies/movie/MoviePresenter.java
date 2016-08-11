package br.com.joaoretamero.popularmovies.movie;

import android.util.Log;

import br.com.joaoretamero.popularmovies.model.Movie;

public class MoviePresenter {

    private static final String TAG = MoviePresenter.class.getSimpleName();
    private MovieView view;

    public MoviePresenter(MovieView view) {
        this.view = view;
    }

    public void start(int movieId) {
        Log.d(TAG, "start > movieId: " + movieId);
        
        Movie movie = Movie.byMovieId(movieId);

        Log.d(TAG, "is null: " + (movie == null));

        if (movie != null) {
            view.bindData(movie);
        }
    }
}
