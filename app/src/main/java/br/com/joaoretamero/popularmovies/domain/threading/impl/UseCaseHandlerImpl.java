package br.com.joaoretamero.popularmovies.domain.threading.impl;

import br.com.joaoretamero.popularmovies.domain.threading.MainThread;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseExecutor;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.usecase.UseCase;

public class UseCaseHandlerImpl implements UseCaseHandler {

    private MainThread mainThread;
    private UseCaseExecutor useCaseExecutor;

    public UseCaseHandlerImpl(MainThread mainThread, UseCaseExecutor useCaseExecutor) {
        this.mainThread = mainThread;
        this.useCaseExecutor = useCaseExecutor;
    }

    @Override
    public <T, H> void execute(final UseCase<T, H> useCase, final T requestValue, final UseCase.Callback<H> callback) {
        useCase.setCallback(new UseCase.Callback<H>() {
            @Override
            public void onSuccess(final H response) {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(response);
                    }
                });
            }

            @Override
            public void onError(final Throwable error) {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(error);
                    }
                });
            }
        });

        useCaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                useCase.execute(requestValue);
            }
        });
    }
}
