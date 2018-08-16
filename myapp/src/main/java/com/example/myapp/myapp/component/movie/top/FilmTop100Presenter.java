package com.example.myapp.myapp.component.movie.top;

import com.example.myapp.myapp.data.bean.Root;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.film.top.FilmTop100Source;

/**
 * Created by yexing on 2018/8/15.
 */

public class FilmTop100Presenter implements FilmTop100Contract.Presenter {
    private FilmTop100Source mSource;
    private FilmTop100Contract.View mView;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public FilmTop100Presenter(FilmTop100Source source, FilmTop100Contract.View view) {

        mSource = source;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getTop100() {
        mSource.getTop100Film(new HttpContext.Response<Root>() {
            @Override
            public void success(Root result) {
                mView.setTop100Data(result);
            }

            @Override
            public void error(String error) {
                super.error(error);
                mView.getTop100fail(error);
            }
        });
    }
}
