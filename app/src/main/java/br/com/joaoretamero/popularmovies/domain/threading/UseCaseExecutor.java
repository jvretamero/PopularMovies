package br.com.joaoretamero.popularmovies.domain.threading;

public interface UseCaseExecutor {
    void execute(Runnable runnable);
}
