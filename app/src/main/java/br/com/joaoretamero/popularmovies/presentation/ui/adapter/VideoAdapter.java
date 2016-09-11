package br.com.joaoretamero.popularmovies.presentation.ui.adapter;

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
import br.com.joaoretamero.popularmovies.infraestructure.network.provider.ImageUrlProvider;
import br.com.joaoretamero.popularmovies.infraestructure.network.provider.PicassoProvider;
import br.com.joaoretamero.popularmovies.infraestructure.storage.model.Video;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends BaseAdapter<Video, VideoAdapter.ViewHolder> {

    private Context context;

    public VideoAdapter(@NonNull Context context) {
        super();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video video = getItem(position);

        // TODO criar drawable de erro
        PicassoProvider.provide(context)
                .load(ImageUrlProvider.provideYoutubeUrl(video.youtubeId))
                .fit()
                .centerCrop()
                .into(holder.image);

        holder.title.setText(video.name);
        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO implementar clique no play
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.video_item_image)
        public ImageView image;

        @BindView(R.id.video_item_title)
        public TextView title;

        @BindView(R.id.video_item_play_button)
        public ImageButton playButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
