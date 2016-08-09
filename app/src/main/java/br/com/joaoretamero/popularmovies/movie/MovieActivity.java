package br.com.joaoretamero.popularmovies.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.joaoretamero.popularmovies.R;

public class MovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "movie_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
    }
}
