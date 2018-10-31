package com.example.myapp.myapp.component.study;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.HomeItemBean;
import com.example.myapp.myapp.data.bean.KeyWordResponse;
import com.example.myapp.myapp.room.search.SearchDataSource;
import com.example.myapp.myapp.room.search.entity.SearchHistory;

import java.util.List;

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

        void requestAllDatabase(SearchDataSource searchDataSource);

        void deleteAll(SearchDataSource searchDataSource);
    }

    interface View extends BaseView<Presenter> {


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

        void setSearchData(List<SearchHistory> list);

        void deleteDatabaseSuccess();

    }


}
