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
    private ArgumentCaptor<MovieRepository.FindAllCallback> movieRepositoryFindAllCallback;

    @Before
    public void setUp() throws Exception {
        moviesView = mock(MoviesContract.View.class);
        movieRepository = mock(MovieRepository.class);

        movieRepositoryFindAllCallback = ArgumentCaptor.forClass(MovieRepository.FindAllCallback.class);

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

    public void verifyShowMovies() {
        verify(movieRepository).findAll(anyString(), movieRepositoryFindAllCallback.capture());
        movieRepositoryFindAllCallback.getValue().onSuccess(MOVIES);

        verify(moviesView).showRefreshIndicator(false);

        ArgumentCaptor<List> showMoviesArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(moviesView).showMovies(showMoviesArgumentCaptor.capture());

        assertThat(showMoviesArgumentCaptor.getValue().size(), is(equalTo(MOVIES.size())));
    }

    public void verifyErrosMessageShown() {
        verify(movieRepository).findAll(anyString(), movieRepositoryFindAllCallback.capture());
        movieRepositoryFindAllCallback.getValue().onError();

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