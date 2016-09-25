package br.com.joaoretamero.popularmovies.infrastructure.network;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.Genre;
import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.domain.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.domain.model.Video;
import br.com.joaoretamero.popularmovies.infrastructure.MovieDataSource;

public class FakeMovieNetworkSource implements MovieDataSource {

    private List<Movie> movies;

    public FakeMovieNetworkSource() {
        movies = new ArrayList<>();

        setUpMovies();
    }

    private void setUpMovies() {
        Movie movie = new Movie(123);
        movie.setTitle("The title");
        movie.setBackdrop("/vsjBeMPZtyB7yNsYY56XYxifaQZ.jpg");
        movie.setDurationInMinutes(12);
        movie.setOverview("This is the overview");
        movie.setPopularity(4.4f);
        movie.setPoster("/cGOPbv9wA5gEejkUN892JrveARt.jpg");
        movie.setVoteAverage(4);
        movie.setGenres(getGenres());
        movie.setProductionCompanies(getProductionCompanies());
        movie.setVideos(getVideos());

        movies.add(movie);
    }

    @NonNull
    private List<Video> getVideos() {
        List<Video> videos = new ArrayList<>();
        videos.add(new Video("Trailer #1", "SUXWAEX2jlg"));
        return videos;
    }

    @NonNull
    private List<ProductionCompany> getProductionCompanies() {
        List<ProductionCompany> productionCompanies = new ArrayList<>();
        productionCompanies.add(new ProductionCompany("Pixar"));
        return productionCompanies;
    }

    @NonNull
    private List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Action"));
        genres.add(new Genre("Terror"));
        return genres;
    }

    @Override
    public void findOne(int movieId, FindOneCallback findOneCallback) {
        Iterator<Movie> movieJsonIterator = movies.iterator();

        while (movieJsonIterator.hasNext()) {
            Movie next = movieJsonIterator.next();

            if (next.getId() == movieId) {
                if (findOneCallback != null) {
                    findOneCallback.onSuccess(next);
                    return;
                }
            }
        }

        if (findOneCallback != null) {
            findOneCallback.onError();
        }
    }

    @Override
    public void findAll(String sortOrder, FindAllCallback findAllCallback) {
        if (findAllCallback != null) {
            findAllCallback.onSuccess(movies);
        }
    }

    @Override
    public void saveOne(Movie movie) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveMany(List<Movie> movies) {
        throw new UnsupportedOperationException();
    }
}

