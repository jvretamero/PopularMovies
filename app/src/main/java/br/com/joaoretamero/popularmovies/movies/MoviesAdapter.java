package br.com.joaoretamero.popularmovies.movies;

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
import br.com.joaoretamero.popularmovies.modelo.Movie;
import io.realm.RealmRecyclerViewAdapter;

public class MoviesAdapter extends RealmRecyclerViewAdapter<Movie, MoviesAdapter.ViewHolder> {

    private Context context;

    public MoviesAdapter(@NonNull Context context) {
        super(context, null, false);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = getItem(position);

        holder.posterImage.setImageResource(R.mipmap.ic_launcher);
        holder.title.setText(movie.title);
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
        public ImageView posterImage;
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            bindViews();
        }

        private void bindViews() {
            posterImage = (ImageView) itemView.findViewById(R.id.movie_item_poster);
            title = (TextView) itemView.findViewById(R.id.movie_item_title);
        }
    }
}
