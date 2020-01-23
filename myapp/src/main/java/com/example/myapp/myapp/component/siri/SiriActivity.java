package com.example.myapp.myapp.component.siri;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.component.siri.fragment.ActiveFragment;
import com.example.myapp.myapp.component.siri.fragment.ContactFragment;
import com.example.myapp.myapp.component.siri.fragment.GroupFragment;
import com.example.myapp.myapp.component.siri.helper.NavHelper;
import com.example.myapp.myapp.utils.Utils;

import java.util.Objects;

public class SiriActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer> {

    private NavHelper<Integer> mNavHelper;
    private AppBarLayout mAppbar;
    private BottomNavigationView mNavigationView;
    private TextView mTitle;
    private FloatingActionButton mAction;


    @Override
    public int inflateContentView() {
        return R.layout.activity_siri;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mAppbar = getView(R.id.appbar);
        setCommonClickListener(findViewById(R.id.im_search));
        mNavigationView = getView(R.id.navigation_view);
        mTitle = getView(R.id.txt_title);
        mAction = getView(R.id.floatbt);

    }

    @Override
    protected void initData() {

        //用Glide加载图片

        Glide.with(this).load(R.mipmap.bg_src_morning)
                .apply(new RequestOptions().centerCrop())
                .into(new ViewTarget<View, Drawable>(mAppbar) {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        this.view.setBackground(resource);
                    }
                });
        mNavHelper = new NavHelper<Integer>(this, R.id.lay_container, getSupportFragmentManager(), this);
        mNavHelper.addTab(R.id.action_home, new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home))
                .addTab(R.id.action_group, new NavHelper.Tab<>(GroupFragment.class, R.string.title_group))
                .addTab(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact));

        mNavigationView.setOnNavigationItemSelectedListener(this);

        Menu menu = mNavigationView.getMenu();
        // 触发首次选中Home
        menu.performIdentifierAction(R.id.action_home, 0);
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return mNavHelper.performClickMenu(item.getItemId());
    }

    /**
     * Tab按钮点击事件
     *
     * @param newTab
     * @param oldTab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        //切换标题
        mTitle.setText(newTab.extra);

        //执行动画
        float translationY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra, R.string.title_home)) {
            // 主界面时隐藏
            translationY = Utils.dp2px(this, 76);
        } else {
            if (Objects.equals(newTab.extra, R.string.title_group)) {
                // 群
                mAction.setImageResource(R.drawable.ic_group_add);
                rotation = -360;
            } else {
                // 联系人
                mAction.setImageResource(R.drawable.ic_contact_add);
                rotation = 360;
            }
        }

        // 开始动画
        // 旋转，Y轴位移，弹性差值器，时间
        mAction.animate()
                .rotation(rotation)
                .translationY(translationY)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(500)
                .start();
    }


    @Override
    protected void onClickImpl(View view) {
        super.onClickImpl(view);
        switch (view.getId()) {
            case R.id.im_search:
//                ToastUtil.showApp(" ");
                break;
        }
    }


}
