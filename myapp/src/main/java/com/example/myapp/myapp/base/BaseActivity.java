package com.example.myapp.myapp.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.myapp.ui.activity.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yexing on 2018/5/2.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG_LOG = this.getClass().getSimpleName();

    private List<TurnBackListener> mTurnBackListeners = new ArrayList<>();
    private View.OnClickListener mOnNavClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
    private View mStatus_bar;
    private long mExitTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflateContentView());
        //ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            //不显示默认的Title
            actionBar.setDisplayShowTitleEnabled(true);
            //不显示默认的Home（an activity icon or logo.）
            actionBar.setDisplayShowHomeEnabled(false);
            //是否显示左上角的返回按钮
            actionBar.setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationIcon(R.mipmap.icon_back);
            toolbar.setTitle(getTitle());
            toolbar.setNavigationOnClickListener(mOnNavClickListener);
        }
        mStatus_bar = findViewById(R.id.status_bar);

        initView(savedInstanceState);
        if (isNeedToBeSubscriber()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
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
     * 设置标题
     *
     * @param title
     */
    protected void setActionTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

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
     * 内存紧张时回调，资源释放
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
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

    /**
     * 跳转并执行动画
     *
     * @param intent
     */
    protected void startActivityTransition(Intent intent) {
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusBarColor(int color) {
        if (mStatus_bar != null) {
            mStatus_bar.setBackgroundResource(color);
        }
    }

    public interface TurnBackListener {
        boolean onTurnBack();
    }

    public void addOnTurnBackListener(TurnBackListener l) {
        this.mTurnBackListeners.add(l);
    }


    @Override
    public void onBackPressed() {

        for (TurnBackListener listener : mTurnBackListeners) {
            if (listener.onTurnBack()) {
                return;
            }
        }

        if (this instanceof MainActivity) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Toast.makeText(this, R.string.exitApp, Toast.LENGTH_SHORT).show();

                mExitTime = System.currentTimeMillis();
            } else {

                System.exit(0);
            }
        } else {
            super.onBackPressed();
        }

    }
}
