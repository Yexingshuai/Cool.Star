package com.example.myapp.myapp.base;

import android.app.Application;
import android.content.Context;

import com.example.myapp.myapp.common.InitializeConfig;

/**
 * Created by yexing on 2018/3/28.
 */

public class MyApp extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        InitializeConfig.init(this);
        //添加代码
    }


}
