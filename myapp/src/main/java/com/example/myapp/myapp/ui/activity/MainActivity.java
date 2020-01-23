package com.example.myapp.myapp.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.component.MainPresenter;
import com.example.myapp.myapp.component.favorite.MyFavoriteActivity;
import com.example.myapp.myapp.component.login.LoginActivity;
import com.example.myapp.myapp.component.login.helper.LoginContext;
import com.example.myapp.myapp.component.study.StudyFragment;
import com.example.myapp.myapp.component.weather.WeatherActivity;
import com.example.myapp.myapp.ui.adapter.FragmentAdapter;
import com.example.myapp.myapp.ui.dialog.BottomGridDialog;
import com.example.myapp.myapp.ui.dialog.DesignDialog;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.ui.view.MyViewPager;
import com.example.myapp.myapp.ui.view.NavigationButton;
import com.example.myapp.myapp.utils.PreferencesUtils;
import com.example.myapp.myapp.utils.ToastUtil;
import com.example.myapp.myapp.utils.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by yexing on 2018/3/28.  现在在测试分支上
 */

public class MainActivity extends BaseActivity implements BaseView<MainPresenter>, BaseActivity.TurnBackListener {


    private MyViewPager mViewPager;
    private TabLayout mTabLayout;
    private MainPresenter mPresenter;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private TextView mUserName;
    private MenuItem logoutMenuItem;
    /**
     * 底部导航动画展示状态标记
     */
    public static boolean isShow = true;

    @Override
    public int inflateContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        new MainPresenter(this);
        addOnTurnBackListener(this);
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        View headerView = mNavigationView.getHeaderView(0);
        mUserName = headerView.findViewById(R.id.tv_username);
        logoutMenuItem = mNavigationView.getMenu().findItem(R.id.navigation_item_logout);
        Utils.setDarkStatusIcon(this, true);

//        BottomGridDialog bottomGridDialog = new BottomGridDialog(this);bottomGridDialog.show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void initData() {

        String userName = LoginContext.getInstance().getUserName();
        if (userName != null) {
            JPushInterface.setAlias(this, 1, userName);
        }
        //判断是否接受推送
        if (!PreferencesUtils.getBoolean(this, AppFlag.ISRECEIVEPUSH, true)) {
            JPushInterface.stopPush(this.getApplicationContext());
        }
        List<Integer> mTabImg = mPresenter.getTabImg();
        List<BaseFragment> fragments = mPresenter.getFragments();
        List<String> mTabText = mPresenter.getPageTitle();
        mNavigationView.setNavigationItemSelectedListener(new NavigationItemSelectListener());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            NavigationButton button = new NavigationButton(this);
            button.init(mTabImg.get(i), mTabText.get(i), null);
            tab.setCustomView(button);
            if (i == 0) {
                TextView textOne = tab.getCustomView().findViewById(R.id.item_tab_text);
                textOne.setTextColor(getResources().getColor(R.color.icon_select));
                button.setSelected1(true);
            }
        }

        mTabLayout.addOnTabSelectedListener(new TabLayoutOnItemSelectListener());
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateNavigationHeadInfo();  //获取焦点时，即更新用户状态信息
    }

    private void updateNavigationHeadInfo() {
        String userName = LoginContext.getInstance().getUserName();
        if (userName != null) {
            mUserName.setText(userName);
            logoutMenuItem.setVisible(true);  // true 为显示，false 为隐藏
        } else {
            mUserName.setText("");
            logoutMenuItem.setVisible(false);  // true 为显示，false 为隐藏
        }
    }

    /**
     * 接收presenter
     *
     * @param presenter presenter.
     */
    @Override
    public void setPresenter(MainPresenter presenter) {
        mPresenter = presenter;
    }


    private void showBottomNav(final View mTarget) {
        // 这种效果最好
        ValueAnimator va = ValueAnimator.ofFloat(mTarget.getY(), mTarget.getTop());// 1920--1773
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTarget.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }

    private void hideBottomNav(final View mTarget) {
        //这种效果最好
        ValueAnimator va = ValueAnimator.ofFloat(mTarget.getY(), mTarget.getBottom());//1773-1920
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTarget.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }


    /**
     * 接收Recyclerview滑动事件
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {
        if (msg.what == StudyFragment.NAVIGATION_HIDE) {
            if (isShow) {
                isShow = !isShow;
                hideBottomNav(mTabLayout);
            }
        } else if (msg.what == StudyFragment.NAVIGATION_SHOW) {
            if (!isShow) {
                isShow = !isShow;
                showBottomNav(mTabLayout);
            }
        } else if (msg.what == BaseFragment.SHOWINDICATOR) {
            showBottomNav(mTabLayout);
        }

    }

    /**
     * 展开侧拉菜单 DrawerLayout
     */
    public void toggle() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onTurnBack() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Jzvd.releaseAllVideos();
    }

    class NavigationItemSelectListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_item_like:
                    if (LoginContext.getInstance().isLogined()) {
                        UiHelper.skipActivityNofinish(MainActivity.this, MyFavoriteActivity.class);
                    } else {
                        ToastUtil.showApp(getString(R.string.request_login));
                        UiHelper.skipActivityNofinish(MainActivity.this, LoginActivity.class);
                    }
                    break;
                case R.id.navigation_item_weather:
                    UiHelper.skipActivityNofinish(MainActivity.this, WeatherActivity.class);
                    break;
                case R.id.navigation_item_skin:
                    DesignDialog designDialog = new DesignDialog();
                    designDialog.show(getSupportFragmentManager(), "tag");
                    break;
                case R.id.navigation_item_logout:
                    if (LoginContext.getInstance().logout()) {
                        updateNavigationHeadInfo();
                    } else {
                        ToastUtil.showApp(getString(R.string.logout_error));
                    }
                    break;

                case R.id.navigation_item_setting:
                    UiHelper.skipActivityNofinish(MainActivity.this, SettingActivity.class);
                    break;
                case R.id.navigation_item_about:
                    startActivityTransition(new Intent(MainActivity.this, AboutActivity.class));
                    break;
            }
            mDrawerLayout.closeDrawers();
            return false;
        }
    }

    class TabLayoutOnItemSelectListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            TextView text = tab.getCustomView().findViewById(R.id.item_tab_text);
            text.setTextColor(getResources().getColor(R.color.icon_select));
            NavigationButton customView = (NavigationButton) tab.getCustomView();
            customView.setSelected1(true);
            mViewPager.setCurrentItem(tab.getPosition(), false);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            TextView text = tab.getCustomView().findViewById(R.id.item_tab_text);
            text.setTextColor(getResources().getColor(R.color.textNormalColor));
            NavigationButton customView = (NavigationButton) tab.getCustomView();
            customView.setSelected1(false);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

}
