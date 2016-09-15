package br.com.joaoretamero.popularmovies.presentation.presenter;

import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMovieUseCase;
import br.com.joaoretamero.popularmovies.domain.usecase.UseCase;
import br.com.joaoretamero.popularmovies.presentation.contract.MovieContract;

public class MoviePresenter {

    private static final String TAG = MoviePresenter.class.getSimpleName();
    private MovieContract.View view;
    private UseCaseHandler useCaseHandler;
    private GetMovieUseCase getMovieUseCase;

    public MoviePresenter(MovieContract.View view, UseCaseHandler useCaseHandler, GetMovieUseCase getMovieUseCase) {
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.getMovieUseCase = getMovieUseCase;
    }

    public void start(int movieId) {
        useCaseHandler.execute(getMovieUseCase, movieId, new UseCase.Callback<Movie>() {
            @Override
            public void onSuccess(Movie response) {
                view.setMovie(response);
            }

            @Override
            public void onError(Throwable error) {
                view.showErrorMessage();
            }
        });
    }
}
