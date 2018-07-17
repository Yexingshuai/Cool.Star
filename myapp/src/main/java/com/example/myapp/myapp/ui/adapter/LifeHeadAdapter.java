package com.example.myapp.myapp.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapp.R;


/**
 * Created by yexing on 2018/6/6.
 */

public class LifeHeadAdapter extends PagerAdapter {
    private int[] imgBgs = {R.mipmap.zuqiu, R.mipmap.lanqiu, R.mipmap.xingzuo};
    private String[] des = {"来一场足球较量吧！", "篮球梦", "星座运势"};
    //   private int[] imgBgs={R.mipmap.splash2};
    private Context context;

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public LifeHeadAdapter(Context context) {
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position=position%imgBgs.length;
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_life_head, container, false);
        ImageView iv_head = view.findViewById(R.id.iv_head);
        TextView tv_desc = view.findViewById(R.id.tv_desc);
        tv_desc.setText(des[position]);
        iv_head.setBackgroundResource(imgBgs[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
