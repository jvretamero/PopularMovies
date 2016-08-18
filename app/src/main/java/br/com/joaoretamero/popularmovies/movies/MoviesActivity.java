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
import com.activeandroid.query.Delete;

import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.domain.local.AppSettings;
import br.com.joaoretamero.popularmovies.domain.local.Genre;
import br.com.joaoretamero.popularmovies.domain.local.Movie;
import br.com.joaoretamero.popularmovies.domain.local.MovieGenre;
import br.com.joaoretamero.popularmovies.domain.local.MovieProductionCompany;
import br.com.joaoretamero.popularmovies.domain.local.ProductionCompany;
import br.com.joaoretamero.popularmovies.domain.local.Video;
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
            new Delete().from(MovieGenre.class).execute();
            new Delete().from(MovieProductionCompany.class).execute();
            new Delete().from(Video.class).execute();
            new Delete().from(Movie.class).execute();
            new Delete().from(Genre.class).execute();
            new Delete().from(ProductionCompany.class).execute();

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
            movie.durationInMinutes = 150;
            movie.voteAverage = 5f;
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
            movie.voteAverage = 4f;
            movie.durationInMinutes = 120;
            movie.save();

            saveFakeMovieGenre(movie, genre2);
            saveFakeMovieProductionCompany(movie, productionCompany1);

            movie = new Movie();
            movie.movieId = 789;
            movie.title = "WinPhone forever";
            movie.overview = "overview";
            movie.voteAverage = 3f;
            movie.durationInMinutes = 130;
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
        presenter = new MoviesPresenter(MoviesActivity.this);
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
    public void showRefreshIndicator(boolean showRefreshIndicator) {
        refreshLayout.setRefreshing(showRefreshIndicator);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        moviesAdapter.updateData(movies);
    }
}
