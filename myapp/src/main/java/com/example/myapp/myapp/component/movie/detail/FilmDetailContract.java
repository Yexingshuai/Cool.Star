package com.example.myapp.myapp.component.movie.detail;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.FilmDetail;

/**
 * Created by yexing on 2018/8/16.
 */

public interface FilmDetailContract {

    interface Presenter extends BasePresenter {

        void getFilmDetail(String movieID);
    }

    interface View extends BaseView<Presenter> {

        void setFilmDetailInfo(FilmDetail filmDetail);
    }

}
