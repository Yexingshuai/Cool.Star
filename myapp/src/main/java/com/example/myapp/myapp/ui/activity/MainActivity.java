package com.example.myapp.myapp.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import java.util.List;

import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.component.MainPresenter;
import com.example.myapp.myapp.test.TestStatic;
import com.example.myapp.myapp.ui.adapter.FragmentAdapter;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.base.BaseFragment;
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
    public TabLayout tab_layout;
    private MainPresenter mPresenter;
    private long mExitTime;
    private boolean isShow = true;


    @Override
    public int inflateContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        new MainPresenter(this);
        mViewPager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
//       new TestStatic.TestSS()


    }

    private void music() {
        try {
            MediaPlayer player = MediaPlayer.create(this, R.raw.baojing);
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initData() {
        List<Integer> mTabImg = mPresenter.getTabImg();
        List<BaseFragment> fragments = mPresenter.getFragments();
        List<String> mTabText = mPresenter.getPageTitle();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        tab_layout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);


        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tab_layout.getTabAt(i);
            NavigationButton button = new NavigationButton(this);
            button.init(mTabImg.get(i), mTabText.get(i), null);
            tab.setCustomView(button);
            if (i == 0) {
                TextView textOne = tab.getCustomView().findViewById(R.id.item_tab_text);
                textOne.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        }

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView text = tab.getCustomView().findViewById(R.id.item_tab_text);
                text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
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
        });
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

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


    @Override
    public void setPresenter(MainPresenter presenter) {
        mPresenter = presenter;
    }


    private void showBottomNav(final View mTarget) {

//        ObjectAnimator anim = ObjectAnimator.ofFloat(mTarget, "translationY", mTarget.getY(),
//                0);
//        anim.setDuration(200);
//        anim.start();

//        mTarget.animate().translationY(mTarget.getHeight());

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

//        ObjectAnimator anim = ObjectAnimator.ofFloat(mTarget, "translationY", mTarget.getY(),
//                mTarget.getY() + mTarget.getHeight());
//        anim.setDuration(200);
//        anim.start();

//        mTarget.animate().translationY(0);

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {
        if (msg.what == StudyFragment.NAVIGATION_HIDE) {
            hideBottomNav(tab_layout);
        } else if (msg.what == StudyFragment.NAVIGATION_SHOW) {
            showBottomNav(tab_layout);
        }
        isShow = !isShow;
    }

}
