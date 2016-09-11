package br.com.joaoretamero.popularmovies.application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import br.com.joaoretamero.popularmovies.infraestructure.local.model.AppSettings;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.Genre;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.LocalMovie;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.MovieGenre;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.MovieProductionCompany;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.Video;

public class PopularMoviesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Configuration.Builder configuration = new Configuration.Builder(this);
        configuration.setDatabaseName("popmoviesdb");
        configuration.setDatabaseVersion(1);
        configureModels(configuration);

        ActiveAndroid.initialize(configuration.create());
    }

    public void configureModels(Configuration.Builder configuration) {
        configuration.addModelClass(AppSettings.class);
        configuration.addModelClass(Genre.class);
        configuration.addModelClass(LocalMovie.class);
        configuration.addModelClass(MovieGenre.class);
        configuration.addModelClass(MovieProductionCompany.class);
        configuration.addModelClass(ProductionCompany.class);
        configuration.addModelClass(Video.class);
    }
}
