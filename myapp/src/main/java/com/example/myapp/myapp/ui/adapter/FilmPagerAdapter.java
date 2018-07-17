package com.example.myapp.myapp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import com.example.myapp.myapp.base.BaseFragment;

/**
 * Created by daixiankade on 2018/5/2.
 */

public class FilmPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private List<BaseFragment> mFragments;

    public FilmPagerAdapter(FragmentManager fm, String[] mTitles, List<BaseFragment> mFragments) {
        super(fm);
        this.mTitles = mTitles;
        this.mFragments = mFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
