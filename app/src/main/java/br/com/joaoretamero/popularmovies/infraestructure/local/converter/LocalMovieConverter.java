package br.com.joaoretamero.popularmovies.infraestructure.local.converter;

import br.com.joaoretamero.popularmovies.domain.model.DomainMovie;
import br.com.joaoretamero.popularmovies.infraestructure.local.model.LocalMovie;

public class LocalMovieConverter {

    public DomainMovie convertToDomainMovie(LocalMovie localModel) {
        DomainMovie movie = new DomainMovie(localModel.movieId);
        movie.setBackdrop(localModel.backdrop);
        movie.setDurationInMinutes(localModel.durationInMinutes);
        movie.setOverview(localModel.overview);
        movie.setPopularity(localModel.popularity);
        movie.setPoster(localModel.poster);
        movie.setTitle(localModel.title);
        movie.setVoteAverage(localModel.voteAverage);
        return movie;
    }

    public LocalMovie fromDomainMovie(DomainMovie domainMovie) {
        LocalMovie localMovie = new LocalMovie();
        localMovie.backdrop = domainMovie.getBackdrop();
        localMovie.durationInMinutes = domainMovie.getDurationInMinutes();
        localMovie.overview = domainMovie.getOverview();
        localMovie.popularity = domainMovie.getPopularity();
        localMovie.poster = domainMovie.getPoster();
        localMovie.title = domainMovie.getTitle();
        localMovie.voteAverage = domainMovie.getVoteAverage();
        return localMovie;
    }
}
