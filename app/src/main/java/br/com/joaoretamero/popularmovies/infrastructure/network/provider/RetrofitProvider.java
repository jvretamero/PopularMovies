package br.com.joaoretamero.popularmovies.infrastructure.network.provider;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.io.IOException;

import br.com.joaoretamero.popularmovies.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class RetrofitProvider {

    private static final String TAG = RetrofitProvider.class.getSimpleName();
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String API_QUERY_PARAM = "api_key";
    private static Retrofit instance;

    public static synchronized Retrofit provide() {
        if (instance == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(LoganSquareConverterFactory.create());

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

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

            instance = retrofitBuilder
                    .client(httpClient.build())
                    .build();
        }
        return instance;
    }
}
