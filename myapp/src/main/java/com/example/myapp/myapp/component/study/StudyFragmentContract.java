package com.example.myapp.myapp.component.study;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.HomeItemBean;
import com.example.myapp.myapp.data.bean.KeyWordResponse;

/**
 * Created by yexing on 2018/7/16.
 */

public interface StudyFragmentContract {

    interface Presenter extends BasePresenter {

        void requestBannerAndStutyInfo(int index);

        void requestStudyInfo(int index);

        void collectArtist(int id);

        void unCollect(int id);

        void searchKeyWord(String message);
    }

    interface View extends BaseView<Presenter> {

        void setBannerInfo();

        void setStudyInfo(HomeItemBean result);

        void setBannerAndStudyInfo(Object result);

        void showLoading();

        void hideLoading();

        void collectSuccess();

        void collectFail(String msg);

        void unCollectSuccess();

        void unCollectFail(String msg);

        void requestBannerAndStudyInfoFail(String errorMsg);

        void setKeyWordInfo(KeyWordResponse response);

    }


}
