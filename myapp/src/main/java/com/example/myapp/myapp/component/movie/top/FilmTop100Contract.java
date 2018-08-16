package com.example.myapp.myapp.component.movie.top;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.Root;

/**
 * Created by yexing on 2018/8/15.
 */

public interface FilmTop100Contract {

    interface Presenter extends BasePresenter {
        void getTop100();
    }

    interface View extends BaseView<Presenter> {

        void setTop100Data(Root root);

        void getTop100fail(String errorMsg);
    }


}
