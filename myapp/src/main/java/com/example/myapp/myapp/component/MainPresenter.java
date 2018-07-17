package com.example.myapp.myapp.component;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.component.study.StudyFragmentPresenter;
import com.example.myapp.myapp.data.source.study.StudyFragmentRepository;
import com.example.myapp.myapp.ui.activity.MainActivity;
import com.example.myapp.myapp.ui.fragment.HappyFragment;
import com.example.myapp.myapp.ui.fragment.LifeFragment;
import com.example.myapp.myapp.ui.fragment.MyFragment;
import com.example.myapp.myapp.ui.fragment.StudyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexing on 2018/7/16.
 */

public class MainPresenter implements BasePresenter {

    private MainActivity mView;

    public MainPresenter(MainActivity view) {
        mView = view;
        mView.setPresenter(this);

    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public List getFragments() {
        List<BaseFragment> fragmentList = new ArrayList<>();

        StudyFragment studyFragment = StudyFragment.newInstance();
        fragmentList.add(studyFragment);
        new StudyFragmentPresenter(new StudyFragmentRepository(),studyFragment);

        fragmentList.add(new HappyFragment());
        fragmentList.add(new LifeFragment());
        fragmentList.add(new MyFragment());
        return fragmentList;
    }

    public List<String> getPageTitle() {
        List<String> titleList = new ArrayList<>();
        titleList.add("学习");
        titleList.add("娱乐");
        titleList.add("生活");
        titleList.add("我的");
        return titleList;
    }

    public List getTabImg() {
        List<Integer> mTabImg = new ArrayList<>();
        mTabImg.add(R.drawable.tab_home_btn);
        mTabImg.add(R.drawable.tab_sm_btn);
        mTabImg.add(R.drawable.tab_shopping_btn);
        mTabImg.add(R.drawable.tab_preson_btn);
        return mTabImg;
    }
}
