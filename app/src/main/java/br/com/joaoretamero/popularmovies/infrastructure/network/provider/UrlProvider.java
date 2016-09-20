package br.com.joaoretamero.popularmovies.infrastructure.network.provider;

public class UrlProvider {

    private static final String POSTER_IMAGE_URL = "http://image.tmdb.org/t/p/w185%s";
    private static final String BACKDROP_IMAGE_URL = "http://image.tmdb.org/t/p/w300%s";
    private static final String YOUTUBE_IMAGE_URL = "http://img.youtube.com/vi/%s/1.jpg";
    private static final String YOUTUBE_VIDEO_URL = "http://youtube.com/v/%s";

    public static String providePosterUrl(String posterPath) {
        return String.format(POSTER_IMAGE_URL, posterPath);
    }

    public static String provideBackdropUrl(String backdropPath) {
        return String.format(BACKDROP_IMAGE_URL, backdropPath);
    }

    public static String provideYoutubeUrl(String youtubeId) {
        return String.format(YOUTUBE_IMAGE_URL, youtubeId);
    }

    public static String provideYoutubeVideoUrl(String youtubeId) {
        return String.format(YOUTUBE_VIDEO_URL, youtubeId);
    }
}
