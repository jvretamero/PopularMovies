package br.com.joaoretamero.popularmovies.infrastructure.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.infrastructure.MovieDataSource;
import br.com.joaoretamero.popularmovies.infrastructure.repository.MovieRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MovieRepositoryImplTest {

    private MovieDataSource movieDataSource;
    private ArgumentCaptor<MovieDataSource.FindOneCallback> movieDataSourceFindOneCallback;
    private ArgumentCaptor<MovieDataSource.FindAllCallback> movieDataSourceFindallCallback;
    private MovieRepository.FindOneCallback movieRepositoryFindOneCallback;
    private MovieRepository.FindAllCallback movieRepositoryFindAllCallback;
    private MovieRepositoryImpl movieRepository;

    @Before
    public void setUp() throws Exception {
        movieDataSourceFindOneCallback = ArgumentCaptor.forClass(MovieDataSource.FindOneCallback.class);
        movieDataSourceFindallCallback = ArgumentCaptor.forClass(MovieDataSource.FindAllCallback.class);

        movieRepositoryFindOneCallback = mock(MovieRepository.FindOneCallback.class);
        movieRepositoryFindAllCallback = mock(MovieRepository.FindAllCallback.class);

        movieDataSource = mock(MovieDataSource.class);
        movieRepository = new MovieRepositoryImpl(movieDataSource);
    }

    @Test
    public void findOneWithSuccess() throws Exception {
        movieRepository.findOne(1, movieRepositoryFindOneCallback);

        verify(movieDataSource).findOne(anyInt(), movieDataSourceFindOneCallback.capture());
        movieDataSourceFindOneCallback.getValue().onSuccess(any(Movie.class));

        verifyNoMoreInteractions(movieDataSource);

        verify(movieRepositoryFindOneCallback).onSuccess(any(Movie.class));
    }

    @Test
    public void findOneWithError() throws Exception {
        movieRepository.findOne(1, movieRepositoryFindOneCallback);

        verify(movieDataSource).findOne(anyInt(), movieDataSourceFindOneCallback.capture());
        movieDataSourceFindOneCallback.getValue().onError();

        verifyNoMoreInteractions(movieDataSource);

        verify(movieRepositoryFindOneCallback).onError();
    }

    @Test
    public void findAllWithSuccess() throws Exception {
        movieRepository.findAll("sort_order", movieRepositoryFindAllCallback);

        verify(movieDataSource).findAll(anyString(), movieDataSourceFindallCallback.capture());
        movieDataSourceFindallCallback.getValue().onSuccess(anyList());

        verifyNoMoreInteractions(movieDataSource);

        verify(movieRepositoryFindAllCallback).onSuccess(anyList());
    }

    @Test
    public void findAllWithError() throws Exception {
        movieRepository.findAll("sort_order", movieRepositoryFindAllCallback);

        verify(movieDataSource).findAll(anyString(), movieDataSourceFindallCallback.capture());
        movieDataSourceFindallCallback.getValue().onError();

        verifyNoMoreInteractions(movieDataSource);

        verify(movieRepositoryFindAllCallback).onError();
    }

}