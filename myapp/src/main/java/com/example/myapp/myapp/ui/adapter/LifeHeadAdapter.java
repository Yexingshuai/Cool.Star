package com.example.myapp.myapp.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.data.bean.JokeResponse;

import java.util.List;
import java.util.Random;


/**
 * Created by yexing on 2018/6/6.
 */

public class LifeHeadAdapter extends PagerAdapter {
        private int[] imgBgs = {R.mipmap.zuqiu, R.mipmap.lanqiu, R.mipmap.xingzuo};
//    private int[] imgBgs = {R.mipmap.joke_cat, R.mipmap.joke_dog, R.mipmap.joke_dog1};
    private String[] des = {"来一场足球较量吧！", "篮球梦", "星座运势"};
    private Context context;
    private List<JokeResponse.ResultBean.DataBean> jokeList;

    @Override
    public int getCount() {
        return jokeList.size();
    }

    public LifeHeadAdapter(Context context, List<JokeResponse.ResultBean.DataBean> jokeList) {
        this.context = context;
        this.jokeList = jokeList;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        JokeResponse.ResultBean.DataBean dataBean = jokeList.get(position);
        Random random = new Random();
        int i = random.nextInt(imgBgs.length);

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_life_head, container, false);
        ImageView iv_head = view.findViewById(R.id.iv_head);
        TextView tv_desc = view.findViewById(R.id.tv_desc);
        tv_desc.setText(dataBean.getContent());
        iv_head.setBackgroundResource(imgBgs[i]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
