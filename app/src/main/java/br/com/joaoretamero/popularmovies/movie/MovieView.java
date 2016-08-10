package br.com.joaoretamero.popularmovies.movie;

import br.com.joaoretamero.popularmovies.model.Movie;
import br.com.joaoretamero.popularmovies.model.Video;
import io.realm.RealmResults;

public interface MovieView {

    void bindData(Movie movie);

    void setVideoList(RealmResults<Video> videos);
}
