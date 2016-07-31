package br.com.joaoretamero.popularmovies.filmes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.joaoretamero.popularmovies.R;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.ViewHolder> {

    private List<Object> listaFilmes;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(null).inflate(R.layout.item_filmes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object object = listaFilmes.get(position);
    }

    @Override
    public int getItemCount() {
        return (listaFilmes == null) ? 0 : listaFilmes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
