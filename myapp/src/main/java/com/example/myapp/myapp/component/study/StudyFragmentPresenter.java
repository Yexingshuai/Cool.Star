package com.example.myapp.myapp.component.study;

import android.util.Log;

import com.example.myapp.myapp.data.bean.HomeItemBean;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.study.StudyFragmentSource;

/**
 * Created by yexing on 2018/7/16.
 */

public class StudyFragmentPresenter implements StudyFragmentContract.Presenter {


    private StudyFragmentSource mSource;
    private StudyFragmentContract.View mView;

    public StudyFragmentPresenter(StudyFragmentSource source, StudyFragmentContract.View view) {

        mSource = source;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    @Override
    public void requestBannerAndStutyInfo(int index) {
        mSource.requestBannerAndStutyInfo(index, new HttpContext.Response() {
            @Override
            public void success(Object result) {

                mView.setBannerAndStudyInfo(result);

            }

            @Override
            public void error(String error) {

            }

            @Override
            public void stop() {
                super.stop();
                mView.hideLoading();

            }
        });
    }

    @Override
    public void requestStudyInfo(int index) {
        mSource.requestStudyInfo(index, new HttpContext.Response<HomeItemBean>() {
            @Override
            public void success(HomeItemBean result) {
                mView.setStudyInfo(result);
            }

            @Override
            public void error(String error) {

            }


        });
    }
}
