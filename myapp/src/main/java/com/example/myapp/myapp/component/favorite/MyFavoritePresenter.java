package com.example.myapp.myapp.component.favorite;

import com.example.myapp.myapp.data.bean.FavoriteResponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.favorite.MyFavoriteSource;

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

          }
      });
    }
}
