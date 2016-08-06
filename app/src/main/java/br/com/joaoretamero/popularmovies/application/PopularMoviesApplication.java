package br.com.joaoretamero.popularmovies.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PopularMoviesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration.Builder configurationBuilder = new RealmConfiguration.Builder(this);
        configurationBuilder.name("popmoviesdb");
        configurationBuilder.schemaVersion(1l);

        Realm.setDefaultConfiguration(configurationBuilder.build());
    }
}
