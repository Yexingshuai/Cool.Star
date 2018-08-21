package com.example.myapp.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.ui.helper.UiHelper;

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
        final String webUrl = getIntent().getStringExtra("webUrl");
        iv_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiHelper.skipWebActivity(ShowBannerActivity.this, null, webUrl);
                finish();
            }
        });

    }

    @Override
    protected void initData() {
        Glide.with(this).load(url).into(iv_banner);
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }
}
