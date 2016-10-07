package br.com.joaoretamero.popularmovies.presentation.ui.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class PosterImageView extends AppCompatImageView {
    
    public PosterImageView(Context context) {
        super(context);
    }

    public PosterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PosterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float ratio = 1.5f;
        int height = (int) (widthMeasureSpec * ratio);
        super.onMeasure(widthMeasureSpec, height);
    }
}
