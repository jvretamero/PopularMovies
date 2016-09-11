package br.com.joaoretamero.popularmovies.presentation.ui.activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.domain.local.Genre;
import br.com.joaoretamero.popularmovies.domain.local.Movie;
import br.com.joaoretamero.popularmovies.domain.local.ProductionCompany;
import br.com.joaoretamero.popularmovies.domain.local.Video;
import br.com.joaoretamero.popularmovies.network.ImageUrlBuilder;
import br.com.joaoretamero.popularmovies.network.Network;
import br.com.joaoretamero.popularmovies.presentation.contract.MovieContract;
import br.com.joaoretamero.popularmovies.presentation.presenter.MoviePresenter;
import br.com.joaoretamero.popularmovies.presentation.ui.adapter.VideoAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity implements MovieContract.View {

    public static final String TAG = MovieActivity.class.getSimpleName();
    public static final String EXTRA_MOVIE_ID = "movie_id";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.movie_backdrop)
    ImageView backdrop;

    @BindView(R.id.movie_title)
    TextView title;

    @BindView(R.id.movie_vote_average)
    TextView voteAverage;

    @BindView(R.id.movie_genres)
    TextView genres;

    @BindView(R.id.movie_duration)
    TextView duration;

    @BindView(R.id.movie_overview)
    TextView overview;

    @BindView(R.id.movie_production_companies)
    TextView productionCompanies;

    @BindView(R.id.movie_videos_list)
    RecyclerView videosList;

    private VideoAdapter videoAdapter;
    private MoviePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.bind(this);

        initToolbar();
        initVideosList();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        presenter = new MoviePresenter(this, connectivityManager);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initVideosList() {
        videoAdapter = new VideoAdapter(MovieActivity.this);

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
    public void setMovie(Movie movie) {
        // TODO criar drawable de erro
        Network.createPicasso(MovieActivity.this)
                .load(ImageUrlBuilder.getBackdropImageUri(movie.backdrop))
                .fit()
                .centerCrop()
                .into(backdrop);

        title.setText(movie.title);
        voteAverage.setText(String.valueOf(movie.voteAverage));
        overview.setText(movie.overview);

        String durationStr = getResources().getString(R.string.movie_duration);
        duration.setText(String.format(durationStr, movie.durationInMinutes));
    }

    @Override
    public void setGenreList(List<Genre> genresList) {
        genres.setText(buildGenreLine(genresList));
    }

    @Override
    public void setProductionCompaniesList(List<ProductionCompany> productionCompaniesList) {
        productionCompanies.setText(buildProductionCompaniesLine(productionCompaniesList));
    }

    private String buildGenreLine(List<Genre> genres) {
        StringBuilder sb = new StringBuilder();

        for (Genre genre : genres) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(genre.name);
        }

        return sb.toString();
    }

    private String buildProductionCompaniesLine(List<ProductionCompany> productionCompanies) {
        StringBuilder sb = new StringBuilder();

        for (ProductionCompany productionCompany : productionCompanies) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(productionCompany.name);
        }

        return sb.toString();
    }

    @Override
    public void setVideoList(List<Video> videos) {
        Log.d(TAG, "setVideoList");
        Log.d(TAG, "video count: " + videos.size());

        videoAdapter.updateData(videos);
    }

    @Override
    public void showErrorMessage() {
        //TODO implementar
    }
}
