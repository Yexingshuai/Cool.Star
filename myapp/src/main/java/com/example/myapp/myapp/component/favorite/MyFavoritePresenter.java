package com.example.myapp.myapp.component.favorite;

import com.example.myapp.myapp.component.login.helper.LoginContext;
import com.example.myapp.myapp.data.bean.FavoriteResponse;
import com.example.myapp.myapp.data.bean.WanAndroidBaseReponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.favorite.MyFavoriteSource;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yexing on 2018/8/11.
 */

public class MyFavoritePresenter implements MyFavoriteContract.Presenter {
    private MyFavoriteSource mSource;
    private MyFavoriteContract.View mView;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public MyFavoritePresenter(MyFavoriteSource source, MyFavoriteContract.View view) {

        mSource = source;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getFavoriteList(int pageNum) {
        mSource.getFavoriteList(pageNum, new HttpContext.Response<FavoriteResponse>() {
            @Override
            public void success(FavoriteResponse result) {
                mView.setFavoriteList(result);
            }

            @Override
            public void error(String error) {
                super.error(error);
                mView.requestError(error);

            }
        });
    }

    @Override
    public void delCollectArtist(int id, String originId) {
        ArrayList<String> userCookieInfo = LoginContext.getInstance().getUserCookieInfo();
        HttpHeaders httpHeaders = new HttpHeaders();
        String name = userCookieInfo.get(0);
        String token = userCookieInfo.get(1);
        String cookie = name + token;
        httpHeaders.put("Cookie", cookie);
        OkGo.post("http://www.wanandroid.com/lg/uncollect/" + id + "/json")
                .tag(this)
                .cacheMode(CacheMode.DEFAULT)
                .headers(httpHeaders)
                .params("originId", originId != null ? originId : "-1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        WanAndroidBaseReponse reponse = gson.fromJson(s, WanAndroidBaseReponse.class);
                        int errorCode = reponse.getErrorCode();
                        if (errorCode == 0) {
                            mView.delCollectArtistSuccess();
                        } else {
                            mView.delCollectArtistFail(reponse.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mView.delCollectArtistFail(e.getMessage());
                    }
                });
    }

    /**
     * 加载更多我喜欢的
     *
     * @param pageNum
     */
    @Override
    public void getMoreFavoreteList(int pageNum) {
        mSource.getFavoriteList(pageNum, new HttpContext.Response<FavoriteResponse>() {
            @Override
            public void success(FavoriteResponse result) {
                mView.setMoreFavoriteList(result);
            }

            @Override
            public void error(String error) {
                super.error(error);
                mView.requestError(error);

            }
        });
    }
}
