package br.com.joaoretamero.popularmovies.presentation.contract;

import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.storage.model.Genre;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.Movie;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.Video;

public interface MovieContract {

    void start(int movieId);

    interface View {

        void setMovie(Movie movie);

        void setGenreList(List<Genre> genres);

        void setProductionCompaniesList(List<ProductionCompany> productionCompanies);

        void setVideoList(List<Video> videos);

        void showErrorMessage();
    }

}
