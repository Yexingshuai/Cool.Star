package com.example.myapp.myapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yexing on 2018/6/7.
 */

public class TestAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestHolder(new TextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TestHolder)holder).tv.setText("666666666666666666666");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TestHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public TestHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView;
        }
    }

}
