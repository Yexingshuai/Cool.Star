package com.example.myapp.myapp.component.life;

import com.example.myapp.myapp.data.bean.JokeResponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.life.LifeFragmentSource;

/**
 * Created by yexing on 2018/8/28.
 */

public class LifeFragmentPresenter implements LifeFragmentContract.Presenter {
    private LifeFragmentSource mSource;
    private LifeFragmentContract.View mView;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public LifeFragmentPresenter(LifeFragmentSource source, LifeFragmentContract.View view) {

        mSource = source;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void requestJoke(int pageNum, int pageSize) {
        mSource.requestJokeInfo(pageNum, pageSize, new HttpContext.Response<JokeResponse>() {
            @Override
            public void success(JokeResponse result) {
                if (result.getError_code() == 0) {
                    mView.setJokeInfo(result);
                } else {
                    mView.requestJokeFail(result.getReason());
                }
            }

            @Override
            public void error(String error) {
                super.error(error);
                mView.requestJokeFail(error);
            }
        });
    }
}
