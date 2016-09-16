package br.com.joaoretamero.popularmovies.infraestructure.network.converter;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.domain.model.Genre;
import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.domain.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.domain.model.Video;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.GenreJson;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.MovieJson;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.ProductionCompanyJson;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.VideoJson;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.VideosJsonResponse;

public class MovieJsonConverter {

    public List<Movie> convertMoviesToDomain(List<MovieJson> movieJsons) {
        List<Movie> movies = new ArrayList<>(movieJsons.size());
        for (MovieJson movieJson : movieJsons) {
            movies.add(convertMovieToDomain(movieJson));
        }
        return movies;
    }

    public Movie convertMovieToDomain(MovieJson movieJson) {
        List<Video> videos = convertVideosToDomain(movieJson.videos);
        List<Genre> genres = convertGenresToDomain(movieJson.genres);
        List<ProductionCompany> productionCompanies = convertProductionCompaniesToDomain(movieJson.productionCompanies);

        Movie movie = new Movie(movieJson.id);
        movie.setVoteAverage(movieJson.voteAverage);
        movie.setTitle(movieJson.title);
        movie.setPoster(movieJson.poster);
        movie.setPopularity(movieJson.popularity);
        movie.setBackdrop(movieJson.backdrop);
        movie.setDurationInMinutes(movieJson.runtime);
        movie.setOverview(movieJson.overview);
        movie.setVideos(videos);
        movie.setGenres(genres);
        movie.setProductionCompanies(productionCompanies);

        return movie;
    }

    private List<Video> convertVideosToDomain(VideosJsonResponse videosJson) {
        List<VideoJson> videoJsonList = videosJson.results;
        List<Video> videoList = new ArrayList<>(videoJsonList.size());

        for (VideoJson videoJson : videoJsonList) {
            videoList.add(convertVideoToDomain(videoJson));
        }

        return videoList;
    }

    private Video convertVideoToDomain(VideoJson videoJson) {
        return new Video(videoJson.name, videoJson.key);
    }

    private List<Genre> convertGenresToDomain(List<GenreJson> genresJson) {
        List<Genre> genres = new ArrayList<>(genresJson.size());

        for (GenreJson genreJson : genresJson) {
            genres.add(convertGenreToDomain(genreJson));
        }

        return genres;
    }

    private Genre convertGenreToDomain(GenreJson genreJson) {
        return new Genre(genreJson.name);
    }

    private List<ProductionCompany> convertProductionCompaniesToDomain(List<ProductionCompanyJson> productionCompaniesJson) {
        List<ProductionCompany> productionCompanies = new ArrayList<>(productionCompaniesJson.size());

        for (ProductionCompanyJson productionCompanyJson : productionCompaniesJson) {
            productionCompanies.add(convertProductionCompanyToDomain(productionCompanyJson));
        }

        return productionCompanies;
    }

    private ProductionCompany convertProductionCompanyToDomain(ProductionCompanyJson productionCompanyJson) {
        return new ProductionCompany(productionCompanyJson.name);
    }
}
