package br.com.joaoretamero.popularmovies.movies;

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

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.modelo.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class MoviesActivity extends AppCompatActivity implements MoviesView {

    private MoviesPresenter presenter;

    private MoviesAdapter moviesAdapter;

    @BindView(R.id.movies_refresh_layout)
    private SwipeRefreshLayout refreshLayout;

    @BindView(R.id.movies_list)
    private RecyclerView moviesList;

    @BindView(R.id.toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);

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
                presenter.onRefresh();
            }
        });
    }

    private void initList() {
        moviesList.setLayoutManager(new GridLayoutManager(MoviesActivity.this, 2));
        moviesList.setItemAnimator(new DefaultItemAnimator());
        moviesList.setAdapter(moviesAdapter);
        moviesList.addOnItemTouchListener(new MoviesAdapter.TouchListener(MoviesActivity.this, new MoviesAdapter.ClickListener() {
            @Override
            public void onClick(View view, int posicao) {
                presenter.onItemClick();
            }
        }));
    }

    private void initPresenter() {
        presenter = new MoviesPresenter(MoviesActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showMovieDetail() {
    }

    @Override
    public void showSortingOptions() {
    }

    @Override
    public void showConfigurationScreen() {
    }

    @Override
    public void showRefreshIndicator(boolean showRefreshIndicator) {
        refreshLayout.setRefreshing(showRefreshIndicator);
    }

    @Override
    public void showMovies(RealmResults<Movie> movies) {
        moviesAdapter.updateData(movies);
    }
}
