package br.com.joaoretamero.popularmovies.domain.threading.impl;

import android.os.Handler;
import android.os.Looper;

public class MainThreadImpl implements br.com.joaoretamero.popularmovies.domain.threading.MainThread {

    private static MainThreadImpl instance;
    private Handler handler;

    private MainThreadImpl() {
        handler = new Handler(Looper.getMainLooper());
    }

    public static synchronized MainThreadImpl getInstance() {
        if (instance == null) {
            instance = new MainThreadImpl();
        }
        return instance;
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
