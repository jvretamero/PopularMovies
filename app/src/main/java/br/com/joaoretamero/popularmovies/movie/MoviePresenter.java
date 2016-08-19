package br.com.joaoretamero.popularmovies.movie;

import br.com.joaoretamero.popularmovies.domain.local.Genre;
import br.com.joaoretamero.popularmovies.domain.local.Movie;
import br.com.joaoretamero.popularmovies.domain.local.ProductionCompany;
import br.com.joaoretamero.popularmovies.domain.local.Video;

public class MoviePresenter {

    private static final String TAG = MoviePresenter.class.getSimpleName();
    private MovieView view;

    public MoviePresenter(MovieView view) {
        this.view = view;
    }

    public void start(int movieId) {
        Movie movie = Movie.findByMovieId(movieId);
        if (movie != null) {
            view.setMovie(movie);
            view.setGenreList(Genre.findAllFromMovie(movie.getId()));
            view.setProductionCompaniesList(ProductionCompany.findAllFromMovie(movie.getId()));
            view.setVideoList(Video.findAllFromMovie(movie.getId()));
        }
    }
}
