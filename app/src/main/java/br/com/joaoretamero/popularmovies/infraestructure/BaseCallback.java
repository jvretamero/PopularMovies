package br.com.joaoretamero.popularmovies.infraestructure;

public interface BaseCallback<T> {
    void onSuccess(T response);

    void onError();
}
