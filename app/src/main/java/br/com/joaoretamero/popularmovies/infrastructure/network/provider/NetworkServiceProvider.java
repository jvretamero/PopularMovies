package br.com.joaoretamero.popularmovies.infrastructure.network.provider;

import br.com.joaoretamero.popularmovies.infrastructure.network.service.TheMovieDbService;

public class NetworkServiceProvider {

    public static TheMovieDbService provideTheMovieDbService() {
        return provideService(TheMovieDbService.class);
    }

    private static <T> T provideService(Class<T> serviceClass) {
        return RetrofitProvider.provide().create(serviceClass);
    }
}
