package com.example.myapp.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;

/**
 * Created by yexing on 2018/6/25.
 */

public class TestAboutActivity extends BaseActivity {
    @Override
    public int inflateContentView() {
        return R.layout.acitvity_test_dialog;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }


}
