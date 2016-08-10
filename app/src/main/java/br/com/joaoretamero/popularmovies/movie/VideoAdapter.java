package br.com.joaoretamero.popularmovies.movie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

        // TODO implementar carregamento da imagem
        holder.title.setText(video.name);
        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO implementar clique no play
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public ImageButton playButton;

        public ViewHolder(View itemView) {
            super(itemView);
            bindViews();
        }

        private void bindViews() {
            image = (ImageView) itemView.findViewById(R.id.video_item_image);
            title = (TextView) itemView.findViewById(R.id.video_item_title);
            playButton = (ImageButton) itemView.findViewById(R.id.video_item_play_button);
        }
    }
}
