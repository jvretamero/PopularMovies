package br.com.joaoretamero.popularmovies.presentation.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.domain.model.Genre;
import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.domain.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.domain.model.Video;
import br.com.joaoretamero.popularmovies.domain.threading.MainThread;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseExecutor;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.threading.impl.MainThreadImpl;
import br.com.joaoretamero.popularmovies.domain.threading.impl.UseCaseExecutorImpl;
import br.com.joaoretamero.popularmovies.domain.threading.impl.UseCaseHandlerImpl;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMovieUseCase;
import br.com.joaoretamero.popularmovies.infrastructure.MovieDataSource;
import br.com.joaoretamero.popularmovies.infrastructure.network.MovieNetworkSource;
import br.com.joaoretamero.popularmovies.infrastructure.network.converter.MovieJsonConverter;
import br.com.joaoretamero.popularmovies.infrastructure.network.provider.NetworkServiceProvider;
import br.com.joaoretamero.popularmovies.infrastructure.network.provider.PicassoProvider;
import br.com.joaoretamero.popularmovies.infrastructure.network.provider.UrlProvider;
import br.com.joaoretamero.popularmovies.infrastructure.network.service.TheMovieDbService;
import br.com.joaoretamero.popularmovies.infrastructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.infrastructure.repository.impl.MovieRepositoryImpl;
import br.com.joaoretamero.popularmovies.presentation.contract.MovieContract;
import br.com.joaoretamero.popularmovies.presentation.presenter.MoviePresenter;
import br.com.joaoretamero.popularmovies.presentation.ui.adapter.BaseAdapter;
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

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

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
        initVideosAdapter();
        initVideosList();

        MainThread mainThread = MainThreadImpl.getInstance();
        UseCaseExecutor useCaseExecutor = UseCaseExecutorImpl.getInstance();
        UseCaseHandler useCaseHandler = new UseCaseHandlerImpl(mainThread, useCaseExecutor);
        TheMovieDbService movieDbService = NetworkServiceProvider.provideTheMovieDbService();
        MovieJsonConverter movieJsonConverter = new MovieJsonConverter();
        MovieDataSource networkSource = new MovieNetworkSource(movieDbService, movieJsonConverter);
        MovieRepository movieRepository = new MovieRepositoryImpl(networkSource);
        GetMovieUseCase getMovieUseCase = new GetMovieUseCase(movieRepository);
        presenter = new MoviePresenter(this, useCaseHandler, getMovieUseCase);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initVideosAdapter() {
        videoAdapter = new VideoAdapter(MovieActivity.this);
        videoAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<Video>() {
            @Override
            public void onItemClick(Video item) {
                String youtubeVideoUrl = UrlProvider.provideYoutubeVideoUrl(item.getYoutubeId());

                Intent youtubeIntent = new Intent();
                youtubeIntent.setAction(Intent.ACTION_VIEW);
                youtubeIntent.setData(Uri.parse(youtubeVideoUrl));

                if (youtubeIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(youtubeIntent);
                }
            }
        });
    }

    private void initVideosList() {
        videosList.setLayoutManager(new GridLayoutManager(MovieActivity.this, 2, LinearLayoutManager.VERTICAL, false));
        videosList.setItemAnimator(new DefaultItemAnimator());
        videosList.setHasFixedSize(true);
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

        int movieId = getMovieIdFromIntent();
        presenter.start(movieId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setMovie(Movie movie) {
        // TODO criar drawable de erro
        PicassoProvider.provide(MovieActivity.this)
                .load(UrlProvider.provideBackdropUrl(movie.getBackdrop()))
                .fit()
                .centerCrop()
                .into(backdrop);

        toolbarLayout.setTitle(movie.getTitle());
        voteAverage.setText(String.valueOf(movie.getVoteAverage()));
        overview.setText(movie.getOverview());

        String durationStr = getResources().getString(R.string.movie_duration);
        duration.setText(String.format(durationStr, movie.getDurationInMinutes()));

        genres.setText(buildGenreLine(movie.getGenres()));
        productionCompanies.setText(buildProductionCompaniesLine(movie.getProductionCompanies()));
        videoAdapter.updateData(movie.getVideos());
    }

    private String buildGenreLine(List<Genre> genres) {
        StringBuilder sb = new StringBuilder();

        for (Genre genre : genres) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(genre.getName());
        }

        return sb.toString();
    }

    private String buildProductionCompaniesLine(List<ProductionCompany> productionCompanies) {
        StringBuilder sb = new StringBuilder();

        for (ProductionCompany productionCompany : productionCompanies) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(productionCompany.getName());
        }

        return sb.toString();
    }

    @Override
    public void showErrorMessage() {
        //TODO implementar
    }
}
