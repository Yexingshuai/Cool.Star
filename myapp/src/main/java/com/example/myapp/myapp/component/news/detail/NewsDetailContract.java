package com.example.myapp.myapp.component.news.detail;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.NewsDetailResponse;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/10/11.
 */

public interface NewsDetailContract {

    interface Presenter extends BasePresenter {

        void requestNewsDetail(int cid, int page, int size);
    }

    interface View extends BaseView<Presenter> {
        void requestNewsSuccess(NewsDetailResponse response);

        void requestNewsDetail(String errorMsg);
    }

    interface NewsDetailSource {
        void requestNewsDetail(int cid, int page, int size, HttpContext.Response response);
    }
}
