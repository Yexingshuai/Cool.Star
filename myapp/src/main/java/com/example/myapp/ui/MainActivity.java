package com.example.myapp.ui;

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

import java.util.ArrayList;

import adapter.FragmentAdapter;
import base.BaseFragment;
import fragment.HappyFragment;
import fragment.LifeFragment;
import fragment.MyFragment;
import fragment.StudyFragment;
import view.MyViewPager;

/**
 * Created by daixiankade on 2018/3/28.
 */

public class MainActivity extends BaseActivity {


    private ArrayList<BaseFragment> fragments;
    private ArrayList<Integer> mTabImg;
    private ArrayList<String> mTabText;
    private MyViewPager view_pager;
    private TabLayout tab_layout;


    @Override
    public int inflateContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
         music();
        view_pager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);

        mTabImg = new ArrayList<>();
        mTabImg.add(R.drawable.tab_home_btn);
        mTabImg.add(R.drawable.tab_sm_btn);
        mTabImg.add(R.drawable.tab_shopping_btn);
        mTabImg.add(R.drawable.tab_preson_btn);
        mTabText = new ArrayList<>();
        mTabText.add("学习");
        mTabText.add("娱乐");
        mTabText.add("生活");
        mTabText.add("我的");

        fragments = new ArrayList<>();
        fragments.add(new StudyFragment());
        fragments.add(new HappyFragment());
        fragments.add(new LifeFragment());
        fragments.add(new MyFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(3);
        tab_layout.setupWithViewPager(view_pager);

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
                // 设置第一个tab的TextView是被选择的样式
                tab.getCustomView().findViewById(R.id.item_tab_img).setSelected(true);
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
                view_pager.setCurrentItem(tab.getPosition(), false);

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

    private void music() {


        try {
            MediaPlayer player = MediaPlayer.create(this, R.raw.baojing);
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

//        View.MeasureSpec.makeMeasureSpec();
    }

    @Override
    protected void initData() {

    }

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序哦.", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
