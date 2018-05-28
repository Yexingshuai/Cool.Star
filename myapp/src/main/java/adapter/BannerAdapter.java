package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapp.ui.ShowBannerActivity;

import java.util.List;

import bean.BannerBean;
import utils.MyAnimationUtils;

/**
 * Created by daixiankade on 2018/3/29.
 */

public class BannerAdapter extends PagerAdapter {
    private BannerBean banner;
    private final List<BannerBean.DataBean> data;
    private Context mCtx;

    public BannerAdapter(BannerBean banner, Context context) {

        this.banner = banner;
        this.mCtx = context;
        data = banner.getData();


    }

    @Override
    public int getCount() {
        if (data.size() > 0 & data != null) {
            return data.size()*100;
        }
        return 0;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        position = position % data.size();
        final String imagePath = data.get(position).getImagePath();
        final ImageView imageView = new ImageView(mCtx);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) container.getContext(), imageView, "bannerImage");
                Bundle bannerBundle = MyAnimationUtils.makeSceneTransition((Activity) container.getContext(), imageView, "bannerImage");
                Intent intent = new Intent(mCtx, ShowBannerActivity.class);
                intent.putExtra("url",imagePath);
                mCtx.startActivity(intent, bannerBundle);
            }
        });
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mCtx).load(imagePath).into(imageView);
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
