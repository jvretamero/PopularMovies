package br.com.joaoretamero.popularmovies.filmes;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.modelo.Filme;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.ViewHolder> {

    private Context context;
    private List<Filme> listaFilmes;

    public FilmesAdapter(Context context) {
        this.context = context;
    }

    public void setListaFilmes(List<Filme> listaFilmes) {
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
        Filme filme = listaFilmes.get(position);

        holder.imagem.setImageResource(R.mipmap.ic_launcher);
        holder.titulo.setText(filme.getTitulo());
    }

    @Override
    public int getItemCount() {
        return (listaFilmes == null) ? 0 : listaFilmes.size();
    }

    public interface ClickListener {
        void onClick(View view, int posicao);
    }

    public static class TouchListener extends RecyclerView.SimpleOnItemTouchListener {

        private static final String TAG = TouchListener.class.getSimpleName();

        private ClickListener clickListener;
        private GestureDetectorCompat gestureDetector;

        public TouchListener(Context context, ClickListener clickListener) {
            this.clickListener = clickListener;
            this.gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            if (gestureDetector.onTouchEvent(e)) {
                View view = rv.findChildViewUnder(e.getX(), e.getY());

                if (view != null && clickListener != null) {
                    clickListener.onClick(view, rv.getChildAdapterPosition(view));
                    return true;
                }
            }

            return super.onInterceptTouchEvent(rv, e);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_filmes_imagem)
        public ImageView imagem;

        @BindView(R.id.item_filmes_titulo)
        public TextView titulo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
