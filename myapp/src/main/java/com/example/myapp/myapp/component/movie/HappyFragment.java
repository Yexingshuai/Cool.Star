package com.example.myapp.myapp.component.movie;

import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.component.movie.FilmLiveFragment;
import com.example.myapp.myapp.component.movie.FilmTop250Fragment;
import com.example.myapp.myapp.ui.adapter.FilmPagerAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by daixiankade on 2018/3/28.
 */

public class HappyFragment extends BaseFragment implements View.OnClickListener {


    private TabLayout tab_layout;
    private ViewPager vp;
    private RelativeLayout title_bar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_happy;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

        tab_layout = getView(R.id.tab_layout);
        title_bar = getView(R.id.title_bar);
        vp = getView(R.id.vp);
        Object view = getView(R.id.toolbar);
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new FilmLiveFragment());
        fragments.add(new FilmTop250Fragment());
        FilmPagerAdapter filmPagerAdapter = new FilmPagerAdapter(getChildFragmentManager(), getResources().getStringArray(R.array.tab_film), fragments);
        vp.setAdapter(filmPagerAdapter);

        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        tab_layout.setTabMode(TabLayout.MODE_FIXED);
//        mTabLayout.setSelectedTabIndicatorColor(ThemeUtils.getThemeColor());
//        mTabLayout.setTabTextColors(getResources().getColor(R.color.text_gray_6),ThemeUtils.getThemeColor());
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        tab_layout.setupWithViewPager(vp);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
//        mTabLayout.setTabsFromPagerAdapter(filmPagerAdapter);
        showContentView();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {

    }


}
