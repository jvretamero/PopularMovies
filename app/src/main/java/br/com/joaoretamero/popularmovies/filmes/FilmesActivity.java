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

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.modelo.Filme;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class FilmesActivity extends AppCompatActivity implements FilmesView {

    private FilmesPresenter presenter;

    private FilmesAdapter filmesAdapter;

    @BindView(R.id.filmes_swipe)
    private SwipeRefreshLayout refreshLayout;

    @BindView(R.id.filmes_lista)
    private RecyclerView listaFilmes;

    @BindView(R.id.toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);

        ButterKnife.bind(this);

        configuraToolbar();
        configuraAdapter();
        configuraRefreshLayout();
        configuraLista();
        configuraPresenter();
    }

    private void configuraToolbar() {
        setSupportActionBar(toolbar);
    }

    private void configuraAdapter() {
        filmesAdapter = new FilmesAdapter(FilmesActivity.this);
    }

    private void configuraRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });
    }

    private void configuraLista() {
        listaFilmes.setLayoutManager(new GridLayoutManager(FilmesActivity.this, 2));
        listaFilmes.setItemAnimator(new DefaultItemAnimator());
        listaFilmes.setAdapter(filmesAdapter);
        listaFilmes.addOnItemTouchListener(new FilmesAdapter.TouchListener(FilmesActivity.this, new FilmesAdapter.ClickListener() {
            @Override
            public void onClick(View view, int posicao) {
                presenter.onItemClick();
            }
        }));
    }

    private void configuraPresenter() {
        presenter = new FilmesPresenter(FilmesActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.inicia();
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.finaliza();
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
    public void exibeIndicadorAtualizando(boolean exibeIndicador) {
        refreshLayout.setRefreshing(exibeIndicador);
    }

    @Override
    public void exibeFilmes(RealmResults<Filme> filmes) {
        filmesAdapter.updateData(filmes);
    }
}
