package com.example.myapp.myapp.component.news;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.NewsCategoryResponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.room.news.entity.Category;

import java.util.List;

/**
 * Created by yexing on 2018/10/10.
 */

public interface NewsContract {

    interface Presenter extends BasePresenter {
        void requestCategory();

        void queryDatabase();

        void deleteDatabase();

        void saveToDatabase(List<NewsCategoryResponse.ResultBean> categoryList);
    }

    interface View extends BaseView<Presenter> {
        void requestSuccess(NewsCategoryResponse result);

        void requestFail(String errorMsg);

        void setDatabaseData(List<Category> categoryList);
    }

    interface NewsFragmentSource {
        void requestCategory(HttpContext.Response response);
    }
}
