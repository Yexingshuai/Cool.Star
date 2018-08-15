package com.example.myapp.myapp.data.source.favorite;

import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/11.
 */

public interface MyFavoriteSource {

    void getFavoriteList(int id, HttpContext.Response response);

    void delCollectArtist(int id,HttpContext.Response response);
}
