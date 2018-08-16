package com.example.myapp.myapp.component.movie.live;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.FilmLive;

/**
 * Created by yexing on 2018/8/15.
 */

public interface FilmLiveContract {

    interface Presenter extends BasePresenter {
        void getLiveFilm();
    }

    interface View extends BaseView<Presenter> {
        void setLiveFilmInfo(FilmLive result);

        void getLiveFilmFail(String errorMsg);
    }


}
