package br.com.joaoretamero.popularmovies.presentation.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.domain.model.Movie;
import br.com.joaoretamero.popularmovies.infrastructure.network.provider.PicassoProvider;
import br.com.joaoretamero.popularmovies.infrastructure.network.provider.UrlProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends BaseAdapter<Movie, MoviesAdapter.ViewHolder> {

    public static final String TAG = MoviesAdapter.class.getSimpleName();
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
    protected void onBindItemHolder(ViewHolder holder, final Movie item) {
        PicassoProvider.provide(context)
                .load(UrlProvider.providePosterUrl(item.getPoster()))
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .into(holder.posterImage);

        holder.title.setText(item.getTitle());
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
