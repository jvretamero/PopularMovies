package br.com.joaoretamero.popularmovies.presentation.contract;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.local.Movie;

public interface MoviesContract {

    void start(String sortOrder);

    void onItemClick(int movieId);

    void onRefresh(String sortOrder);

    void onSortMenuClick();

    void onConfigurationMenuClick();

    interface View {
        void showMovieDetail(int movieId);

        void showSortingOptions();

        void showConfigurationScreen();

        void showErrorLoadingMovies();

        void showRefreshIndicator(boolean showRefreshIndicator);

        void showMovies(List<Movie> movies);
    }
}
