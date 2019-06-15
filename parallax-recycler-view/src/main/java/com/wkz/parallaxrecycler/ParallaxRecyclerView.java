package com.wkz.parallaxrecycler;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/6/15 11:42
 */
public class ParallaxRecyclerView extends RecyclerView {

    private OnScrollListener mScrollListener;
    private OnScrollListener mDefaultListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (mScrollListener != null) {
                mScrollListener.onScrollStateChanged(recyclerView, newState);
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                if (viewHolder instanceof AbstractParallaxViewHolder) {
                    ((AbstractParallaxViewHolder) viewHolder).animateImage();
                }
            }

            if (mScrollListener != null) {
                mScrollListener.onScrolled(recyclerView, dx, dy);
            }
        }
    };


    public ParallaxRecyclerView(Context context) {
        this(context, null);
    }

    public ParallaxRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addOnScrollListener(mDefaultListener);
    }

    /**
     * @deprecated Use {@link #addOnScrollListener(OnScrollListener)} and
     * {@link #removeOnScrollListener(OnScrollListener)}
     */
    @Deprecated
    @Override
    public void setOnScrollListener(@Nullable OnScrollListener listener) {
        super.setOnScrollListener(listener);
        this.mScrollListener = listener;
    }

    @Override
    public void addOnScrollListener(@NonNull OnScrollListener listener) {
        super.addOnScrollListener(listener);
    }
}
