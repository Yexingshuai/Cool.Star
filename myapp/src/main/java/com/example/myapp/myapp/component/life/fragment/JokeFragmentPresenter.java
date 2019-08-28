package com.example.myapp.myapp.component.life.fragment;

import com.example.myapp.myapp.component.life.entity.JokeBean;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.joke.JokeFragmentSource;

public class JokeFragmentPresenter implements JokeFragmentContract.Presenter {


    private JokeFragmentSource mSource;
    private JokeFragmentContract.View mView;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public JokeFragmentPresenter(JokeFragmentSource source, JokeFragmentContract.View view) {

        mSource = source;
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void requestJokeInfo(int type) {
        mSource.getJokeList(type, new HttpContext.Response<JokeBean>() {
            @Override
            public void success(JokeBean result) {
                if (result.getCode() == 200) {
                    mView.setJokeInfo(result);
                }


            }
        });
    }
}
