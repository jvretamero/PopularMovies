package br.com.joaoretamero.popularmovies.presentation.contract;

import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.storage.model.Movie;

public interface MoviesContract {

    interface View {
        void showMovieDetail(int movieId);

        void showSortingOptions();

        void showConfigurationScreen();

        void showErrorLoadingMovies();

        void showRefreshIndicator(boolean showRefreshIndicator);

        void showMovies(List<Movie> movies);
    }
}
