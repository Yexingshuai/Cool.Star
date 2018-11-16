package com.example.myapp.myapp.component.news.category;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.room.news.entity.Category;

import java.util.List;

/**
 * Created by yexing on 2018/11/8.
 */

public interface CategoryContract {

    interface View extends BaseView<Presenter> {

        void setDatabaseData(List<Category> list);
    }

    interface Presenter extends BasePresenter {

        void saveData2Database(List<Category> mActiveList, List<Category> mInActiveList);

        void updateCatogryDatabase(List<Category> mActiveList, List<Category> mInActiveList);

        void queryAll();
    }

}
