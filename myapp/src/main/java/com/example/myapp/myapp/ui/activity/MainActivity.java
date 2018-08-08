package com.example.myapp.myapp.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import java.util.List;

import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.component.MainPresenter;
import com.example.myapp.myapp.ui.adapter.FragmentAdapter;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.ui.dialog.DesignDialog;
import com.example.myapp.myapp.ui.fragment.StudyFragment;
import com.example.myapp.myapp.ui.view.MyViewPager;
import com.example.myapp.myapp.ui.view.NavigationButton;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by yexing on 2018/3/28.
 */

public class MainActivity extends BaseActivity implements BaseView<MainPresenter> {


    private MyViewPager mViewPager;
    public TabLayout mTabLayout;
    private MainPresenter mPresenter;
    private long mExitTime;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;


    @Override
    public int inflateContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        new MainPresenter(this);
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
    }


    @Override
    protected void initData() {
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
        ValueAnimator va = ValueAnimator.ofFloat(mTarget.getY(), mTarget.getTop());
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
        ValueAnimator va = ValueAnimator.ofFloat(mTarget.getY(), mTarget.getBottom());
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
            hideBottomNav(mTabLayout);
        } else if (msg.what == StudyFragment.NAVIGATION_SHOW) {
            showBottomNav(mTabLayout);
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

                mDrawerLayout.closeDrawers();

                return true;
            }

            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Toast.makeText(this, "再按一次退出程序哦", Toast.LENGTH_SHORT).show();

                mExitTime = System.currentTimeMillis();
            } else {

                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class NavigationItemSelectListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_item_like:
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "我喜欢的", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_item_weather:
                    Toast.makeText(MainActivity.this, "天气", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_item_skin:
                    DesignDialog designDialog = new DesignDialog();
                    designDialog.show(getSupportFragmentManager(), "tag");
                    break;
                case R.id.navigation_item_setting:
                    Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_item_about:
                    Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_SHORT).show();
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
