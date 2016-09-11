package br.com.joaoretamero.popularmovies.domain.threading.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import br.com.joaoretamero.popularmovies.domain.threading.UseCaseExecutor;

public class UseCaseExecutorImpl implements UseCaseExecutor {

    public static final int TIMEOUT = 120;
    private static UseCaseExecutorImpl instance;
    private ThreadPoolExecutor threadPoolExecutor;

    private UseCaseExecutorImpl() {
        int poolSize = Runtime.getRuntime().availableProcessors();
        int maxPoolSize = poolSize * 2;

        threadPoolExecutor = new ThreadPoolExecutor(poolSize, maxPoolSize, TIMEOUT, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(poolSize));
    }

    public static synchronized UseCaseExecutorImpl getInstance() {
        if (instance == null) {
            instance = new UseCaseExecutorImpl();
        }
        return instance;
    }

    @Override
    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }
}
