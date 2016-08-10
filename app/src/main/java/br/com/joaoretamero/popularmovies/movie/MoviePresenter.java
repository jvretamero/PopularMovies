package br.com.joaoretamero.popularmovies.movie;

import br.com.joaoretamero.popularmovies.model.Movie;
import br.com.joaoretamero.popularmovies.repository.MovieRepository;

public class MoviePresenter {

    private MovieView view;
    private MovieRepository movieRepository;

    public MoviePresenter(MovieView view) {
        this.view = view;
        this.movieRepository = new MovieRepository();
    }

    public void start(int movieId) {
        Movie movie = movieRepository.findById(movieId);
        view.bindData(movie);
    }
}
