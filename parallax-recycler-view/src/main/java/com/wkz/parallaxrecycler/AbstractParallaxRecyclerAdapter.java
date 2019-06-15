package com.wkz.parallaxrecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wkz
 * @date 2019/6/15 17:21
 */
public abstract class AbstractParallaxRecyclerAdapter<T> extends RecyclerView.Adapter<AbstractParallaxRecyclerAdapter.ParallaxViewHolder> {

    protected Context mContext;
    protected List<T> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public AbstractParallaxRecyclerAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas == null ? new ArrayList<>() : mDatas;
    }

    @NonNull
    @Override
    public ParallaxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ParallaxViewHolder(LayoutInflater.from(mContext).inflate(getLayoutId(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParallaxViewHolder holder, int position) {
        // set data
        setData(holder, mDatas.get(position), position);

        // calling this method is very important!
        holder.getParallaxImageView().reset();

        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 返回RecyclerView的item布局资源id
     *
     * @return 布局资源id
     */
    public abstract int getLayoutId();

    /**
     * 设置数据
     *
     * @param holder   ParallaxViewHolder
     * @param data     数据
     * @param position 下标
     */
    public abstract void setData(ParallaxViewHolder holder, T data, int position);

    public AbstractParallaxRecyclerAdapter<T> setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        return this;
    }

    public static class ParallaxViewHolder extends AbstractParallaxViewHolder {

        public ParallaxViewHolder(View itemView) {
            super(itemView);
        }

        public View getView(@IdRes int id) {
            return itemView.findViewById(id);
        }

        @Override
        public ParallaxImageView getParallaxImageView() {
            return itemView.findViewById(R.id.ivParallaxImage);
        }
    }
}
