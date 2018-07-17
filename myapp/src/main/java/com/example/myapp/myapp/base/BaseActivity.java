package com.example.myapp.myapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.myapp.myapp.ui.view.StateLayout;

/**
 * Created by daixiankade on 2018/5/2.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG_LOG = this.getClass().getSimpleName();
    protected StateLayout stateLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflateContentView());
        initView(savedInstanceState);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG_LOG, "==onResume===");
    }


    public abstract int inflateContentView();

    /**
     * @param savedInstanceState 保存对象的状态.
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    protected abstract void initData();

    public <T> T getView(int id) {
        return (T) findViewById(id);
    }
}
