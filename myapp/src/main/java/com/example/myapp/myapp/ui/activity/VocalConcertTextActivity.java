package com.example.myapp.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.sunfusheng.marqueeview.MarqueeView;

/**
 * Created by yexing on 2018/9/19.
 */

public class VocalConcertTextActivity extends BaseActivity {

    public static final String TEXT = "mText";
    private MarqueeView marqueeView;

    @Override
    public int inflateContentView() {
        return R.layout.activity_vocalconcert_text;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        marqueeView = (MarqueeView) findViewById(R.id.marqueeView);
    }

    @Override
    protected void initData() {
        String text = getIntent().getStringExtra(TEXT);
        marqueeView.startWithText(text);

    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }
}
