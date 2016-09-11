package br.com.joaoretamero.popularmovies.domain.repository;

public interface BaseRepositoryCallback<T> {
    void onSuccess(T response);

    void onError();
}
