package br.com.joaoretamero.popularmovies.network;

import android.content.Context;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import br.com.joaoretamero.popularmovies.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class Network {

    private static final String TAG = Network.class.getSimpleName();
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String API_QUERY_PARAM = "api_key";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(LoganSquareConverterFactory.create());

    public static TheMovieDbService createTheMovieDbService() {
        return createService(TheMovieDbService.class);
    }

    public static Picasso createPicasso(Context context) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(context, Long.MAX_VALUE))
                .build();
    }

    private static <T> T createService(Class<T> serviceClass) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        httpClient.addInterceptor(loggingInterceptor);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl originalHttpUrl = originalRequest.url();

                HttpUrl newHttpUrl = originalHttpUrl.newBuilder()
                        .removeAllQueryParameters(API_QUERY_PARAM)
                        .addQueryParameter(API_QUERY_PARAM, BuildConfig.API_KEY)
                        .build();

                Request newRequest = new Request.Builder()
                        .url(newHttpUrl)
                        .build();

                return chain.proceed(newRequest);
            }
        });

        OkHttpClient okHttpClient = httpClient.build();
        Retrofit retrofit = retrofitBuilder
                .client(okHttpClient)
                .build();
        return retrofit.create(serviceClass);
    }
}
