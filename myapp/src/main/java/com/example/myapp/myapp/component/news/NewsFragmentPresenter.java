package com.example.myapp.myapp.component.news;

import com.example.myapp.myapp.data.bean.NewsCategoryResponse;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/10/10.
 */

public class NewsFragmentPresenter implements NewsContract.Presenter {


    private NewsContract.View mView;
    private NewsContract.NewsFragmentSource mSource;

    public NewsFragmentPresenter(NewsContract.View view, NewsContract.NewsFragmentSource source) {

        mView = view;
        mSource = source;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void requestCategory() {
        mSource.requestCategory(new HttpContext.Response<NewsCategoryResponse>() {
            @Override
            public void success(NewsCategoryResponse result) {
                if (result.getRetCode() == 200) {
                    mView.requestSuccess(result);
                } else {
                    mView.requestFail(result.getMsg());
                }
            }

            @Override
            public void error(String error) {
                super.error(error);
                mView.requestFail(error);
            }
        });
    }
}
