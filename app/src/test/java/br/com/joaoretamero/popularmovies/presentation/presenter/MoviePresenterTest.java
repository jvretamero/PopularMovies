package br.com.joaoretamero.popularmovies.presentation.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.joaoretamero.popularmovies.TestUseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMovieUseCase;
import br.com.joaoretamero.popularmovies.infrastructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.presentation.contract.MovieContract;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MoviePresenterTest {

    private static Movie MOVIE;
    private MovieContract.View movieView;
    private MovieRepository movieRepository;
    private MoviePresenter moviePresenter;
    private ArgumentCaptor<MovieRepository.FindOneCallback> movieRepositoryFindOneCallback;

    @Before
    public void setUp() throws Exception {
        movieView = mock(MovieContract.View.class);
        movieRepository = mock(MovieRepository.class);
        movieRepositoryFindOneCallback = ArgumentCaptor.forClass(MovieRepository.FindOneCallback.class);

        MOVIE = new Movie(1);

        GetMovieUseCase getMovieUseCase = new GetMovieUseCase(movieRepository);

        moviePresenter = new MoviePresenter(movieView, new TestUseCaseHandler(), getMovieUseCase);
    }

    @Test
    public void findOneMovie_Show() throws Exception {
        moviePresenter.start(1);

        verify(movieRepository).findOne(anyInt(), movieRepositoryFindOneCallback.capture());
        movieRepositoryFindOneCallback.getValue().onSuccess(MOVIE);

        verify(movieView).setMovie(MOVIE);
        verifyNoMoreInteractions(movieView);
    }

    @Test
    public void findOneMovieError_ShowErrorMessage() throws Exception {
        moviePresenter.start(1);

        verify(movieRepository).findOne(anyInt(), movieRepositoryFindOneCallback.capture());
        movieRepositoryFindOneCallback.getValue().onError();

        verify(movieView).showErrorMessage();
        verifyNoMoreInteractions(movieView);
    }

}