package br.com.joaoretamero.popularmovies.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.domain.threading.MainThread;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseExecutor;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.threading.impl.MainThreadImpl;
import br.com.joaoretamero.popularmovies.domain.threading.impl.UseCaseExecutorImpl;
import br.com.joaoretamero.popularmovies.domain.threading.impl.UseCaseHandlerImpl;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMoviesUseCase;
import br.com.joaoretamero.popularmovies.infraestructure.MovieDataSource;
import br.com.joaoretamero.popularmovies.infraestructure.network.MovieNetworkSource;
import br.com.joaoretamero.popularmovies.infraestructure.network.converter.MovieJsonConverter;
import br.com.joaoretamero.popularmovies.infraestructure.network.provider.NetworkServiceProvider;
import br.com.joaoretamero.popularmovies.infraestructure.network.service.TheMovieDbService;
import br.com.joaoretamero.popularmovies.infraestructure.repository.MovieRepository;
import br.com.joaoretamero.popularmovies.infraestructure.repository.impl.MovieRepositoryImpl;
import br.com.joaoretamero.popularmovies.presentation.contract.MoviesContract;
import br.com.joaoretamero.popularmovies.presentation.presenter.MoviesPresenter;
import br.com.joaoretamero.popularmovies.presentation.ui.adapter.BaseAdapter;
import br.com.joaoretamero.popularmovies.presentation.ui.adapter.MoviesAdapter;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends AppCompatActivity implements MoviesContract.View {

    private static final String TAG = MoviesActivity.class.getSimpleName();

    @BindView(R.id.movies_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.movies_list)
    RecyclerView moviesList;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindString(R.string.default_order)
    String sortOrder;

    @BindString(R.string.popularity_order)
    String popularityOrder;

    @BindString(R.string.vote_average_order)
    String voteAverageOrder;

    private MoviesPresenter presenter;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);

        initToolbar();
        initAdapter();
        initRefreshLayout();
        initList();
        initPresenter();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(MoviesActivity.this);
        moviesAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<Movie>() {
            @Override
            public void onItemClick(Movie movie) {
                presenter.onItemClick(movie.getId());
            }
        });
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh(sortOrder);
            }
        });
    }

    private void initList() {
        moviesList.setLayoutManager(new GridLayoutManager(MoviesActivity.this, 2));
        moviesList.setItemAnimator(new DefaultItemAnimator());
        moviesList.setAdapter(moviesAdapter);
    }

    private void initPresenter() {
        MainThread mainThread = MainThreadImpl.getInstance();
        UseCaseExecutor useCaseExecutor = UseCaseExecutorImpl.getInstance();
        UseCaseHandler useCaseHandler = new UseCaseHandlerImpl(mainThread, useCaseExecutor);
        TheMovieDbService movieDbService = NetworkServiceProvider.provideTheMovieDbService();
        MovieJsonConverter movieJsonConverter = new MovieJsonConverter();
        MovieDataSource networkSource = new MovieNetworkSource(movieDbService, movieJsonConverter);
        MovieRepository movieRepository = new MovieRepositoryImpl(networkSource);
        GetMoviesUseCase getMoviesUseCase = new GetMoviesUseCase(movieRepository);
        presenter = new MoviesPresenter(MoviesActivity.this, useCaseHandler, getMoviesUseCase);
        presenter.loadMovies(sortOrder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filmes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_sort:
                showSortOrderPopup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortOrderPopup() {
        View menuItemView = findViewById(R.id.menu_sort);

        PopupMenu popupMenu = new PopupMenu(MoviesActivity.this, menuItemView);
        popupMenu.getMenuInflater().inflate(R.menu.menu_sort_order, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_sort_popularity:
                        sortOrder = popularityOrder;
                        break;
                    case R.id.menu_sort_vote_average:
                        sortOrder = voteAverageOrder;
                        break;
                }
                presenter.loadMovies(sortOrder);
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public void showMovieDetail(int movieId) {
        Intent intent = new Intent(MoviesActivity.this, MovieActivity.class);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void showErrorLoadingMovies() {
        // TODO implementar
    }

    @Override
    public void showRefreshIndicator(boolean showRefreshIndicator) {
        refreshLayout.setRefreshing(showRefreshIndicator);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        moviesAdapter.updateData(movies);
    }
}
