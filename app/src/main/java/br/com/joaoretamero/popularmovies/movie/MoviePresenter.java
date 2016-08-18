package br.com.joaoretamero.popularmovies.movie;

import java.util.List;

import br.com.joaoretamero.popularmovies.model.local.Movie;
import br.com.joaoretamero.popularmovies.model.local.Video;

public class MoviePresenter {

    private static final String TAG = MoviePresenter.class.getSimpleName();
    private MovieView view;

    public MoviePresenter(MovieView view) {
        this.view = view;
    }

    public void start(int movieId) {
        Movie movie = Movie.findByMovieId(movieId);
        if (movie != null) {
            view.bindData(movie);

            List<Video> videos = movie.getVideos();
            view.setVideoList(videos);
        }
    }
}
