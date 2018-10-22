package com.example.myapp.myapp.component.news.detail;

import com.example.myapp.myapp.data.bean.NewsDetailResponse;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/10/11.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter {


    private NewsDetailContract.View mView;
    private NewsDetailContract.NewsDetailSource mSource;

    public NewsDetailPresenter(NewsDetailContract.View view, NewsDetailContract.NewsDetailSource source) {

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
    public void requestNewsDetail(int cid, int page, int size) {
        mSource.requestNewsDetail(cid, page, size, new HttpContext.Response<NewsDetailResponse>() {
            @Override
            public void success(NewsDetailResponse result) {
                if (result.getRetCode() == 200) {
                    mView.requestNewsSuccess(result);
                } else {
                    mView.requestNewsDetail(result.getMsg());
                }
            }

            @Override
            public void error(String error) {
                super.error(error);
                mView.requestNewsDetail(error);
            }
        });
    }
}
