package com.example.myapp.myapp.component.life.fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapp.myapp.component.life.entity.JokeBean;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.joke.JokeFragmentSource;
import com.example.myapp.myapp.ui.activity.AboutActivity;
import com.example.myapp.myapp.utils.ToastUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

public class JokeFragmentPresenter implements JokeFragmentContract.Presenter {


    private JokeFragmentSource mSource;
    private JokeFragmentContract.View mView;
    public String joke="https://www.apiopen.top/satinGodApi?type=%s&page=0";

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
//        mSource.getJokeList(type, new HttpContext.Response<JokeBean>() {
//            @Override
//            public void success(JokeBean result) {
//                mView.setJokeInfo(result);
//            }
//
//            @Override
//            public void error(String error) {
//                super.error(error);
//                mView.loadFail();
//                ToastUtil.showApp(error);
//            }
//        });
        joke = String.format(joke, type);


        OkGo.get(joke)
                .tag(this)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        JokeBean jokeBean = gson.fromJson(s, JokeBean.class);
                        mView.setJokeInfo(jokeBean);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });

    }

    @Override
    public void requestMoreJokeInfo(int type) {
        mSource.getJokeList(type, new HttpContext.Response<JokeBean>() {
            @Override
            public void success(JokeBean result) {
                if (result.getCode() == 200) {
                    mView.setMoreJokeInfo(result);
                }

            }
        });

    }
}
