package br.com.joaoretamero.popularmovies.util;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class DefaultTouchListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = DefaultTouchListener.class.getSimpleName();

    private ClickListener clickListener;
    private GestureDetectorCompat gestureDetector;

    public DefaultTouchListener(Context context, ClickListener clickListener) {
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

    public interface ClickListener {
        void onClick(View view, int posicao);
    }
}
