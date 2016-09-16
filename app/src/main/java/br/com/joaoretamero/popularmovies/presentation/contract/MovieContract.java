package br.com.joaoretamero.popularmovies.presentation.contract;

import br.com.joaoretamero.popularmovies.domain.model.Movie;

public interface MovieContract {

    void start(int movieId);

    interface View {

        void setMovie(Movie movie);

        void showErrorMessage();
    }

}
