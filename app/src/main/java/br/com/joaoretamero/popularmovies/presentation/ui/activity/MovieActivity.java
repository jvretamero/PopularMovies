package br.com.joaoretamero.popularmovies.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.domain.model.Genre;
import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.domain.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.domain.threading.MainThread;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseExecutor;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.threading.impl.MainThreadImpl;
import br.com.joaoretamero.popularmovies.domain.threading.impl.UseCaseExecutorImpl;
import br.com.joaoretamero.popularmovies.domain.threading.impl.UseCaseHandlerImpl;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMovieUseCase;
import br.com.joaoretamero.popularmovies.infraestructure.MovieDataSource;
import br.com.joaoretamero.popularmovies.infraestructure.network.MovieNetworkSource;
import br.com.joaoretamero.popularmovies.infraestructure.network.converter.MovieJsonConverter;
import br.com.joaoretamero.popularmovies.infraestructure.network.provider.ImageUrlProvider;
import br.com.joaoretamero.popularmovies.infraestructure.network.provider.NetworkServiceProvider;
import br.com.joaoretamero.popularmovies.infraestructure.network.provider.PicassoProvider;
import br.com.joaoretamero.popularmovies.infraestructure.network.service.TheMovieDbService;
import br.com.joaoretamero.popularmovies.infraestructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.infraestructure.repository.impl.MovieRepositoryImpl;
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

        int movieId = getMovieIdFromIntent();
        presenter.start(movieId);
    }

    @Override
    public void setMovie(Movie movie) {
        // TODO criar drawable de erro
        PicassoProvider.provide(MovieActivity.this)
                .load(ImageUrlProvider.provideBackdropUrl(movie.getBackdrop()))
                .fit()
                .centerCrop()
                .into(backdrop);

        title.setText(movie.getTitle());
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
