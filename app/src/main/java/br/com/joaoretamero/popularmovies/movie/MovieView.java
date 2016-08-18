package br.com.joaoretamero.popularmovies.movie;

import java.util.List;

import br.com.joaoretamero.popularmovies.model.local.Movie;
import br.com.joaoretamero.popularmovies.model.local.Video;

public interface MovieView {

    void bindData(Movie movie);

    void setVideoList(List<Video> videos);
}
