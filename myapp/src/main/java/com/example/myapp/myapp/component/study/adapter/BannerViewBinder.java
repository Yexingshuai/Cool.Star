package com.example.myapp.myapp.component.study.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapp.myapp.base.MyApp;
import com.example.myapp.myapp.data.bean.BannerBean;
import com.example.myapp.myapp.di.glide.GlideContext;
import com.example.myapp.myapp.utils.DeviceUtils;
import com.example.myapp.myapp.utils.Utils;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by yexing on 2018/8/17.
 */

public class BannerViewBinder extends ItemViewBinder<BannerBean, BannerViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        ViewPager viewPager = new ViewPager(parent.getContext());
        viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dp2px(MyApp.getContext(), 200)));
        return new ViewHolder(viewPager);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BannerBean item) {
        List<BannerBean.DataBean> data = item.getData();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewPager viewPager;

        ViewHolder(View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView;
        }
    }
}
