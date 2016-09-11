package br.com.joaoretamero.popularmovies.infraestructure.network.provider;

public class ImageUrlProvider {

    private static final String POSTER_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185%s";
    private static final String BACKDROP_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w300%s";
    private static final String YOUTUBE_IMAGE_BASE_URL = "http://img.youtube.com/vi/%s/1.jpg";

    public static String providePosterUrl(String posterPath) {
        return String.format(POSTER_IMAGE_BASE_URL, posterPath);
    }

    public static String provideBackdropUrl(String backdropPath) {
        return String.format(BACKDROP_IMAGE_BASE_URL, backdropPath);
    }

    public static String provideYoutubeUrl(String youtubeId) {
        return String.format(YOUTUBE_IMAGE_BASE_URL, youtubeId);
    }
}
