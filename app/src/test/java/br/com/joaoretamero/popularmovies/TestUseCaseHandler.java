package br.com.joaoretamero.popularmovies;

import br.com.joaoretamero.popularmovies.domain.threading.UseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.usecase.UseCase;

public class TestUseCaseHandler implements UseCaseHandler {

    @Override
    public <T, H> void execute(UseCase<T, H> useCase, T requestValue, UseCase.Callback<H> callback) {
        useCase.setCallback(callback);
        useCase.execute(requestValue);
    }
}
