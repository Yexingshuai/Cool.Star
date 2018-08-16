package com.example.myapp.myapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;

import com.example.myapp.myapp.data.bean.Subjects;
import com.example.myapp.myapp.di.glide.GlideContext;

/**
 * Created by yexing on 2018/5/7.
 */

public class MovieTop100Adapter extends BasicAdapter<Subjects> {

    private Context mCtx;

    public MovieTop100Adapter(Context mCtx) {
        this.mCtx = mCtx;
    }


    @Override
    protected RecyclerView.ViewHolder onCreateHolder() {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_movie_top250, null, false);
        NoramlViewHolder noramlViewHolder = new NoramlViewHolder(view);
        return noramlViewHolder;
    }

    @Override
    protected void onBind(final Subjects subjects, int position, RecyclerView.ViewHolder holder) {
        GlideContext.loadCommon(mCtx, subjects.getImages().getLarge(), ((NoramlViewHolder) holder).iv_film);
        ((NoramlViewHolder) holder).tv_film.setText(subjects.getTitle());
        ((NoramlViewHolder) holder).tv_film_english.setText(subjects.getOriginal_title());
        ((NoramlViewHolder) holder).tv_film_grade.setText("评分:" + subjects.getRating().getAverage());
        if (position < 9) {
            ((NoramlViewHolder) holder).tv_rank.setText("0" + (position + 1));
        } else {
            ((NoramlViewHolder) holder).tv_rank.setText("" + (position + 1));
        }
    }


    class NoramlViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_film;
        TextView tv_rank;
        TextView tv_film;
        TextView tv_film_english;
        TextView tv_film_grade;
        LinearLayout ll_item_view;

        public NoramlViewHolder(View itemView) {
            super(itemView);
            iv_film = itemView.findViewById(R.id.iv_film);
            tv_rank = itemView.findViewById(R.id.tv_rank);
            tv_film = itemView.findViewById(R.id.tv_film);
            tv_film_english = itemView.findViewById(R.id.tv_film_english);
            tv_film_grade = itemView.findViewById(R.id.tv_film_grade);
            ll_item_view = itemView.findViewById(R.id.ll_item_view);
        }
    }

}
