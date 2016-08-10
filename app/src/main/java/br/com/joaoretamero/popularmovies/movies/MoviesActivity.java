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

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.model.Genre;
import br.com.joaoretamero.popularmovies.model.Movie;
import br.com.joaoretamero.popularmovies.model.ProductionCompany;
import br.com.joaoretamero.popularmovies.model.Video;
import br.com.joaoretamero.popularmovies.movie.MovieActivity;
import io.realm.Realm;
import io.realm.RealmResults;

public class MoviesActivity extends AppCompatActivity implements MoviesView {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView moviesList;
    private Toolbar toolbar;
    private MoviesPresenter presenter;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        generateFakeData();

        initToolbar();
        initAdapter();
        initRefreshLayout();
        initList();
        initPresenter();
    }

    // TODO remover
    private void generateFakeData() {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();

            realm.delete(Movie.class);
            realm.delete(ProductionCompany.class);
            realm.delete(Genre.class);
            realm.delete(Video.class);

            Genre genre1 = realm.createObject(Genre.class);
            genre1.id = 100;
            genre1.name = "Genre 1";
            realm.copyToRealmOrUpdate(genre1);

            Genre genre2 = realm.createObject(Genre.class);
            genre2.id = 200;
            genre2.name = "Genre 2";
            realm.copyToRealmOrUpdate(genre2);

            ProductionCompany productionCompany1 = realm.createObject(ProductionCompany.class);
            productionCompany1.id = 100;
            productionCompany1.name = "Production Company 1";
            realm.copyToRealmOrUpdate(productionCompany1);

            ProductionCompany productionCompany2 = realm.createObject(ProductionCompany.class);
            productionCompany2.id = 200;
            productionCompany2.name = "Production Company 2";
            realm.copyToRealmOrUpdate(productionCompany2);

            Movie movie;
            Video video;

            movie = realm.createObject(Movie.class);
            movie.id = 123;
            movie.title = "Android forever";
            movie.overview = "overview";
            movie.voteAverage = 8f;
            movie.genres.add(genre1);
            movie.genres.add(genre2);
            movie.productionCompanies.add(productionCompany1);
            movie.productionCompanies.add(productionCompany2);
            realm.copyToRealmOrUpdate(movie);

            video = realm.createObject(Video.class);
            video.id = "100";
            video.name = "Video 1";
            video.youtubeId = "abcdefg";
            video.movie = movie;
            realm.copyToRealmOrUpdate(video);

            video = realm.createObject(Video.class);
            video.id = "200";
            video.name = "Video 2";
            video.youtubeId = "abcdefg";
            video.movie = movie;
            realm.copyToRealmOrUpdate(video);

            movie = realm.createObject(Movie.class);
            movie.id = 456;
            movie.title = "iOS forever";
            movie.overview = "overview";
            movie.voteAverage = 7.5f;
            movie.genres.add(genre2);
            movie.productionCompanies.add(productionCompany2);
            realm.copyToRealmOrUpdate(movie);

            movie = realm.createObject(Movie.class);
            movie.id = 789;
            movie.title = "WinPhone forever";
            movie.overview = "overview";
            movie.voteAverage = 4.5f;
            movie.productionCompanies.add(productionCompany1);
            realm.copyToRealmOrUpdate(movie);

            video = realm.createObject(Video.class);
            video.id = "300";
            video.name = "Video 1";
            video.youtubeId = "abcdefg";
            video.movie = movie;
            realm.copyToRealmOrUpdate(video);

            realm.commitTransaction();
        } finally {
            realm.close();
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initAdapter() {
        moviesAdapter = new MoviesAdapter(MoviesActivity.this);
    }

    private void initRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.movies_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });
    }

    private void initList() {
        moviesList = (RecyclerView) findViewById(R.id.movies_list);
        moviesList.setLayoutManager(new GridLayoutManager(MoviesActivity.this, 2));
        moviesList.setItemAnimator(new DefaultItemAnimator());
        moviesList.setAdapter(moviesAdapter);
        moviesList.addOnItemTouchListener(new MoviesAdapter.TouchListener(MoviesActivity.this, new MoviesAdapter.ClickListener() {
            @Override
            public void onClick(View view, int posicao) {
                presenter.onItemClick(moviesAdapter.getItem(posicao).id);
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
    public void showMovies(RealmResults<Movie> movies) {
        moviesAdapter.updateData(movies);
    }
}
