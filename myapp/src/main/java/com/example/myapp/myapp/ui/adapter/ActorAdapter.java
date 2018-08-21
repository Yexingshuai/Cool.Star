package com.example.myapp.myapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapp.R;
import com.example.myapp.myapp.component.movie.detail.FilmDetailActivity;
import com.example.myapp.myapp.ui.activity.WebActivity;

import java.util.List;

import com.example.myapp.myapp.data.bean.FilmPeople;
import com.example.myapp.myapp.ui.helper.UiHelper;

/**
 * Created by daixiankade on 2018/5/8.
 */

public class ActorAdapter extends RecyclerView.Adapter {
    private Context mctx;
    private List<FilmPeople> list;

    public ActorAdapter(FilmDetailActivity filmDetailActivity, List<FilmPeople> list) {
        mctx = filmDetailActivity;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.item_actor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        FilmPeople filmPeople = list.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.setData(filmPeople);
//        if (filmPeople.getAvatars().getLarge()!=null) {
//            GlideContext.loadCommon(mctx, filmPeople.getAvatars().getLarge(), holder.iv_actor);
//        }
        Glide.with(mctx).load(filmPeople.getAvatars().getLarge()).diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder.iv_actor);

        holder.tv_actor_name.setText(filmPeople.getName());
        if (filmPeople.getType() == 1) {
            holder.tv_actor_type.setText("［导演］");
        } else {
            holder.tv_actor_type.setText("［演员］");
        }
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_actor;
        TextView tv_actor_name;
        TextView tv_actor_type;
        LinearLayout ll_actor;
        FilmPeople filmPeople;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_actor = itemView.findViewById(R.id.iv_actor);
            tv_actor_name = itemView.findViewById(R.id.tv_actor_name);
            tv_actor_type = itemView.findViewById(R.id.tv_actor_type);
            ll_actor = itemView.findViewById(R.id.ll_actor);
            ll_actor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String alt = filmPeople.getAlt();
                    UiHelper.skipWebActivity(mctx,null,alt);
                }
            });
        }

        public void setData(FilmPeople filmPeople) {
            this.filmPeople = filmPeople;
        }
    }

}
