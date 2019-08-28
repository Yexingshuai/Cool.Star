package com.example.myapp.myapp.component.life.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseWidget {

    public View mRootView;
    public Context mContext;


    public BaseWidget(Context context) {
        this.mContext = context;
    }

    public void init() {
        mRootView = LayoutInflater.from(mContext).inflate(getWidgetLayout(), null);
        initView();
        initData();

    }

    public void initView() {

    }

    public void initData() {

    }


    public View getRootView() {
        return mRootView;
    }

    public abstract int getWidgetLayout();

}
