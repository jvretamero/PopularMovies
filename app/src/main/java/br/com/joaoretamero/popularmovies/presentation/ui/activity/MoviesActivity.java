package br.com.joaoretamero.popularmovies.presentation.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import br.com.joaoretamero.popularmovies.Injection;
import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.domain.threading.UseCaseHandler;
import br.com.joaoretamero.popularmovies.domain.usecase.GetMoviesUseCase;
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

    @BindString(R.string.popularity_order_key)
    String popularityOrder;

    @BindString(R.string.vote_average_order_key)
    String voteAverageOrder;

    private String currentSortOrder;
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
        initSortOrder();
        initPresenter();
    }

    private void initSortOrder() {
        String orderPreferenceKey = getString(R.string.order_preference_key);
        String orderPreferenceDefaultValue = getString(R.string.order_preference_default_value);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        currentSortOrder = sharedPreferences.getString(orderPreferenceKey, orderPreferenceDefaultValue);
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
                presenter.onRefresh(currentSortOrder);
            }
        });
    }

    private void initList() {
        moviesList.setLayoutManager(new GridLayoutManager(MoviesActivity.this, 2));
        moviesList.setItemAnimator(new DefaultItemAnimator());
        moviesList.setAdapter(moviesAdapter);
    }

    private void initPresenter() {
        UseCaseHandler useCaseHandler = Injection.provideUseCaseHandler();
        GetMoviesUseCase getMoviesUseCase = Injection.provideGetMoviesUseCase();

        presenter = new MoviesPresenter(MoviesActivity.this, useCaseHandler, getMoviesUseCase);
        presenter.loadMovies(currentSortOrder);
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
            case R.id.menu_settings:
                showSettingsScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortOrderPopup() {
        View menuItemView = findViewById(R.id.menu_sort);

        PopupMenu popupMenu = new PopupMenu(MoviesActivity.this, menuItemView);
        popupMenu.getMenuInflater().inflate(R.menu.menu_sort_order, popupMenu.getMenu());

        Menu menu = popupMenu.getMenu();
        checkCurrentSortOrderOf(menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_sort_popularity:
                        currentSortOrder = popularityOrder;
                        break;
                    case R.id.menu_sort_vote_average:
                        currentSortOrder = voteAverageOrder;
                        break;
                }
                presenter.loadMovies(currentSortOrder);
                return true;
            }
        });
        popupMenu.show();
    }

    private void checkCurrentSortOrderOf(Menu sortOrderMenu) {
        MenuItem menuItem;
        int menuSize = sortOrderMenu.size();

        for (int i = 0; i < menuSize; i++) {
            menuItem = sortOrderMenu.getItem(i);
            switch (menuItem.getItemId()) {
                case R.id.menu_sort_popularity:
                    menuItem.setChecked(currentSortOrder.equals(popularityOrder));
                    break;
                case R.id.menu_sort_vote_average:
                    menuItem.setChecked(currentSortOrder.equals(voteAverageOrder));
                    break;
            }
        }
    }

    private void showSettingsScreen() {
        Intent settingsIntent = new Intent(MoviesActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);
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
