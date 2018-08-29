package com.example.myapp.myapp.component;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.component.life.LifeFragmentPresenter;
import com.example.myapp.myapp.component.study.StudyFragment2;
import com.example.myapp.myapp.component.study.StudyFragmentPresenter;
import com.example.myapp.myapp.component.study.adapter.StudyEntryBinder;
import com.example.myapp.myapp.data.source.life.LifeFragmentResiporty;
import com.example.myapp.myapp.data.source.study.StudyFragmentRepository;
import com.example.myapp.myapp.ui.activity.MainActivity;
import com.example.myapp.myapp.component.movie.HappyFragment;
import com.example.myapp.myapp.component.life.LifeFragment;
import com.example.myapp.myapp.ui.fragment.MyFragment;
import com.example.myapp.myapp.component.study.StudyFragment;

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
        //学习
        StudyFragment studyFragment = StudyFragment.newInstance();
        new StudyFragmentPresenter(new StudyFragmentRepository(), studyFragment);
        fragmentList.add(studyFragment);
        //娱乐
        fragmentList.add(new HappyFragment());
        //生活
        LifeFragment lifeFragment = LifeFragment.newInstance();
        new LifeFragmentPresenter(new LifeFragmentResiporty(), lifeFragment);
        fragmentList.add(lifeFragment);
        return fragmentList;
    }

    public List<String> getPageTitle() {
        List<String> titleList = new ArrayList<>();
        titleList.add("学习");
        titleList.add("娱乐");
        titleList.add("生活");
//        titleList.add("我的");
        return titleList;
    }

    public List getTabImg() {
        List<Integer> mTabImg = new ArrayList<>();
        mTabImg.add(R.drawable.tab_home_btn);
        mTabImg.add(R.drawable.tab_sm_btn);
        mTabImg.add(R.drawable.tab_shopping_btn);
//        mTabImg.add(R.drawable.tab_preson_btn);
        return mTabImg;
    }
}
