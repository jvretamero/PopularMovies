package br.com.joaoretamero.popularmovies.presentation.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.TestUseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMoviesUseCase;
import br.com.joaoretamero.popularmovies.infrastructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.presentation.contract.MoviesContract;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MoviesPresenterTest {

    private static List<Movie> MOVIES;
    private MovieRepository movieRepository;
    private MoviesContract.View moviesView;
    private MoviesPresenter moviesPresenter;
    private ArgumentCaptor<MovieRepository.FindAllCallback> movieRepositoryFindAllCallbackCaptor;
    private ArgumentCaptor<GetMoviesUseCase.Callback> getMoviesCallbackCaptor;

    @Before
    public void setUp() throws Exception {
        moviesView = mock(MoviesContract.View.class);
        movieRepository = mock(MovieRepository.class);

        movieRepositoryFindAllCallbackCaptor = ArgumentCaptor.forClass(MovieRepository.FindAllCallback.class);
        getMoviesCallbackCaptor = ArgumentCaptor.forClass(GetMoviesUseCase.Callback.class);

        TestUseCaseHandler testUseCaseHandler = new TestUseCaseHandler();
        GetMoviesUseCase getMoviesUseCase = new GetMoviesUseCase(movieRepository);

        moviesPresenter = new MoviesPresenter(moviesView, testUseCaseHandler, getMoviesUseCase);

        setUpMoviesList();
    }

    private void setUpMoviesList() {
        MOVIES = new ArrayList<>();
        MOVIES.add(new Movie(1));
        MOVIES.add(new Movie(2));
        MOVIES.add(new Movie(3));
    }

    public void verifyListMoviesAction() {
        verify(movieRepository).findAll(anyString(), movieRepositoryFindAllCallbackCaptor.capture());
        movieRepositoryFindAllCallbackCaptor.getValue().onSuccess(MOVIES);

        verify(moviesView).showRefreshIndicator(false);

        ArgumentCaptor<List> showMoviesArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(moviesView).showMovies(showMoviesArgumentCaptor.capture());

        assertThat(showMoviesArgumentCaptor.getValue().size(), is(equalTo(MOVIES.size())));
    }

    @Test
    public void loadMovies() throws Exception {
        moviesPresenter.loadMovies("sort_order");
        verify(moviesView).showRefreshIndicator(true);
        verifyListMoviesAction();
    }

    @Test
    public void onItemClick() throws Exception {
        int movieId = 1;

        moviesPresenter.onItemClick(movieId);
        verify(moviesView).showMovieDetail(movieId);
    }

    @Test
    public void onRefresh() throws Exception {
        moviesPresenter.onRefresh("sort_order");
        verifyListMoviesAction();
    }

}