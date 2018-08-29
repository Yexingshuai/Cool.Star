package com.example.myapp.myapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.ViewGroupHierarchyChildViewRemoveEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  yexing on 2018/5/14.
 */

public abstract class BasicAdapter<T> extends RecyclerView.Adapter {
    private List<T> mDatas = new ArrayList<>();
    private OnItemClickListener listener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final T t = mDatas.get(position);
        onBind(t, position, holder);
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view, position, t);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mDatas.size() > 0) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    public void setDatas(List<T> mData) {
        this.mDatas = mData;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> mData) {
        mDatas.addAll(mData);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mDatas;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener<T> {
        void onClick(View view, int position, T t);
    }

    /**
     * 创建ViewHolder
     *
     * @return
     */
    protected abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent);

    /**
     * 绑定数据
     *
     * @param t
     * @param position
     * @param holder
     */
    protected abstract void onBind(T t, int position, RecyclerView.ViewHolder holder);
}
