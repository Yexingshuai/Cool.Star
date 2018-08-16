package com.example.myapp.myapp.component.movie.live;

import com.example.myapp.myapp.data.bean.FilmLive;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.film.live.FilmLiveSource;

/**
 * Created by yexing on 2018/8/15.
 */

public class FilmLivePresenter implements FilmLiveContract.Presenter {
    private FilmLiveSource mSource;
    private FilmLiveContract.View mView;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public FilmLivePresenter(FilmLiveSource source, FilmLiveContract.View view) {

        mSource = source;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getLiveFilm() {
        mSource.getLiveFilm(new HttpContext.Response<FilmLive>() {
            @Override
            public void success(FilmLive result) {
                mView.setLiveFilmInfo(result);
            }

            @Override
            public void error(String error) {
                super.error(error);
                mView.getLiveFilmFail(error);
            }
        });
    }
}
