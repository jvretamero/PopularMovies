package br.com.joaoretamero.popularmovies.movie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.model.Video;
import io.realm.RealmRecyclerViewAdapter;

public class VideoAdapter extends RealmRecyclerViewAdapter<Video, VideoAdapter.ViewHolder> {
    private Context context;

    public VideoAdapter(@NonNull Context context) {
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
        Video video = getItem(position);
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
