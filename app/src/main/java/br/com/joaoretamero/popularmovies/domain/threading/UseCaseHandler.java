package br.com.joaoretamero.popularmovies.domain.threading;

import br.com.joaoretamero.popularmovies.domain.usecase.UseCase;

public interface UseCaseHandler {
    <T, H> void execute(final UseCase<T, H> useCase, final T requestValue, final UseCase.Callback<H> callback);
}
