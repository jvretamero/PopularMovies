package br.com.joaoretamero.popularmovies.presentation.contract;

import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.DomainMovie;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.Genre;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.Video;

public interface MovieContract {

    void start(int movieId);

    interface View {

        void setMovie(DomainMovie movie);

        void setGenreList(List<Genre> genres);

        void setProductionCompaniesList(List<ProductionCompany> productionCompanies);

        void setVideoList(List<Video> videos);

        void showErrorMessage();
    }

}
