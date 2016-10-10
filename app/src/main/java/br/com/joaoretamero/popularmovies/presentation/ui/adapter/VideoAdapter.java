package br.com.joaoretamero.popularmovies.presentation.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.domain.model.Video;
import br.com.joaoretamero.popularmovies.infrastructure.network.provider.PicassoProvider;
import br.com.joaoretamero.popularmovies.infrastructure.network.provider.UrlProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends BaseAdapter<Video, VideoAdapter.ViewHolder> {

    public static final String TAG = VideoAdapter.class.getSimpleName();
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
    protected void onBindItemHolder(final VideoAdapter.ViewHolder holder, Video item) {
        PicassoProvider.provide(context)
                .load(UrlProvider.provideYoutubeUrl(item.getYoutubeId()))
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.playButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_item_image)
        public AppCompatImageView image;

        @BindView(R.id.video_item_play_button)
        public AppCompatImageView playButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
