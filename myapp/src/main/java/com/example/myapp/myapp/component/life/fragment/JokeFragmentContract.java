package com.example.myapp.myapp.component.life.fragment;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.component.life.entity.JokeBean;

public interface JokeFragmentContract {


    interface Presenter extends BasePresenter {

        void requestJokeInfo(int type);
    }


    interface View extends BaseView<Presenter> {

        void setJokeInfo(JokeBean jokeBean);
    }
}


