package br.com.joaoretamero.popularmovies.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.joaoretamero.popularmovies.infrastructure.repository.MovieRepository;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class GetMoviesUseCaseTest {

    private MovieRepository movieRepository;
    private ArgumentCaptor<MovieRepository.FindAllCallback> movieRepositoryFindAllCallback;
    private GetMoviesUseCase.Callback getMoviesUseCaseCallback;
    private GetMoviesUseCase getMoviesUseCase;

    @Before
    public void setUp() throws Exception {
        movieRepositoryFindAllCallback = ArgumentCaptor.forClass(MovieRepository.FindAllCallback.class);
        getMoviesUseCaseCallback = mock(GetMoviesUseCase.Callback.class);

        movieRepository = mock(MovieRepository.class);
        getMoviesUseCase = new GetMoviesUseCase(movieRepository);
    }

    @Test
    public void executeWithSuccess() throws Exception {
        getMoviesUseCase.setCallback(getMoviesUseCaseCallback);
        getMoviesUseCase.execute("sort_order");

        verify(movieRepository).findAll(anyString(), movieRepositoryFindAllCallback.capture());
        movieRepositoryFindAllCallback.getValue().onSuccess(anyList());

        verifyNoMoreInteractions(movieRepository);

        verify(getMoviesUseCaseCallback).onSuccess(anyObject());

        verifyNoMoreInteractions(getMoviesUseCaseCallback);
    }

    @Test
    public void executeWithError() throws Exception {
        getMoviesUseCase.setCallback(getMoviesUseCaseCallback);
        getMoviesUseCase.execute("sort_order");

        verify(movieRepository).findAll(anyString(), movieRepositoryFindAllCallback.capture());
        movieRepositoryFindAllCallback.getValue().onError();

        verifyNoMoreInteractions(movieRepository);

        verify(getMoviesUseCaseCallback).onError(null);

        verifyNoMoreInteractions(getMoviesUseCaseCallback);
    }

}