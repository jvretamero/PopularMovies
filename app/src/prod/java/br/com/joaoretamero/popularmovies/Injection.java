package br.com.joaoretamero.popularmovies;

import br.com.joaoretamero.popularmovies.domain.threading.MainThread;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseExecutor;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.threading.impl.MainThreadImpl;
import br.com.joaoretamero.popularmovies.domain.threading.impl.UseCaseExecutorImpl;
import br.com.joaoretamero.popularmovies.domain.threading.impl.UseCaseHandlerImpl;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMovieUseCase;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMoviesUseCase;
import br.com.joaoretamero.popularmovies.infrastructure.MovieDataSource;
import br.com.joaoretamero.popularmovies.infrastructure.network.MovieNetworkSource;
import br.com.joaoretamero.popularmovies.infrastructure.network.converter.MovieJsonConverter;
import br.com.joaoretamero.popularmovies.infrastructure.network.provider.RetrofitProvider;
import br.com.joaoretamero.popularmovies.infrastructure.network.service.TheMovieDbService;
import br.com.joaoretamero.popularmovies.infrastructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.infrastructure.repository.impl.MovieRepositoryImpl;

public class Injection {

    public static UseCaseHandler provideUseCaseHandler() {
        MainThread mainThread = MainThreadImpl.getInstance();
        UseCaseExecutor useCaseExecutor = UseCaseExecutorImpl.getInstance();

        return new UseCaseHandlerImpl(mainThread, useCaseExecutor);
    }

    public static MovieDataSource provideMovieNetworkSource() {
        TheMovieDbService theMovieDbService = RetrofitProvider.provide().create(TheMovieDbService.class);
        MovieJsonConverter movieJsonConverter = new MovieJsonConverter();

        return new MovieNetworkSource(theMovieDbService, movieJsonConverter);
    }

    public static MovieRepository provideMovieRepository() {
        return new MovieRepositoryImpl(provideMovieNetworkSource());
    }

    public static GetMoviesUseCase provideGetMoviesUseCase() {
        return new GetMoviesUseCase(provideMovieRepository());
    }

    public static GetMovieUseCase provideGetMovieUseCase() {
        return new GetMovieUseCase(provideMovieRepository());
    }
}
