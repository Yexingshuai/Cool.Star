package com.example.myapp.myapp.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.example.myapp.myapp.ui.view.StateLayout;

import org.greenrobot.eventbus.EventBus;


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
        if (isNeedToBeSubscriber()) {
            EventBus.getDefault().register(this);
        }

        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG_LOG, "==onResume===");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isNeedToBeSubscriber()) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        }
    }


    /**
     * 填充布局
     *
     * @return
     */
    public abstract int inflateContentView();

    /**
     * @param savedInstanceState 保存对象的状态.
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    protected abstract void initData();

    /**
     * 是否需要接收广播
     *
     * @return
     */
    protected abstract boolean isNeedToBeSubscriber();


    public <T> T getView(int id) {
        return (T) findViewById(id);
    }

    /**
     * Click listener 的统一管理
     */
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickImpl(v);
        }
    };

    /**
     * @param view v
     * @see #setCommonClickListener(View)
     * 接收点击事件
     */
    protected void onClickImpl(View view) {
        int id = view.getId();

    }


    /**
     * 设置点击事件
     *
     * @param view v
     */
    protected void setCommonClickListener(View view) {
        if (view == null) {
            return;
        }
        view.setOnClickListener(mOnClickListener);

    }

    /**
     * Add current activity with transition animation.
     */
    protected void showDefaultActivityTransition() {
        //Animation.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide(Gravity.RIGHT).setDuration(500));
        }
    }

    /**
     * Add current activity with transition animation.
     */
    protected void showBoundActivityTransition() {
        //Animation.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new ChangeBounds().setDuration(500));
        }
    }

    /**
     * Add current activity with transition animation.
     */
    protected void showFadeActivityTransition() {
        //Animation.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade().setDuration(500));
        }
    }

    /**
     * Add current activity with transition animation.
     */
    protected void showExplodeActivityTransition() {
        //Animation.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Explode().setDuration(500));
        }
    }
}
