package com.example.myapp.myapp.component.life.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapp.myapp.data.bean.JokeBean;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {


    public Context mContext;

    public BaseViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        initView();
        initData();
    }


    public abstract void initView();


    public abstract void initData();


    public void setData(JokeBean.DataBean dataBean) {

    }
}
