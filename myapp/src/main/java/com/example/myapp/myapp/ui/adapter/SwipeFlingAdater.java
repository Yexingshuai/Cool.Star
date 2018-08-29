package com.example.myapp.myapp.ui.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.data.bean.JokeResponse;

import java.util.List;
import java.util.Random;

/**
 * Created by yexing on 2018/8/29.
 */

public class SwipeFlingAdater extends BaseAdapter {
    private Context mCtx;
    private List<JokeResponse.ResultBean.DataBean> mList;
//    private int[] imgBgs = {R.mipmap.joke_cat, R.mipmap.joke_dog, R.mipmap.joke_dog1};
    private int[] imgBgs = {R.mipmap.joke_bg};

    public SwipeFlingAdater(FragmentActivity activity, List<JokeResponse.ResultBean.DataBean> jokeList) {
        this.mCtx = activity;
        mList = jokeList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Random random = new Random();
        int i = random.nextInt(imgBgs.length);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mCtx).inflate(R.layout.item_life_head, viewGroup, false);
            holder.tvJoke = convertView.findViewById(R.id.tv_desc);
            holder.ivBg = convertView.findViewById(R.id.iv_head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivBg.setImageResource(imgBgs[0]);

        holder.tvJoke.setText(mList.get(position).getContent());

        return convertView;
    }

    static class ViewHolder {

        TextView tvJoke;
        ImageView ivBg;
    }

}
