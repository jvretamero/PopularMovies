package br.com.joaoretamero.popularmovies.filmes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.modelo.ItemFilme;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.ViewHolder> {

    private Context context;
    private List<ItemFilme> listaFilmes;

    public FilmesAdapter(Context context) {
        this.context = context;
    }

    public void setListaFilmes(List<ItemFilme> listaFilmes) {
        this.listaFilmes = listaFilmes;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filmes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemFilme itemFilme = listaFilmes.get(position);

        holder.imagem.setImageResource(R.mipmap.ic_launcher);
        holder.titulo.setText(itemFilme.getTitulo());
    }

    @Override
    public int getItemCount() {
        return (listaFilmes == null) ? 0 : listaFilmes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagem;
        public TextView titulo;

        public ViewHolder(View itemView) {
            super(itemView);

            this.imagem = (ImageView) itemView.findViewById(R.id.item_filmes_imagem);
            this.titulo = (TextView) itemView.findViewById(R.id.item_filmes_titulo);
        }
    }
}
