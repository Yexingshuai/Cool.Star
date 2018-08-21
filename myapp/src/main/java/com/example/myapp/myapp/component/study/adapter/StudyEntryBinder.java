package com.example.myapp.myapp.component.study.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.data.bean.HomeItemBean;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;
import java.util.Random;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by yexing on 2018/8/17.
 */

public class StudyEntryBinder extends ItemViewBinder<HomeItemBean, StudyEntryBinder.ViewHolder> {

    private int[] array;
    private Context mCtx;

    public StudyEntryBinder(Context context) {

        this.mCtx = context;
        array = new int[]{
                mCtx.getResources().getColor(R.color.cardView),
                mCtx.getResources().getColor(R.color.cardView2),
                mCtx.getResources().getColor(R.color.colorAccent),
                mCtx.getResources().getColor(R.color.xxblue)};
    }


    @NonNull
    @Override
    protected StudyEntryBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_home_normal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HomeItemBean item) {
        final List<HomeItemBean.DataBean.DatasBean> data = item.getData().getDatas();
//        for (int i = 0; i < data.size(); i++) {

        int i = getPosition(holder);
        holder.tv_author.setText(data.get(i).getAuthor() != null ? data.get(i).getAuthor() : "");
        holder.tv_time.setText(data.get(i).getNiceDate() != null ? data.get(i).getNiceDate() : "");
        holder.tv_title.setText(data.get(i).getTitle() != null ? data.get(i).getTitle() : "");
        holder.tv_type.setText(data.get(i).getChapterName() != null ? data.get(i).getChapterName() : "");
        holder.likebutton.setLiked(data.get(i).isCollect());

        Random random = new Random();
        int j = random.nextInt(array.length);
        holder.ll_normal.setCardBackgroundColor(array[j]);

//        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_author;
        private TextView tv_time;
        private TextView tv_title;
        private TextView tv_type;
        private CardView ll_normal;
        private LikeButton likebutton;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_type = itemView.findViewById(R.id.tv_type);
            ll_normal = itemView.findViewById(R.id.ll_normal);
            likebutton = itemView.findViewById(R.id.likebutton);
            likebutton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                }

                @Override
                public void unLiked(LikeButton likeButton) {

                }
            });
        }
    }
}
