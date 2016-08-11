package br.com.joaoretamero.popularmovies.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.model.Movie;
import br.com.joaoretamero.popularmovies.util.BaseAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends BaseAdapter<Movie, MoviesAdapter.ViewHolder> {

    private Context context;

    public MoviesAdapter(@NonNull Context context) {
        super();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_item_poster)
        public ImageView posterImage;

        @BindView(R.id.movie_item_title)
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
