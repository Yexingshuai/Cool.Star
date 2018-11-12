package com.example.myapp.myapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;


import java.util.List;

import com.example.myapp.myapp.base.BaseFragment;

/**
 * Created by lrd on 2018/3/22.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fm;
    private List<BaseFragment> fragmentList;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fm = fm;
        this.fragmentList = fragmentList;
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
