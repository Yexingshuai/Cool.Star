package com.example.myapp.myapp.component.movie;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.component.movie.live.FilmLiveFragment;
import com.example.myapp.myapp.component.movie.live.FilmLivePresenter;
import com.example.myapp.myapp.component.movie.top.FilmTop100Fragment;
import com.example.myapp.myapp.component.movie.top.FilmTop100Presenter;
import com.example.myapp.myapp.data.source.film.live.FilmLiveRepository;
import com.example.myapp.myapp.data.source.film.top.FilmTopRepository;
import com.example.myapp.myapp.ui.adapter.FilmPagerAdapter;

import java.util.ArrayList;

/**
 * Created by yexing on 2018/3/28.
 */

public class HappyFragment extends BaseFragment {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_happy;
    }

    @Override
    public void initView() {
        mTabLayout = getView(R.id.tab_layout);
        mViewPager = getView(R.id.vp);
    }

    @Override
    public void initData() {

        ArrayList<BaseFragment> fragments = new ArrayList<>();
        FilmLiveFragment filmLiveFragment = FilmLiveFragment.newInstance();
        new FilmLivePresenter(new FilmLiveRepository(), filmLiveFragment);
        fragments.add(filmLiveFragment);
        FilmTop100Fragment filmTop100Fragment = FilmTop100Fragment.newInstance();
        new FilmTop100Presenter(new FilmTopRepository(), filmTop100Fragment);
        fragments.add(filmTop100Fragment);
        FilmPagerAdapter filmPagerAdapter = new FilmPagerAdapter(getChildFragmentManager(), getResources().getStringArray(R.array.tab_film), fragments);
        mViewPager.setAdapter(filmPagerAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);

    }


}
