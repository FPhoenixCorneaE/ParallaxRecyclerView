package com.fphoenixcorneae.demo;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.fphoenixcorneae.parallax.AbstractParallaxRecyclerAdapter;

import java.util.List;

public class ParallaxRecyclerAdapter extends AbstractParallaxRecyclerAdapter<String> {

    public ParallaxRecyclerAdapter(Context mContext, List<String> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    public int getLayoutId() {
        return R.layout.recycler_item_parallax_recycler;
    }

    @Override
    public void setData(AbstractParallaxRecyclerAdapter.ParallaxViewHolder holder, String data, int position) {
        Glide.with(holder.getParallaxImageView())
                .load(data)
                .skipMemoryCache(false)
                .into(holder.getParallaxImageView());
    }
}
