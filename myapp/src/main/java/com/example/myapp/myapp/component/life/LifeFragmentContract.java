package com.example.myapp.myapp.component.life;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.JokeResponse;

/**
 * Created by yexing on 2018/8/28.
 */

public interface LifeFragmentContract {


    interface Presenter extends BasePresenter {

        void requestJoke(int pageNum, int pageSize);
    }

    interface View extends BaseView<Presenter> {

        void setJokeInfo(JokeResponse response);

        void requestJokeFail(String errorMsg);
    }


}
