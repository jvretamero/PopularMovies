package br.com.joaoretamero.popularmovies.domain.threading;

public interface MainThread {
    void post(Runnable runnable);
}
