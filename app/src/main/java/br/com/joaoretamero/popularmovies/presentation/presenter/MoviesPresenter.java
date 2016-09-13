package br.com.joaoretamero.popularmovies.presentation.presenter;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.DomainMovie;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMoviesUseCase;
import br.com.joaoretamero.popularmovies.domain.usecase.UseCase;
import br.com.joaoretamero.popularmovies.presentation.contract.MoviesContract;

public class MoviesPresenter implements MoviesContract {

    private final static String TAG = MoviesPresenter.class.getSimpleName();
    private MoviesContract.View view;
    private GetMoviesUseCase getMoviesUseCase;
    private UseCaseHandler useCaseHandler;
    private boolean firstLoad;

    public MoviesPresenter(MoviesContract.View view, UseCaseHandler useCaseHandler, GetMoviesUseCase getMoviesUseCase) {
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.getMoviesUseCase = getMoviesUseCase;
        this.firstLoad = true;
    }

    public void loadMovies(String sortOrder) {
        view.showRefreshIndicator(true);
        listMovies(sortOrder);
        firstLoad = false;
    }

    public void onItemClick(int movieId) {
        view.showMovieDetail(movieId);
    }

    public void onRefresh(String sortOrder) {
        listMovies(sortOrder);
    }

    public void onSortMenuClick() {
        view.showSortingOptions();
    }

    public void onConfigurationMenuClick() {
        view.showConfigurationScreen();
    }

    private void listMovies(String sortOrder) {
        useCaseHandler.execute(getMoviesUseCase, sortOrder, new UseCase.Callback<List<DomainMovie>>() {
            @Override
            public void onSuccess(List<DomainMovie> response) {
                view.showMovies(response);
                view.showRefreshIndicator(false);
            }

            @Override
            public void onError(Throwable error) {
                view.showErrorLoadingMovies();
                view.showRefreshIndicator(false);
            }
        });
    }
}
