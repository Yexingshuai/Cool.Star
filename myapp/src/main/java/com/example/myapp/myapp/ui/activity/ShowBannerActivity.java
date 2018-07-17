package com.example.myapp.myapp.ui.activity;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;

public class ShowBannerActivity extends BaseActivity {

    private ImageView iv_banner;
    private String url;


    @Override
    public int inflateContentView() {
        return R.layout.activity_show_banner;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        iv_banner = findViewById(R.id.iv_banner);
        url = getIntent().getStringExtra("url");

    }

    @Override
    protected void initData() {
        Glide.with(this).load(url).into(iv_banner);
    }
}
