package com.example.myapp.myapp.component.favorite;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.FavoriteResponse;

/**
 * Created by yexing on 2018/8/11.
 */

public interface MyFavoriteContract {

    interface Presenter extends BasePresenter {

        void getFavoriteList(int pageNum);

        void delCollectArtist(int id,String originId);

        void getMoreFavoreteList(int pageNum);
    }

    interface View extends BaseView<Presenter> {

        void setFavoriteList(FavoriteResponse response);

        void setMoreFavoriteList(FavoriteResponse response);

        void requestError(String errorMsg);

        void delCollectArtistSuccess();

        void delCollectArtistFail(String errorMsg);
    }


}
