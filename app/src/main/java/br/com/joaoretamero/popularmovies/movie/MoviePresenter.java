package br.com.joaoretamero.popularmovies.movie;

import java.util.List;

import br.com.joaoretamero.popularmovies.model.Movie;
import br.com.joaoretamero.popularmovies.model.Video;

public class MoviePresenter {

    private static final String TAG = MoviePresenter.class.getSimpleName();
    private MovieView view;

    public MoviePresenter(MovieView view) {
        this.view = view;
    }

    public void start(int movieId) {
        Movie movie = Movie.byMovieId(movieId);
        if (movie != null) {
            view.bindData(movie);

            List<Video> videos = movie.getVideos();
            view.setVideoList(videos);
        }
    }
}
