package com.example.myapp.myapp.ui.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class NiceRecyclerAdapter<Data> extends RecyclerView.Adapter<NiceRecyclerAdapter.NiceRecyclerHolder<Data>>
        implements View.OnClickListener, AdapterCallback<Data> {


    private final List<Data> mList;
    private AdapterListener<Data> mListener;

    public static final int CLICK_TAG = 0;  //绑定tag ID

    public NiceRecyclerAdapter() {
        this(null);
    }


    public NiceRecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }


    public NiceRecyclerAdapter(List<Data> list, AdapterListener<Data> listener) {
        this.mList = list;
        mListener = listener;
    }


    /**
     * 布局类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mList.get(position));
    }


    //用layoutID 作为type
    protected abstract int getItemViewType(int pos, Data data);


    @NonNull
    @Override
    public NiceRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(viewType, viewGroup, false);

        NiceRecyclerHolder niceRecyclerHolder = createRecyclerViewHolder(view, viewType);

        //设置view的tag 是viewHolder
        view.setTag(CLICK_TAG, niceRecyclerHolder.itemView);

        //点击事件
        niceRecyclerHolder.itemView.setOnClickListener(this);

        //绑定callBack
        niceRecyclerHolder.callback = this;

        return niceRecyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NiceRecyclerHolder<Data> niceRecyclerHolder, int position) {

        Data data = mList.get(position);

        //触发holder 绑定方法
        niceRecyclerHolder.bind(data);

    }

    @Override
    public int getItemCount() {

        return mList != null && mList.size() > 0 ? mList.size() : 0;

    }


    /**
     * 生成ViewHolder
     *
     * @param view
     * @param viewType
     * @return
     */

    public abstract NiceRecyclerHolder createRecyclerViewHolder(View view, int viewType);

    @Override
    public void onClick(View v) {
        NiceRecyclerHolder<Data> holder = (NiceRecyclerHolder) v.getTag(CLICK_TAG);
        if (this.mListener != null) {
            int adapterPosition = holder.getAdapterPosition();
            //  mList.get(adapterPosition);
            mListener.onItemClick(holder, holder.data);
        }
    }


    /**
     * 设置适配器的监听
     *
     * @param adapterListener AdapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /**
     * 自定义监听器
     *
     * @param <Data> 范型
     */
    public interface AdapterListener<Data> {

        // 当Cell点击的时候触发
        void onItemClick(NiceRecyclerAdapter.NiceRecyclerHolder holder, Data data);

    }


    //----------------------------数据的增删改查---------------------------------------


    //获取整个list
    public List<Data> getItems() {
        return mList;
    }


    /**
     * 添加了一条数据
     *
     * @param data
     */
    public void add(Data data) {
        mList.add(data);
        notifyItemInserted(mList.size() - 1);
    }


    /**
     * 添加一组数据
     *
     * @param dataList
     */
    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mList.size();
            Collections.addAll(mList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }


    /**
     * 插入一段集合
     *
     * @param list
     */
    public void add(Collection<Data> list) {
        if (list != null && list.size() > 0) {
            int startPos = mList.size();
            mList.addAll(list);
            notifyItemRangeInserted(startPos, list.size());
        }
    }


    /**
     * 删除所有的数据
     */
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }


    /**
     * 删除某个位置的数据
     *
     * @param pos
     */
    public void remove(int pos) {
        mList.remove(pos);
        notifyItemRemoved(pos);
    }


    /**
     * 替换新的数据
     *
     * @param dataList
     */
    public void replace(Collection<Data> dataList) {
        mList.clear();
        if (dataList != null & dataList.size() > 0) {
            mList.addAll(dataList);
        }
        notifyDataSetChanged();
    }


    /**
     * 单条数据替换
     *
     * @param data
     * @param holder
     */
    @Override
    public void update(Data data, NiceRecyclerHolder<Data> holder) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition >= 0) {
            mList.remove(adapterPosition);
            mList.add(adapterPosition, data);
            notifyItemChanged(adapterPosition);
        }
    }

    //--------------------------end-----------------------------------------


    public static abstract class NiceRecyclerHolder<Data> extends RecyclerView.ViewHolder {

        public Data data;

        private AdapterCallback<Data> callback;


        public NiceRecyclerHolder(@NonNull View itemView) {
            super(itemView);
        }


        //绑定数据
        void bind(Data data) {
            this.data = data;
            onBind(data);
        }

        /**
         * ViewHolder对自己的data 进行更改
         *
         * @param data
         */
        public void updateData(Data data) {
            if (callback != null) {
                callback.update(data, this);
            }
        }

        //数据绑定
        protected abstract void onBind(Data data);

    }
}

