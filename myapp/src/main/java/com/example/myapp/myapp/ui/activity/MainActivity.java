package com.example.myapp.myapp.ui.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import java.util.List;

import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.component.MainPresenter;
import com.example.myapp.myapp.ui.adapter.FragmentAdapter;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.ui.view.MyViewPager;

/**
 * Created by yexing on 2018/3/28.
 */

public class MainActivity extends BaseActivity implements BaseView<MainPresenter> {


    private MyViewPager mViewPager;
    public TabLayout tab_layout;
    private MainPresenter mPresenter;
    private long mExitTime;


    @Override
    public int inflateContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        new MainPresenter(this);
        mViewPager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
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
            View view = LayoutInflater.from(this).inflate(R.layout.item_tablayout, null);
            ImageView img = view.findViewById(R.id.item_tab_img);
            TextView text = view.findViewById(R.id.item_tab_text);
            img.setImageResource(mTabImg.get(i));
            text.setText(mTabText.get(i));
            if (tab != null) {
                tab.setCustomView(view);
            }
            if (i == 0) {
                TextView textOne = tab.getCustomView().findViewById(R.id.item_tab_text);
                textOne.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        }

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.item_tab_img).setSelected(true);
                TextView text = tab.getCustomView().findViewById(R.id.item_tab_text);
                text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                mViewPager.setCurrentItem(tab.getPosition(), false);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = tab.getCustomView().findViewById(R.id.item_tab_text);
                text.setTextColor(getResources().getColor(R.color.textNormalColor));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
}
