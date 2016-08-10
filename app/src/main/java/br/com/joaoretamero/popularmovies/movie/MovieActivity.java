package br.com.joaoretamero.popularmovies.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.model.Genre;
import br.com.joaoretamero.popularmovies.model.Movie;
import br.com.joaoretamero.popularmovies.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.model.Video;
import io.realm.RealmResults;

public class MovieActivity extends AppCompatActivity implements MovieView {

    public static final String EXTRA_MOVIE_ID = "movie_id";

    private ImageView backdrop;
    private TextView title;
    private AppCompatRatingBar ratingBar;
    private TextView genres;
    private VideoAdapter videoAdapter;
    private TextView duration;
    private TextView overview;
    private TextView productionCompanies;
    private RecyclerView videosList;

    private MoviePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        initToolbar();
        initTitle();
        initRatingBar();
        initGenres();
        initDuration();
        initOverview();
        initProductionCompanies();
        initVideosList();

        presenter = new MoviePresenter(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initTitle() {
        title = (TextView) findViewById(R.id.movie_title);
    }

    private void initRatingBar() {
        ratingBar = (AppCompatRatingBar) findViewById(R.id.movie_vote_average);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1f);
        ratingBar.setRating(0f);
    }

    private void initGenres() {
        genres = (TextView) findViewById(R.id.movie_genres);
    }

    private void initDuration() {
        duration = (TextView) findViewById(R.id.movie_duration);
    }

    private void initOverview() {
        overview = (TextView) findViewById(R.id.movie_overview);
    }

    private void initProductionCompanies() {
        productionCompanies = (TextView) findViewById(R.id.movie_production_companies);
    }

    private void initVideosList() {
        videoAdapter = new VideoAdapter(MovieActivity.this);

        videosList = (RecyclerView) findViewById(R.id.movie_videos_list);
        videosList.setLayoutManager(new LinearLayoutManager(MovieActivity.this, LinearLayoutManager.VERTICAL, false));
        videosList.setItemAnimator(new DefaultItemAnimator());
        videosList.setAdapter(videoAdapter);
    }

    private int getMovieIdFromIntent() {
        int movieId = 0;

        Intent intent = getIntent();
        if (intent != null) {
            movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0);
        }

        return movieId;
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.start(getMovieIdFromIntent());
    }

    @Override
    public void bindData(Movie movie) {
        RealmResults<Genre> genresList = movie.genres.where().findAll();
        RealmResults<ProductionCompany> productionCompaniesList = movie.productionCompanies.where().findAll();

        title.setText(movie.title);
        ratingBar.setRating(convert10StarsValueTo5StarsValue(movie.voteAverage));
        genres.setText("None: " + genresList.size());
        duration.setText(movie.runtime + " min");
        overview.setText(movie.overview);
        productionCompanies.setText("None: " + productionCompaniesList.size());
    }

    @Override
    public void setVideoList(RealmResults<Video> videos) {
        videoAdapter.updateData(videos);
    }

    private float convert10StarsValueTo5StarsValue(float tenStarsValue) {
        return (tenStarsValue * 10) / (100 * 5);
    }
}
