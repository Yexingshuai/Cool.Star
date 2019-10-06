package com.example.myapp.myapp.ui.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.utils.CacheCleanUtil;
import com.example.myapp.myapp.utils.PreferencesUtils;
import com.example.myapp.myapp.utils.ToastUtil;
import com.tencent.bugly.beta.Beta;

import org.androidannotations.annotations.App;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by YeXing on 2018/9/18.
 */

public class SettingActivity extends BaseActivity {

    private TextView mCache;
    private Switch mSwitchImage;
    private Switch mSwitchPush;
    private RelativeLayout mRootUpdate;

    @Override
    public int inflateContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        CardView cardView = getView(R.id.cardview);
        mCache = getView(R.id.tv_cache);
        setCommonClickListener(cardView);
        mSwitchImage = getView(R.id.switch_image);
        mSwitchPush = getView(R.id.switch_push);
        mRootUpdate = getView(R.id.rl_update);
        setCommonClickListener(mRootUpdate);
    }

    @Override
    protected void initData() {
        mSwitchImage.setChecked(PreferencesUtils.getBoolean(this, AppFlag.ISLOADIMAGE, true));
        mSwitchPush.setChecked(PreferencesUtils.getBoolean(this, AppFlag.ISRECEIVEPUSH, true));
        mSwitchImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PreferencesUtils.putBoolean(SettingActivity.this, AppFlag.ISLOADIMAGE, b);
            }
        });

        mSwitchPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PreferencesUtils.putBoolean(SettingActivity.this, AppFlag.ISRECEIVEPUSH, b);
                if (b) {
                    JPushInterface.resumePush(SettingActivity.this.getApplicationContext());
                } else {
                    JPushInterface.stopPush(SettingActivity.this.getApplicationContext());
                }
            }
        });
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

            case R.id.rl_update:
                Beta.checkUpgrade(true, false);
                break;
        }
    }
}
