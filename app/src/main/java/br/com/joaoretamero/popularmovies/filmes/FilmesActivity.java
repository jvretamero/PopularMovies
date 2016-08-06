package br.com.joaoretamero.popularmovies.filmes;

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

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.modelo.Filme;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmesActivity extends AppCompatActivity implements FilmesView {

    private FilmesPresenter presenter;

    private FilmesAdapter filmesAdapter;

    @BindView(R.id.filmes_swipe)
    private SwipeRefreshLayout refreshLayout;

    @BindView(R.id.filmes_lista)
    private RecyclerView listaFilmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        filmesAdapter = new FilmesAdapter(FilmesActivity.this);
        filmesAdapter.setListaFilmes(criaListaFilmes()); //TODO remover mais tarde

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });

        listaFilmes.setLayoutManager(new GridLayoutManager(FilmesActivity.this, 2));
        listaFilmes.setItemAnimator(new DefaultItemAnimator());
        listaFilmes.setAdapter(filmesAdapter);
        listaFilmes.addOnItemTouchListener(new FilmesAdapter.TouchListener(FilmesActivity.this, new FilmesAdapter.ClickListener() {
            @Override
            public void onClick(View view, int posicao) {
                presenter.onItemClick();
            }
        }));

        presenter = new FilmesPresenter(FilmesActivity.this);
        presenter.inicia();
    }

    private List<Filme> criaListaFilmes() {
        List<Filme> lista = new ArrayList<Filme>();
        Filme filme = null;

        filme = new Filme();
        filme.id = 11;
        filme.titulo = "Filme 1";
        lista.add(filme);

        filme = new Filme();
        filme.id = 12;
        filme.titulo = "Filme 2";
        lista.add(filme);

        filme = new Filme();
        filme.id = 13;
        filme.titulo = "Filme 3";
        lista.add(filme);

        return lista;
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
            case R.id.menu_ordem:
                presenter.onMenuOrdemClick();
                return true;
            case R.id.menu_configuracoes:
                presenter.onMenuConfiguracoesClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void exibeFilmeDetalhe() {
    }

    @Override
    public void exibeOpcoesOrdem() {
    }

    @Override
    public void exibeConfiguracoes() {
    }

    @Override
    public void setShowRefresh(boolean showRefresh) {
        refreshLayout.setRefreshing(showRefresh);
    }
}
