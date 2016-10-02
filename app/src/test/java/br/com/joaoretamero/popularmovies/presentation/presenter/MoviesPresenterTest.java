package br.com.joaoretamero.popularmovies.presentation.presenter;

import org.junit.Before;
import org.junit.Test;

import br.com.joaoretamero.popularmovies.TestUseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMoviesUseCase;
import br.com.joaoretamero.popularmovies.infrastructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.presentation.contract.MoviesContract;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MoviesPresenterTest {

    private MoviesContract.View moviesView;
    private MovieRepository movieRepository;
    private MoviesPresenter moviesPresenter;
    private GetMoviesUseCase.Callback getMoviesUseCaseCallback;

    @Before
    public void setUp() throws Exception {
        moviesView = mock(MoviesContract.View.class);
        movieRepository = mock(MovieRepository.class);
        getMoviesUseCaseCallback = mock(GetMoviesUseCase.Callback.class);

        GetMoviesUseCase getMoviesUseCase = new GetMoviesUseCase(movieRepository);
        getMoviesUseCase.setCallback(getMoviesUseCaseCallback);

        moviesPresenter = new MoviesPresenter(moviesView, new TestUseCaseHandler(), getMoviesUseCase);
    }

    public void verifyShowMovies() {
        verify(getMoviesUseCaseCallback).onSuccess(anyObject());
        verify(moviesView).showRefreshIndicator(false);
        verify(moviesView).showMovies(anyList());
    }

    public void verifyErrosMessageShown() {
        verify(getMoviesUseCaseCallback).onError(any(Throwable.class));
        verify(moviesView).showRefreshIndicator(false);
        verify(moviesView).showErrorLoadingMovies();
        verifyNoMoreInteractions(moviesView);
    }

    @Test
    public void loadMoviesFromRepositoryAndShowThem() throws Exception {
        moviesPresenter.loadMovies("sort_order");
        verify(moviesView).showRefreshIndicator(true);
        verifyShowMovies();
    }

    @Test
    public void onItemClickThenShowMovieDetail() throws Exception {
        int movieId = 1;

        moviesPresenter.onItemClick(movieId);
        verify(moviesView).showMovieDetail(movieId);
        verifyNoMoreInteractions(moviesView);
    }

    @Test
    public void onRefreshLoadMoviesFromRepositoryAndShowThem() throws Exception {
        moviesPresenter.onRefresh("sort_order");
        verifyShowMovies();
    }

    @Test
    public void errorLoadingMovies_DisplayErrorMessage() throws Exception {
        moviesPresenter.loadMovies("sort_order");

        verify(moviesView).showRefreshIndicator(true);
        verifyErrosMessageShown();
    }

    @Test
    public void errorLoadingMoviesOnRefresh_DisplayErrorMessage() throws Exception {
        moviesPresenter.onRefresh("sort_order");
        verifyErrosMessageShown();
    }

}