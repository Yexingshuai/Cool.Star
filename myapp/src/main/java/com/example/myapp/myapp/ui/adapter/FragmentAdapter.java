package com.example.myapp.myapp.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import java.util.List;

import com.example.myapp.myapp.base.BaseFragment;

/**
 * Created by lrd on 2018/3/22.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    //    private String [] title = {"one","two","three","four"};
    private List<BaseFragment> fragmentList;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
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

}
