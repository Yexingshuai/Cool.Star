package com.example.myapp.myapp.component.study;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.HomeItemBean;

/**
 * Created by yexing on 2018/7/16.
 */

public interface StudyFragmentContract {

    interface Presenter extends BasePresenter {

        void requestBannerAndStutyInfo(int index);

        void requestStudyInfo(int index);
    }

    interface View extends BaseView<Presenter> {

        void setBannerInfo();

        void setStudyInfo(HomeItemBean result);

        void setBannerAndStudyInfo(Object result);

        void showLoading();

        void hideLoading();

    }


}
