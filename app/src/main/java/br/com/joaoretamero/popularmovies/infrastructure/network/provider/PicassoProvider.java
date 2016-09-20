package br.com.joaoretamero.popularmovies.infrastructure.network.provider;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class PicassoProvider {

    private static Picasso instance;

    public static synchronized Picasso provide(Context context) {
        if (instance == null) {
            Context applicationContext = context.getApplicationContext();

            instance = new Picasso.Builder(applicationContext)
                    .downloader(new OkHttp3Downloader(applicationContext, Long.MAX_VALUE))
                    .build();
        }
        return instance;
    }
}
