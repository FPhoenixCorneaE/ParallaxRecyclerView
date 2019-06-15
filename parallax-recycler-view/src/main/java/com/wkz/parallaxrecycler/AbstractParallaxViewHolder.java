package com.wkz.parallaxrecycler;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Administrator
 * @date 2019/6/15 11:46
 */
public abstract class AbstractParallaxViewHolder extends RecyclerView.ViewHolder implements OnParallaxImageListener {

    /**
     * 返回ParallaxImageView
     *
     * @return ParallaxImageView
     */
    public abstract ParallaxImageView getParallaxImageView();

    public AbstractParallaxViewHolder(View itemView) {
        super(itemView);
        getParallaxImageView().setListener(this);
    }

    @Override
    public int[] requireValuesForTranslate() {
        if (itemView.getParent() == null) {
            // Not added to parent yet!
            return null;
        } else {
            int[] itemPosition = new int[2];
            itemView.getLocationOnScreen(itemPosition);

            int[] recyclerPosition = new int[2];
            ((RecyclerView) itemView.getParent()).getLocationOnScreen(recyclerPosition);

            return new int[]{
                    itemPosition[1],
                    ((RecyclerView) itemView.getParent()).getMeasuredHeight(),
                    recyclerPosition[1]
            };
        }
    }

    public void animateImage() {
        if (getParallaxImageView() != null) {
            getParallaxImageView().doTranslate();
        }
    }
}
