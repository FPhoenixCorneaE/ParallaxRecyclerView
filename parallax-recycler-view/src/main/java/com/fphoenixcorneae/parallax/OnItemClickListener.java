package com.fphoenixcorneae.parallax;

/**
 * item点击监听
 *
 * @date 2019-06-15 18:51
 */
public interface OnItemClickListener {
    /**
     * item点击监听
     *
     * @param holder   ParallaxViewHolder
     * @param position 下标
     */
    void onItemClick(AbstractParallaxRecyclerAdapter.ParallaxViewHolder holder, int position);
}
