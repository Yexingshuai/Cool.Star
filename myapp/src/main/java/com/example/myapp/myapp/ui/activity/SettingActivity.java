package com.example.myapp.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.utils.CacheCleanUtil;
import com.example.myapp.myapp.utils.ToastUtil;
import com.kongqw.radarscanviewlibrary.RadarScanView;

/**
 * Created by Yexing on 2018/9/18.
 */

public class SettingActivity extends BaseActivity {

    private TextView mCache;

    @Override
    public int inflateContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        CardView cardView = getView(R.id.cardview);
        mCache = getView(R.id.tv_cache);
        setCommonClickListener(cardView);
    }

    @Override
    protected void initData() {
        try {
            String totalCacheSize = CacheCleanUtil.getTotalCacheSize(getApplicationContext());
            mCache.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void onClickImpl(View view) {
        super.onClickImpl(view);
        switch (view.getId()) {
            case R.id.cardview:
                CacheCleanUtil.clearAllCache(getApplicationContext());
                try {
                    mCache.setText(CacheCleanUtil.getTotalCacheSize(getApplicationContext()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ToastUtil.showApp("清除成功！");
                break;
        }
    }
}
