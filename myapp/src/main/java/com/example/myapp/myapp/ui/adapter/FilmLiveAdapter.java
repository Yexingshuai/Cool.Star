package com.example.myapp.myapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.List;

import com.example.myapp.myapp.data.bean.Subjects;
import com.example.myapp.myapp.di.glide.GlideContext;
import com.example.myapp.myapp.utils.Utils;

/**
 * Created by daixiankade on 2018/5/2.
 */

public class FilmLiveAdapter extends RecyclerView.Adapter {
    private List<Subjects> subjects;
    private Context mCtx;

    public FilmLiveAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_live, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final FilmViewHolder filmViewHolder = (FilmViewHolder) holder;

        final Subjects subjects = this.subjects.get(position);
        String url = subjects.getImages().getLarge();
        ViewGroup.LayoutParams params = filmViewHolder.iv_film.getLayoutParams();
        int width = Utils.getScreenWidthDp(mCtx);
        int ivWidth = (width - Utils.dp2px(mCtx, 80)) / 3;
//        params.width = ivWidth;
        double height = (420.0 / 300.0) * ivWidth;
//        params.height = (int) height;
//        filmViewHolder.iV_film.setLayoutParams(params);
        GlideContext.loadCommon(mCtx, url, filmViewHolder.iv_film);
        if (!TextUtils.isEmpty("" + subjects.getRating().getAverage())) {
            filmViewHolder.tv_film_grade.setText("评分:" + String.valueOf(subjects.getRating().getAverage()));
        } else {
            filmViewHolder.tv_film_grade.setText("");
        }
        filmViewHolder.tv_film_name.setText(subjects.getTitle());
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClick(filmViewHolder.iv_film, position, subjects);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (subjects != null && subjects.size() > 0) {
            return subjects.size();
        }
        return 0;
    }

    public void addFilmData(List<Subjects> subjects) {
        this.subjects = subjects;
        notifyDataSetChanged();
    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void OnItemClick(View view, int position, T data);
    }

    class FilmViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_film;
        TextView tv_film_grade;
        TextView tv_film_name;

        public FilmViewHolder(View itemView) {
            super(itemView);
            iv_film = itemView.findViewById(R.id.iV_film);
            tv_film_grade = itemView.findViewById(R.id.tv_film_grade);
            tv_film_name = itemView.findViewById(R.id.tv_film_name);
        }
    }

}
