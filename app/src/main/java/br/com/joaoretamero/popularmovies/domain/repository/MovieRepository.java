package br.com.joaoretamero.popularmovies.domain.repository;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.infraestructure.network.converter.MovieConverter;
import br.com.joaoretamero.popularmovies.infraestructure.network.converter.VideoConverter;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.GenreJson;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.MovieJson;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.MovieJsonResponse;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.ProductionCompanyJson;
import br.com.joaoretamero.popularmovies.infraestructure.network.model.VideosJsonResponse;
import br.com.joaoretamero.popularmovies.infraestructure.network.provider.NetworkServiceProvider;
import br.com.joaoretamero.popularmovies.infraestructure.network.service.TheMovieDbService;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.Genre;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.Movie;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.MovieGenre;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.MovieProductionCompany;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.Video;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();

    private ConnectivityManager connectivityManager;
    private TheMovieDbService theMovieDbService;

    public MovieRepository(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
        this.theMovieDbService = NetworkServiceProvider.provideTheMovieDbService();
    }

    public void findOne(final int movieId, final FindOneCallback findOneCallback) {
        if (isNetworkAvailable()) {
            theMovieDbService.getMovie(movieId).enqueue(new Callback<MovieJson>() {
                @Override
                public void onResponse(Call<MovieJson> call, Response<MovieJson> response) {
                    if (response.isSuccessful()) {
                        mapOneAndUpdate(response.body());
                        findOneFromLocal(movieId, findOneCallback);
                    } else {
                        callFail(findOneCallback);
                    }
                }

                @Override
                public void onFailure(Call<MovieJson> call, Throwable t) {
                    callFail(findOneCallback);
                }
            });
        } else {
            findOneFromLocal(movieId, findOneCallback);
        }
    }

    private void findOneFromLocal(int movieId, FindOneCallback findOneCallback) {
        if (findOneCallback != null) {
            findOneCallback.onSuccess(Movie.findByMovieId(movieId));
        }
    }

    private void mapOneAndUpdate(MovieJson movieJson) {
        Movie movie = updateMovie(movieJson);
        saveVideosFromMovie(movie, movieJson.videos);
        saveGenresFromMovie(movie, movieJson.genres);
        saveProductionCompaniesFromMovie(movie, movieJson.productionCompanies);
    }

    public Movie updateMovie(MovieJson movieJson) {
        Movie movieLocal = Movie.findByMovieId(movieJson.id);
        if (movieLocal == null) {
            movieLocal = new Movie();
            movieLocal.movieId = movieJson.id;
        }
        movieLocal.voteAverage = movieJson.voteAverage;
        movieLocal.title = movieJson.title;
        movieLocal.poster = movieJson.poster;
        movieLocal.popularity = movieJson.popularity;
        movieLocal.backdrop = movieJson.backdrop;
        movieLocal.durationInMinutes = movieJson.runtime;
        movieLocal.overview = movieJson.overview;
        movieLocal.save();

        return movieLocal;
    }

    public void saveVideosFromMovie(Movie movie, VideosJsonResponse videosJsonResponse) {
        if (videosJsonResponse == null || videosJsonResponse.results == null ||
                videosJsonResponse.results.size() == 0) {
            return;
        }

        VideoConverter videoMapper = new VideoConverter();
        List<Video> videos = videoMapper.convertListToStorageModel(videosJsonResponse.results);
        Video.clearAllFromMovie(movie.getId());
        Video.bulkInsert(movie, videos);
    }

    public void saveGenresFromMovie(Movie movie, List<GenreJson> genreJsonList) {
        if (genreJsonList == null || genreJsonList.size() == 0) {
            return;
        }

        List<Genre> genreList = saveGenres(genreJsonList);
        MovieGenre.clearAllFromMovie(movie.getId());
        MovieGenre.bulkInsert(movie, genreList);
    }

    private List<Genre> saveGenres(List<GenreJson> genreJsonList) {
        List<Genre> genreList = new ArrayList<Genre>(genreJsonList.size());
        Genre genre;

        ActiveAndroid.beginTransaction();
        try {
            for (GenreJson genreJson : genreJsonList) {
                genre = Genre.findOneByGenreId(genreJson.id);
                if (genre == null) {
                    genre = new Genre();
                    genre.genreId = genreJson.id;
                }
                genre.name = genreJson.name;
                genre.save();

                genreList.add(genre);
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }

        return genreList;
    }

    private void saveProductionCompaniesFromMovie(Movie movie, List<ProductionCompanyJson> productionCompanyJsonList) {
        if (productionCompanyJsonList == null || productionCompanyJsonList.size() == 0) {
            return;
        }

        List<ProductionCompany> productionCompanies = saveProductionCompanies(productionCompanyJsonList);
        MovieProductionCompany.clearAllFromMovie(movie.getId());
        MovieProductionCompany.bulkInsert(movie, productionCompanies);
    }

    private List<ProductionCompany> saveProductionCompanies(List<ProductionCompanyJson> productionCompanyJsonList) {
        List<ProductionCompany> productionCompanies = new ArrayList<ProductionCompany>(productionCompanyJsonList.size());
        ProductionCompany productionCompany;

        ActiveAndroid.beginTransaction();
        try {
            for (ProductionCompanyJson productionCompanyJson : productionCompanyJsonList) {
                productionCompany = ProductionCompany.findOneFromProductionCompanyId(productionCompanyJson.id);
                if (productionCompany == null) {
                    productionCompany = new ProductionCompany();
                    productionCompany.productionCompanyId = productionCompanyJson.id;
                }
                productionCompany.name = productionCompanyJson.name;
                productionCompany.save();

                productionCompanies.add(productionCompany);
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }

        return productionCompanies;
    }

    public void findAll(final String sortOrder, final FindAllCallback findAllCallback) {
        if (isNetworkAvailable()) {
            theMovieDbService.getMovies().enqueue(new Callback<MovieJsonResponse>() {
                @Override
                public void onResponse(Call<MovieJsonResponse> call, Response<MovieJsonResponse> response) {
                    if (response.isSuccessful()) {
                        mapCleanAndInsert(response.body());
                        findAllFromLocal(sortOrder, findAllCallback);
                    } else {
                        callFail(findAllCallback);
                    }
                }

                @Override
                public void onFailure(Call<MovieJsonResponse> call, Throwable t) {
                    callFail(findAllCallback);
                }
            });
        } else {
            findAllFromLocal(sortOrder, findAllCallback);
        }
    }

    private void mapCleanAndInsert(MovieJsonResponse movieJsonResponse) {
        List<Movie> movieList = mapMovieJsonListToMovieLocalList(movieJsonResponse);
        Video.clearAll();
        MovieGenre.clearAll();
        MovieProductionCompany.clearAll();
        Movie.clearAll();
        Movie.bulkInsert(movieList);
    }

    private List<Movie> mapMovieJsonListToMovieLocalList(MovieJsonResponse movieJsonResponse) {
        if (movieJsonResponse == null || movieJsonResponse.results == null) {
            return new ArrayList<Movie>();
        }

        MovieConverter mapper = new MovieConverter();
        return mapper.convertListToStorageModel(movieJsonResponse.results);
    }

    private void findAllFromLocal(String sortOrder, FindAllCallback findAllCallback) {
        if (findAllCallback != null) {
            findAllCallback.onSuccess(Movie.findAllSortedBy(sortOrder));
        }
    }

    private void callFail(DefaultCallback callback) {
        if (callback != null) {
            callback.onFail();
        }
    }

    private boolean isNetworkAvailable() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isAvailable();
    }

    private interface DefaultCallback {
        void onFail();
    }

    public interface FindAllCallback extends DefaultCallback {
        void onSuccess(List<Movie> movies);
    }

    public interface FindOneCallback extends DefaultCallback {
        void onSuccess(Movie movie);
    }
}
