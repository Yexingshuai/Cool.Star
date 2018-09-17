package com.example.myapp.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.ui.view.DragLayout;

public class ShowBannerActivity extends BaseActivity {

    private ImageView iv_banner;
    private String url;
    private DragLayout mDragLayout;


    @Override
    public int inflateContentView() {
        return R.layout.activity_show_banner;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        iv_banner = findViewById(R.id.iv_banner);
        url = getIntent().getStringExtra("url");
        mDragLayout = findViewById(R.id.drag_layout);
        mDragLayout.bind(this).setDragListener(new DragLayout.DragListener() {
            @Override
            public void onDragFinished() {
                ShowBannerActivity.this.onBackPressed();
            }

        });
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
//        Glide.with(this).load(url).into(iv_banner);
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.IMMEDIATE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(iv_banner);
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }
}
