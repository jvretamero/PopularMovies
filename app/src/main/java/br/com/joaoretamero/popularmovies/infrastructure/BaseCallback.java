package br.com.joaoretamero.popularmovies.infrastructure;

public interface BaseCallback<T> {
    void onSuccess(T response);

    void onError();
}
