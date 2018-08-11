package com.example.myapp.myapp.ui.adapter;

import android.view.View;



public interface OnItemClickListener {
    /**
     * 点击
     *
     * @param view     click view.
     * @param position click position.
     */
    void onItemClick(View view, int position);

}
