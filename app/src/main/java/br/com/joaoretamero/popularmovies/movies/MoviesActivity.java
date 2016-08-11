package br.com.joaoretamero.popularmovies.movies;

import android.content.Intent;
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

import com.activeandroid.ActiveAndroid;

import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.model.Genre;
import br.com.joaoretamero.popularmovies.model.Movie;
import br.com.joaoretamero.popularmovies.model.MovieGenre;
import br.com.joaoretamero.popularmovies.model.MovieProductionCompany;
import br.com.joaoretamero.popularmovies.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.model.Video;
import br.com.joaoretamero.popularmovies.movie.MovieActivity;
import br.com.joaoretamero.popularmovies.util.DefaultTouchListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends AppCompatActivity implements MoviesView {

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

        generateFakeData();

        initToolbar();
        initAdapter();
        initRefreshLayout();
        initList();
        initPresenter();
    }

    // TODO remover
    private void generateFakeData() {
        ActiveAndroid.beginTransaction();
        try {
            Genre genre1 = new Genre();
            genre1.name = "Genre 1";
            genre1.save();

            Genre genre2 = new Genre();
            genre2.name = "Genre 2";
            genre2.save();

            ProductionCompany productionCompany1 = new ProductionCompany();
            productionCompany1.name = "Production Company 1";
            productionCompany1.save();

            ProductionCompany productionCompany2 = new ProductionCompany();
            productionCompany2.name = "Production Company 2";
            productionCompany2.save();

            Movie movie;

            movie = new Movie();
            movie.movieId = 123;
            movie.title = "Android forever";
            movie.overview = "overview";
            movie.voteAverage = 8f;
            movie.save();

            saveFakeMovieGenre(movie, genre1);
            saveFakeMovieGenre(movie, genre2);
            saveFakeMovieProductionCompany(movie, productionCompany1);
            saveFakeMovieProductionCompany(movie, productionCompany2);
            saveFakeVideo(movie);
            saveFakeVideo(movie);

            movie = new Movie();
            movie.movieId = 456;
            movie.title = "iOS forever";
            movie.overview = "overview";
            movie.voteAverage = 7.5f;
            movie.save();

            saveFakeMovieGenre(movie, genre2);
            saveFakeMovieProductionCompany(movie, productionCompany1);

            movie = new Movie();
            movie.movieId = 789;
            movie.title = "WinPhone forever";
            movie.overview = "overview";
            movie.voteAverage = 4.5f;
            movie.save();

            saveFakeMovieGenre(movie, genre1);
            saveFakeMovieProductionCompany(movie, productionCompany2);
            saveFakeVideo(movie);

            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    private void saveFakeMovieGenre(Movie movie, Genre genre) {
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.movie = movie;
        movieGenre.genre = genre;
        movieGenre.save();
    }

    private void saveFakeMovieProductionCompany(Movie movie, ProductionCompany productionCompany) {
        MovieProductionCompany movieProductionCompany = new MovieProductionCompany();
        movieProductionCompany.movie = movie;
        movieProductionCompany.productionCompany = productionCompany;
        movieProductionCompany.save();
    }

    private void saveFakeVideo(Movie movie) {
        Video video = new Video();
        video.movie = movie;
        video.name = "Video name";
        video.youtubeId = "abc";
        video.save();
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
        moviesList.addOnItemTouchListener(new DefaultTouchListener(MoviesActivity.this, new DefaultTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int posicao) {
                presenter.onItemClick(moviesAdapter.getItem(posicao).movieId);
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
    public void showMovieDetail(int movieId) {
        Intent intent = new Intent(MoviesActivity.this, MovieActivity.class);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
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
    public void showMovies(List<Movie> movies) {
        moviesAdapter.updateData(movies);
    }
}
