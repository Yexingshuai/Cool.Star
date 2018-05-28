package com.example.myapp.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;

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
