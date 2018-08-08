package com.example.myapp.myapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapp.myapp.ui.activity.ShowBannerActivity;

import java.util.List;

import com.example.myapp.myapp.data.bean.BannerBean;
import com.example.myapp.myapp.ui.helper.GlideRoundTransform;
import com.example.myapp.myapp.utils.MyAnimationUtils;

/**
 * Created by daixiankade on 2018/3/29.
 */

public class BannerAdapter extends PagerAdapter {

    private List<BannerBean.DataBean> data;
    private Context mCtx;

    public BannerAdapter(List<BannerBean.DataBean> banner, Context context) {

        this.data = banner;
        this.mCtx = context;



    }

    @Override
    public int getCount() {
        if (data.size() > 0 & data != null) {
            return data.size() * 100;
        }
        return 0;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        position = position % data.size();
        final String imagePath = data.get(position).getImagePath();
        final String webUrl = data.get(position).getUrl();
        final ImageView imageView = new ImageView(mCtx);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) container.getContext(), imageView, "bannerImage");
                Bundle bannerBundle = MyAnimationUtils.makeSceneTransition((Activity) container.getContext(), imageView, "bannerImage");
                Intent intent = new Intent(mCtx, ShowBannerActivity.class);
                intent.putExtra("url", imagePath);
                intent.putExtra("webUrl",webUrl);
                mCtx.startActivity(intent, bannerBundle);
            }
        });
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        Glide.with(mCtx).load(imagePath).into(imageView);
        Glide.with(mCtx).load(imagePath).transform(new GlideRoundTransform(mCtx,2)).diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
