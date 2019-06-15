package com.wkz.parallaxrecycler;

/**
 * item点击监听
 *
 * @author wkz
 * @date 2019/6/15 18:51
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
