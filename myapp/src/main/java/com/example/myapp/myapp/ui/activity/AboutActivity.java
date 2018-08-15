package com.example.myapp.myapp.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;

public class AboutActivity extends BaseActivity {


    @Override
    public int inflateContentView() {
        return R.layout.activity_about;
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
