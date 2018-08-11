package com.example.myapp.myapp.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {

    private int mLayoutResourceId;
    private List<T> mList;

    private OnItemClickListener mOnItemClickListener;

    /**
     * Item内的View的点击
     */
    private int[] mClickViewIds;
    private View.OnClickListener mOnClickListener;

    public RecyclerAdapter(@LayoutRes int mLayoutResourceId, List<T> mList) {
        this.mLayoutResourceId = mLayoutResourceId;
        this.mList = mList;
    }

    public RecyclerAdapter(@LayoutRes int mLayoutResourceId, List<T> mList, OnItemClickListener onItemClickListener) {
        this.mLayoutResourceId = mLayoutResourceId;
        this.mList = mList;
        mOnItemClickListener = onItemClickListener;
    }


    public RecyclerAdapter(@LayoutRes int mLayoutResourceId, List<T> mList, int[] clickViewIds, View.OnClickListener onClickListener) {
        this.mLayoutResourceId = mLayoutResourceId;
        this.mList = mList;
        mClickViewIds = clickViewIds;
        mOnClickListener = onClickListener;
    }

    public RecyclerAdapter(@LayoutRes int mLayoutResourceId, List<T> mList, OnItemClickListener onItemClickListener, int[] clickViewIds, View.OnClickListener onClickListener) {
        this.mLayoutResourceId = mLayoutResourceId;
        this.mList = mList;
        mOnItemClickListener = onItemClickListener;
        mClickViewIds = clickViewIds;
        mOnClickListener = onClickListener;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutResourceId, parent, false);
        RecyclerHolder holder = new RecyclerHolder(view, mOnItemClickListener, mClickViewIds, mOnClickListener);
        return holder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        onBindHolder(holder, mList.get(position), position);
    }

    /**
     * onBindHolder
     *
     * @param holder
     * @param model
     * @param position
     */
    protected abstract void onBindHolder(RecyclerHolder holder, T model, int position);

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
