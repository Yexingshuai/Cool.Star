package com.example.myapp.myapp.component.movie.detail;

import com.example.myapp.myapp.data.bean.FilmDetail;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.film.detail.FilmDetailSource;

/**
 * Created by yexing on 2018/8/16.
 */

public class FilmDetailPresenter implements FilmDetailContract.Presenter {
    private FilmDetailSource mSource;
    private FilmDetailContract.View mView;


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public FilmDetailPresenter(FilmDetailSource source, FilmDetailContract.View view) {

        mSource = source;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getFilmDetail(String movieID) {
        mSource.getFilmDetail(movieID, new HttpContext.Response<FilmDetail>() {
            @Override
            public void success(FilmDetail result) {
                mView.setFilmDetailInfo(result);
            }

            @Override
            public void error(String error) {
                super.error(error);
            }
        });
    }


}
