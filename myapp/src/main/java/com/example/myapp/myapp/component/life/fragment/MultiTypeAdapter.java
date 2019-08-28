package com.example.myapp.myapp.component.life.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.myapp.myapp.component.life.entity.JokeBean;
import com.example.myapp.myapp.component.life.viewholder.BaseViewHolder;
import com.example.myapp.myapp.component.life.viewholder.GifRVHolder;
import com.example.myapp.myapp.component.life.viewholder.ImageRVHolder;
import com.example.myapp.myapp.component.life.viewholder.TextRVHolder;
import com.example.myapp.myapp.component.life.viewholder.VideoRVHolder;

import java.util.ArrayList;
import java.util.List;

public class MultiTypeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final String RV_TYPE_TEXT = "text";
    public static final String RV_TYPE_IMAGE = "image";
    public static final String RV_TYPE_GIF = "gif";
    public static final String RV_TYPE_VIDEO = "video";

    private List<JokeBean.DataBean> mlist = new ArrayList();
    private Context mContext;


    public MultiTypeAdapter(Context context, List<JokeBean.DataBean> mlist) {
        this.mlist = mlist;
        mContext = context;
    }


    @Override
    public int getItemViewType(int position) {
        switch (mlist.get(position).type) {
            case RV_TYPE_TEXT:
                return 1;
            case RV_TYPE_IMAGE:
                return 2;
            case RV_TYPE_GIF:
                return 3;
            case RV_TYPE_VIDEO:
                return 4;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return selectViewHolder(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
        JokeBean.DataBean dataBean = mlist.get(position);
        viewHolder.setData(dataBean);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mlist != null && mlist.size() > 0 ? mlist.size() : 0;
    }

    private BaseViewHolder selectViewHolder(ViewGroup viewGroup, int viewType) {
        BaseViewHolder baseViewHolder = null;
        switch (viewType) {
            case 1:
                return baseViewHolder = new TextRVHolder(LayoutInflater.from(mContext).inflate(TextRVHolder.getLayout(), viewGroup, false), mContext);
            case 2:
                return baseViewHolder = new ImageRVHolder(LayoutInflater.from(mContext).inflate(ImageRVHolder.getLayout(), viewGroup, false), mContext);
            case 3:
                return baseViewHolder = new GifRVHolder(LayoutInflater.from(mContext).inflate(GifRVHolder.getLayout(), viewGroup, false), mContext);
            case 4:
                return baseViewHolder = new VideoRVHolder(LayoutInflater.from(mContext).inflate(VideoRVHolder.getLayout(), viewGroup, false), mContext);
        }

        return baseViewHolder;
    }
}
