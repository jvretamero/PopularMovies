package br.com.joaoretamero.popularmovies.presentation.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import br.com.joaoretamero.popularmovies.R;
import br.com.joaoretamero.popularmovies.domain.model.Video;
import br.com.joaoretamero.popularmovies.infraestructure.network.provider.ImageUrlProvider;
import br.com.joaoretamero.popularmovies.infraestructure.network.provider.PicassoProvider;
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
    protected void onBindItemHolder(VideoAdapter.ViewHolder holder, Video item) {
        PicassoProvider.provide(context)
                .load(ImageUrlProvider.provideYoutubeUrl(item.getYoutubeId()))
                .into(holder.image);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_item_image)
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
