package br.com.joaoretamero.popularmovies.filmes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.modelo.Filme;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;

public class FilmesAdapter extends RealmRecyclerViewAdapter<Filme, FilmesAdapter.ViewHolder> {

    private Context context;

    public FilmesAdapter(@NonNull Context context) {
        super(context, null, false);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filmes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Filme filme = getItem(position);

        holder.imagem.setImageResource(R.mipmap.ic_launcher);
        holder.titulo.setText(filme.titulo);
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
