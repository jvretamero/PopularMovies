package br.com.joaoretamero.popularmovies.presentation.ui.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BackdropImageView extends ImageView {

    public BackdropImageView(Context context) {
        super(context);
    }

    public BackdropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackdropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BackdropImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float ratio = 0.5f;
        int height = (int) (widthMeasureSpec * ratio);
        super.onMeasure(widthMeasureSpec, height);
    }
}
