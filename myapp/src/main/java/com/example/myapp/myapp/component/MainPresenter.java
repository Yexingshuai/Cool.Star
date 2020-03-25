package com.example.myapp.myapp.component;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.component.life.LifeFragment;
import com.example.myapp.myapp.component.life.LifeFragment2;
import com.example.myapp.myapp.component.life.LifeFragment3;
import com.example.myapp.myapp.component.life.LifeFragmentPresenter;
import com.example.myapp.myapp.component.movie.HappyFragment;
import com.example.myapp.myapp.component.news.NewsFragment;
import com.example.myapp.myapp.component.news.NewsFragmentPresenter;
import com.example.myapp.myapp.component.study.StudyFragment;
import com.example.myapp.myapp.component.study.StudyFragmentPresenter;
import com.example.myapp.myapp.data.source.life.LifeFragmentRepository;
import com.example.myapp.myapp.data.source.news.NewsFragmentRepository;
import com.example.myapp.myapp.data.source.study.StudyFragmentRepository;
import com.example.myapp.myapp.ui.activity.MainActivity;

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
        fragmentList.add(HappyFragment.newInstance());
        //新闻
        NewsFragment newsFragment = NewsFragment.newInstance();
        new NewsFragmentPresenter(newsFragment, new NewsFragmentRepository());
//        fragmentList.add(newsFragment);
        //生活
        LifeFragment lifeFragment = LifeFragment.newInstance();
        new LifeFragmentPresenter(new LifeFragmentRepository(), lifeFragment);

        LifeFragment3 lifeFragment3 = LifeFragment3.newInstance();
        new LifeFragmentPresenter(new LifeFragmentRepository(), lifeFragment3);
        fragmentList.add(lifeFragment3);

        return fragmentList;
    }

    public List<String> getPageTitle() {
        List<String> titleList = new ArrayList<>();
        titleList.add("学习");
        titleList.add("影评");
//        titleList.add("新闻");
        titleList.add("生活");

        return titleList;
    }

    public List getTabImg() {
        List<Integer> mTabImg = new ArrayList<>();
        mTabImg.add(R.drawable.tab_home_btn);
        mTabImg.add(R.drawable.tab_sm_btn);
//        mTabImg.add(R.drawable.tab_news_btn);
        mTabImg.add(R.drawable.tab_shopping_btn);
        return mTabImg;
    }
}
