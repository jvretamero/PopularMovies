package br.com.joaoretamero.popularmovies.movies;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.domain.local.AppSettings;
import br.com.joaoretamero.popularmovies.domain.local.Movie;
import br.com.joaoretamero.popularmovies.movie.MovieActivity;
import br.com.joaoretamero.popularmovies.util.DefaultTouchListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends AppCompatActivity implements MoviesView {

    private static final String TAG = MoviesActivity.class.getSimpleName();

    @BindView(R.id.movies_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.movies_list)
    RecyclerView moviesList;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh(AppSettings.get().sortOrder);
            }
        });
    }

    private void initList() {
        moviesList.setLayoutManager(new GridLayoutManager(MoviesActivity.this, 2));
        moviesList.setItemAnimator(new DefaultItemAnimator());
        moviesList.setAdapter(moviesAdapter);
        moviesList.addOnItemTouchListener(new DefaultTouchListener(MoviesActivity.this, new DefaultTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int posicao) {
                Movie movie = moviesAdapter.getItem(posicao);
                presenter.onItemClick(movie.movieId);
            }
        }));
    }

    private void initPresenter() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        presenter = new MoviesPresenter(MoviesActivity.this, connectivityManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.start(AppSettings.get().sortOrder);
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
                presenter.onSortMenuClick();
                return true;
            case R.id.menu_configurations:
                presenter.onConfigurationMenuClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMovieDetail(int movieId) {
        Intent intent = new Intent(MoviesActivity.this, MovieActivity.class);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void showSortingOptions() {
        // TODO implementar
    }

    @Override
    public void showConfigurationScreen() {
        // TODO implementar
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
