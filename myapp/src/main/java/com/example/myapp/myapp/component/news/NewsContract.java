package com.example.myapp.myapp.component.news;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.NewsCategoryResponse;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/10/10.
 */

public interface NewsContract {

    interface Presenter extends BasePresenter {
        void requestCategory();
    }

    interface View extends BaseView<Presenter> {
        void requestSuccess(NewsCategoryResponse result);

        void requestFail(String errorMsg);
    }

    interface NewsFragmentSource {
        void requestCategory(HttpContext.Response response);
    }
}
