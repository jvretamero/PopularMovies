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

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.modelo.ItemFilme;

public class FilmesActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView listaFilmes;
    private FilmesAdapter filmesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FilmesActivity.this, 2);

        filmesAdapter = new FilmesAdapter(FilmesActivity.this);
        //TODO remover mais tarde
        filmesAdapter.setListaFilmes(criaListaFilmes());

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.filmes_swipe);

        listaFilmes = (RecyclerView) findViewById(R.id.filmes_lista);
        listaFilmes.setLayoutManager(layoutManager);
        listaFilmes.setItemAnimator(new DefaultItemAnimator());
        listaFilmes.setAdapter(filmesAdapter);
        //TODO implementar o click
        //listaFilmes.addOnItemTouchListener(null);
    }

    //TODO remover mais tarde
    private List<ItemFilme> criaListaFilmes() {
        List<ItemFilme> lista = new ArrayList<ItemFilme>();
        lista.add(new ItemFilme(100, "Filme 1"));
        lista.add(new ItemFilme(200, "Filme 2"));
        lista.add(new ItemFilme(300, "Filme 3"));
        lista.add(new ItemFilme(400, "Filme 4"));
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
                return true;
            case R.id.menu_configuracoes:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
